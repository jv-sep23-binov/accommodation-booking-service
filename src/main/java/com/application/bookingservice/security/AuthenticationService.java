package com.application.bookingservice.security;

import com.application.bookingservice.dto.customer.CustomerLoginRequestDto;
import com.application.bookingservice.dto.customer.CustomerLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public CustomerLoginResponseDto authenticate(CustomerLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(), requestDto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return new CustomerLoginResponseDto(token);
    }
}
