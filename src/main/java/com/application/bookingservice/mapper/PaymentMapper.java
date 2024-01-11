package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "bookingId", source = "booking.id")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(target = "booking.id", source = "bookingId")
    Payment toEntity(PaymentRequestDto requestDto);
}
