package com.application.bookingservice.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @Positive
    @NotNull
    private BigDecimal total;
    @Positive
    private Long bookingId;
}
