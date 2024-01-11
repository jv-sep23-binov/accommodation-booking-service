package com.application.bookingservice.service.bot;

public interface NotificationService {
    Boolean bookingsMessage(Object o);

    Boolean accommodationsMessage(Object o);

    Boolean paymentMessage(Object o);
}
