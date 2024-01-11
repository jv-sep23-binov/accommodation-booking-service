package com.application.bookingservice.controller;

import com.application.bookingservice.dto.customer.CustomerLoginRequestDto;
import com.application.bookingservice.dto.customer.CustomerLoginResponseDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.security.AuthenticationService;
import com.application.bookingservice.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CustomerService customerService;

    @PostMapping("/registration")
    public CustomerRegistrationResponseDto register(
            @RequestBody @Valid CustomerRegistrationRequestDto requestDto) {
        return customerService.register(requestDto);
    }

    @PostMapping("/login")
    public CustomerLoginResponseDto login(@RequestBody @Valid CustomerLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
