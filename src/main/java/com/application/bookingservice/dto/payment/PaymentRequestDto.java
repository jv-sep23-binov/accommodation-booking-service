package com.application.bookingservice.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @Positive
    @NotBlank
    private BigDecimal total;
    @Positive
    private Long bookingId;
}
