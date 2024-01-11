package com.application.bookingservice.service.bot;

import com.application.bookingservice.bot.NotificationBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService{
    private final NotificationBot notificationBot;

    @Override
    public Boolean bookingsMessage(Object o) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        notificationBot.sendLogMessage( formattedDateTime + "new bookings created/canceled");
        return true;
    }

    @Override
    public Boolean accommodationsMessage(Object o) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        notificationBot.sendLogMessage(formattedDateTime + "new created/released accommodations");
        return true;
    }

    @Override
    public Boolean paymentMessage(Object o) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        notificationBot.sendLogMessage(formattedDateTime + "successful payments");
        return true;
    }
}
