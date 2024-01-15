package com.application.bookingservice.service.payment;

import static com.application.bookingservice.model.Payment.Status;

import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.mapper.PaymentMapper;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Payment;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.repository.payment.PaymentRepository;
import com.application.bookingservice.service.bot.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String PAYMENT_NOT_FOUND_MESSAGE =
            "Can't find payment by session id: ";
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationService notificationService;

    @Override
    public List<PaymentResponseDto> getPaymentsByCustomerId(Long customerId) {
        return paymentRepository.findAllByCustomerId(customerId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void succeed(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId).orElseThrow(
                () -> new EntityNotFoundException(PAYMENT_NOT_FOUND_MESSAGE + sessionId)
        );
        payment.setStatus(Status.SUCCEED);
        paymentRepository.save(payment);

        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.CONFIRMED);
        bookingRepository.save(booking);

        notificationService.paymentMessage(payment);
    }

    @Override
    @Transactional
    public void cancel(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId).orElseThrow(
                () -> new EntityNotFoundException(PAYMENT_NOT_FOUND_MESSAGE + sessionId)
        );
        payment.setStatus(Status.FAILED);
        paymentRepository.save(payment);

        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.REJECTED);
        bookingRepository.save(booking);

        notificationService.paymentMessage(payment);
    }
}
