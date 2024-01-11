package com.application.bookingservice.controller;

import com.application.bookingservice.service.AuthenticationService;
import com.application.bookingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public Object register(@RequestBody Object request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Object request) {
        return authenticationService.authenticate(request);
    }
}
