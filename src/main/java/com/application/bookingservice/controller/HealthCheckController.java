package com.application.bookingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthCheckController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String ping() {
        return "Working...";
    }
}
