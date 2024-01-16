package com.application.bookingservice.dto.bot;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BotRequestDto {
    @NotBlank
    private String message;
}
