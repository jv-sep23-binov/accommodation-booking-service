package com.application.bookingservice.dto.booking;

import com.application.bookingservice.model.Booking;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingUpdateStatusRequestDto {
    @NotNull
    private Booking.Status status;
}
