package com.application.bookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingUpdateRequestDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
}
