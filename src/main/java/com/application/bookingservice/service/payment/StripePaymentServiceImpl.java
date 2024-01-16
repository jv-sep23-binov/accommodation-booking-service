package com.application.bookingservice.service.payment;

import com.application.bookingservice.dto.payment.PaymentCreateResponseDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.exception.PaymentFailedException;
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
    private static final String CHECKOUT_FAILURE_MESSAGE = "Checkout failure! ";
    private static final String DEFAULT_PRODUCT_TYPE = "Booking";
    private static final String DEFAULT_CURRENCY_USD = "usd";
    private static final Long DEFAULT_PRODUCTS_QUANTITY = 1L;
    private static final Long PRICE_VALUE_IN_CENTS = 100L;
    private final PaymentService paymentService;
    @Value("${stripe.success.url}")
    private String successUrl;
    @Value("${stripe.cancel.url}")
    private String cancelUrl;
    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentCreateResponseDto createPaymentSession(PaymentRequestDto requestDto) {
        SessionCreateParams params = SessionCreateParams.builder()
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(getPrice(requestDto.getTotal()))
                        .setQuantity(DEFAULT_PRODUCTS_QUANTITY)
                        .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .build();
        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new PaymentFailedException(CHECKOUT_FAILURE_MESSAGE + e);
        }
        paymentService.save(requestDto, session);
        return new PaymentCreateResponseDto(session.getUrl());
    }

    private String getPrice(BigDecimal total) {
        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency(DEFAULT_CURRENCY_USD)
                .setUnitAmount(total.longValue() * PRICE_VALUE_IN_CENTS)
                .setProductData(PriceCreateParams.ProductData.builder()
                        .setName(DEFAULT_PRODUCT_TYPE)
                        .build()
                )
                .build();
        try {
            return Price.create(params).getId();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
