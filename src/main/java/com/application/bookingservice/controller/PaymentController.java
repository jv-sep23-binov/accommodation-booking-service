package com.application.bookingservice.controller;

import com.application.bookingservice.dto.payment.PaymentCreateResponseDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.exception.PaymentFailedException;
import com.application.bookingservice.service.payment.StripePaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final StripePaymentService stripePaymentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public PaymentCreateResponseDto checkout(@RequestBody @Valid PaymentRequestDto requestDto) {
        try {
            return stripePaymentService.createPaymentSession(requestDto);
        } catch (StripeException e) {
            throw new PaymentFailedException("Checkout failure!" + e);
        }
    }
}
