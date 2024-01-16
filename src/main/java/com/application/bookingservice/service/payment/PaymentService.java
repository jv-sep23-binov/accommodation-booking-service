package com.application.bookingservice.service.payment;

import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.stripe.model.checkout.Session;
import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getPaymentsByCustomerId(Long customerId);

    void succeed(String sessionId);

    void cancel(String sessionId);

    void save(PaymentRequestDto requestDto, Session session);
}
