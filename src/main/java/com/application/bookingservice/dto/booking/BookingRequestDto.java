package com.application.bookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingRequestDto {
    @NotNull
    @Positive
    private Long accommodationId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
}
