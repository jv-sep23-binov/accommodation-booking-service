package com.application.bookingservice.service.bot;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;

public interface NotificationService {
    Boolean bookingsMessage(BookingRequestDto bookingRequestDto);

    Boolean accommodationsMessage(AccommodationRequestDto accommodationRequestDto);

    Boolean paymentMessage(PaymentRequestDto paymentRequestDto);
}
