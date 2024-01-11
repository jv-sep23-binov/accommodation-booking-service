package com.application.bookingservice.mapper;

import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.model.Payment;

public interface PaymentMapper {
    PaymentResponseDto toDto(Payment payment);

    Payment toEntity(PaymentRequestDto requestDto);
}
