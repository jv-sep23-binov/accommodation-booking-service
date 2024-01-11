package com.application.bookingservice.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final Long CHAT_ID = -1002107651145L;
    private static String token;
    private static String botName;

    public NotificationBot(
            @Value("${bot.token}") String token,
            @Value("${bot.name}") String botName
    ) {
        super(token);
        this.token = token;
        this.botName = botName;
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
        } catch (Exception e) {
            throw new RuntimeException("can't send message to user", e);
        }
    }

    public void sendLogMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("can't send message" + e);
        }
    }

    private void handleStartCommand(Update update) {
        String text = update.getMessage().getText();
        System.out.println("text resive - " + text);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Hello, I am a bot for logging in BINOV booking");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
