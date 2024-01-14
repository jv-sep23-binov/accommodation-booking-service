package com.application.bookingservice.service.payment;

import com.application.bookingservice.dto.payment.PaymentResponseDto;
import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getPaymentsByCustomerId(Long customerId);

    void succeed(String sessionId);

    void cancel(String sessionId);
}
