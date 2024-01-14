package com.application.bookingservice.controller;

import com.application.bookingservice.dto.payment.PaymentCreateResponseDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.service.payment.PaymentService;
import com.application.bookingservice.service.payment.StripePaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final StripePaymentService stripePaymentService;
    private final PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "All user's payments",
            description = "Get payments history of certain customer")
    public List<PaymentResponseDto> getPaymentsByUserId(Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return paymentService.getPaymentsByCustomerId(customer.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public PaymentCreateResponseDto checkout(@RequestBody @Valid PaymentRequestDto requestDto) {
        return stripePaymentService.createPaymentSession(requestDto);
    }

    @GetMapping("/success")
    @Operation(summary = "Succeed payment",
            description = "Redirection after successfully processing of payment")
    public String getSuccessfulResponse(@RequestParam("session-id") String sessionId) {
        paymentService.succeed(sessionId);
        return "Paid successfully! Session ID: " + sessionId;
    }

    @GetMapping("/cancel")
    @Operation(summary = "Payment failure",
            description = "Redirection after unsuccessfully processing of payment")
    public String getCancelResponse(@RequestParam("session-id") String sessionId) {
        paymentService.cancel(sessionId);
        return "Payment canceled. Session ID: " + sessionId;
    }
}
