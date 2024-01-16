package com.application.bookingservice.service.bot;

import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.model.Payment;

public interface NotificationService {
    Boolean bookingsCreatedMessage(BookingResponseDto responseDto);

    Boolean bookingCanceledMessage(Long id);

    Boolean bookingStatusChangedMessage(BookingResponseDto responseDto);

    Boolean accommodationCreatedMessage(AccommodationResponseDto responseDto);

    Boolean accommodationUpdateMessage(AccommodationResponseDto responseDto);

    Boolean accommodationUpdateAddressMessage(AccommodationResponseDto responseDto);

    Boolean paymentMessage(Payment payment);

    Boolean bookingExpiredMessage(String text);
}
