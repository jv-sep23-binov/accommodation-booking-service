package com.application.bookingservice.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static String token = "6881196064:AAE7-P80dCgVvGdPhmcBEx5j52egElQj1wg";
    private static String botName = "BINOV_booking_notification_bot";

    public NotificationBot() {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null) {
                handleStartCommand(update);
            }
        } catch (Exception e){
            throw new RuntimeException("can't send message to user", e);
        }
    }

    private void handleStartCommand(Update update) {
        String text = update.getMessage().getText();
        System.out.println("text resive - " + text);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Welcome to BINOV booking! bot is still in development");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}