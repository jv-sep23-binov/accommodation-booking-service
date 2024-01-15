package com.application.bookingservice.service.bot;

import com.application.bookingservice.bot.NotificationBot;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.model.Payment;
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
    public Boolean bookingsCreatedMessage(BookingResponseDto responseDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" new bookings was created with accommodation id: ")
                .append(responseDto.getAccommodationId())
                .append(" Customer Id : ")
                .append(responseDto.getCustomerId())
                .append(" Сheck in data: ")
                .append(responseDto.getCheckIn())
                .append(" Сheck out data: ")
                .append(responseDto.getCheckOut());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean bookingCanceledMessage(Long id) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" Booking ")
                .append(id)
                .append(" was canceled");
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean bookingStatusChangedMessage(BookingResponseDto responseDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                        .append(" Booking status was changed to ")
                        .append(responseDto.getStatus())
                        .append(" booking id: ")
                        .append(responseDto.getId());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean accommodationCreatedMessage(AccommodationResponseDto responseDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" new Accommodation was created type: ")
                .append(responseDto.getType())
                .append(" size : ")
                .append(responseDto.getSize())
                .append(" Amentites: ")
                .append(responseDto.getAmenities())
                .append(" Address: ")
                .append(responseDto.getAddress())
                .append(" Price: ")
                .append(responseDto.getPrice());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean accommodationUpdateMessage(AccommodationResponseDto responseDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" Accommodation ")
                .append(responseDto.getId())
                .append(" data was changed to")
                .append(" type: ")
                .append(responseDto.getType())
                .append(" size : ")
                .append(responseDto.getSize())
                .append(" Amentites: ")
                .append(responseDto.getAmenities())
                .append(" Address: ")
                .append(responseDto.getAddress())
                .append(" Price: ")
                .append(responseDto.getPrice());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean accommodationUpdateAddressMessage(AccommodationResponseDto responseDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" Accommodation ")
                .append(responseDto.getId())
                .append(" address was changed to")
                .append(responseDto.getAddress());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean paymentMessage(Payment payment) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                .append(" Payment ")
                .append(payment.getId())
                .append(" was ")
                .append(payment.getStatus())
                .append(" total: ")
                .append(payment.getTotal());
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

    @Override
    public Boolean bookingExpiredMessage(String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedDateTime = now.format(formatter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formattedDateTime)
                        .append(" ")
                        .append(text);
        notificationBot.sendLogMessage(stringBuilder.toString());
        return true;
    }

}
