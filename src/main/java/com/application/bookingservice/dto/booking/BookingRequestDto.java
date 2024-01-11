package com.application.bookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class BookingRequestDto {
    private Long accommodationId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
}
