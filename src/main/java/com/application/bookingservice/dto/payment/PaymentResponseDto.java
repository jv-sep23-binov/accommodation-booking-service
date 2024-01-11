package com.application.bookingservice.dto.payment;

import com.application.bookingservice.model.Payment.Status;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private Status status;
    private Long bookingId;
    private BigDecimal total;
}
