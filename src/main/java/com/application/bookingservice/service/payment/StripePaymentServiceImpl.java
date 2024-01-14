package com.application.bookingservice.service.payment;

import com.application.bookingservice.dto.payment.PaymentCreateResponseDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.exception.PaymentFailedException;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Payment;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.repository.payment.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements StripePaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentCreateResponseDto createPaymentSession(PaymentRequestDto requestDto) {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://example.com/success")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(getPrice(requestDto.getTotal()))
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build();
        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new PaymentFailedException("Checkout failure!" + e);
        }
        savePayment(requestDto, session);
        return new PaymentCreateResponseDto(session.getUrl());
    }

    private String getPrice(BigDecimal total) {
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("usd")
                        .setUnitAmount(total.longValue() * 100L)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();
        try {
            return Price.create(params).getId();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePayment(PaymentRequestDto requestDto, Session session) {
        Booking booking = bookingRepository.findById(requestDto.getBookingId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find booking with id: "
                                + requestDto.getBookingId())
                );
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PROCESSING);
        payment.setBooking(booking);
        payment.setSessionId(session.getId());
        payment.setSessionUrl(session.getUrl());
        payment.setTotal(requestDto.getTotal());
        paymentRepository.save(payment);
    }
}
