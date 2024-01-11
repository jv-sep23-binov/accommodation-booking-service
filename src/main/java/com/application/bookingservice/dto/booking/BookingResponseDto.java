package com.application.bookingservice.dto.booking;

import com.application.bookingservice.model.Booking.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class BookingResponseDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private Long accommodationId;
    private Long customerId;
    private Status status;
}
