package com.application.bookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingRequestDto {
    @NotBlank
    @Positive
    private Long accommodationId;
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
}
