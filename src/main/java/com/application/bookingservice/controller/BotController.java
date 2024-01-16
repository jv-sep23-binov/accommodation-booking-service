package com.application.bookingservice.controller;

import com.application.bookingservice.dto.bot.BotRequestDto;
import com.application.bookingservice.service.bot.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram")
public class BotController {
    private final NotificationService notificationService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send notification",
            description = "Permits to send message to all users")
    public void sendNotification(@RequestBody @Valid BotRequestDto botRequestDto) {
        notificationService.sendToAllUsers(botRequestDto.getMessage());
    }

}
