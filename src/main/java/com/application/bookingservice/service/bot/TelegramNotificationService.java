package com.application.bookingservice.service.bot;

import com.application.bookingservice.bot.NotificationBot;
import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.payment.PaymentRequestDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final NotificationBot notificationBot;

    @Override
    public Boolean bookingsMessage(BookingRequestDto bookingRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append("new bookings was created with id: ")
                .append(bookingRequestDto.getAccommodationId())
                .append(" Сheck in data: ")
                .append(bookingRequestDto.getCheckIn())
                .append(" Сheck out data: ")
                .append(bookingRequestDto.getCheckOut());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean accommodationsMessage(AccommodationRequestDto accommodationRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" new Accommodation was created type: ")
                .append(accommodationRequestDto.getType())
                .append(" size : ")
                .append(accommodationRequestDto.getSize())
                .append(" Amentites: ")
                .append(accommodationRequestDto.getAmenities())
                .append(" Address: ")
                .append(accommodationRequestDto.getAddress())
                .append(" Price: ")
                .append(accommodationRequestDto.getPrice());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean paymentMessage(PaymentRequestDto paymentRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" successful payments  booking id :")
                .append(paymentRequestDto.getBookingId())
                .append(" total : ")
                .append(paymentRequestDto.getTotal());
        notificationBot.sendLogMessage(formattedDateTime + "successful payments");
        return true;
    }
}
