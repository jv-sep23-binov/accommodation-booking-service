package com.application.bookingservice.service.payment;

import com.application.bookingservice.dto.payment.PaymentCreateResponseDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;

public interface StripePaymentService {
    PaymentCreateResponseDto createPaymentSession(PaymentRequestDto requestDto);
}
