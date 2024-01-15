package com.application.bookingservice.bot;

import com.application.bookingservice.exception.TelegramMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final Long CHAT_ID = -1002107651145L;
    private static final String START_COMMAND = "/start";
    private static final String CANT_HANDLE_START_MESSAGE = "Can't handle start command";
    private static final String CANT_LOG_MESSAGE = "Can't send logs to chat text: %s";
    private static final String CANT_SEND_MESSAGE = "Can't send message to user chatId: %d";
    private static final String START_MESSAGE = "Hello, I am a bot for logging in BINOV booking";
    private final String botName;

    public NotificationBot(
            @Value("${bot.token}") String token,
            @Value("${bot.name}") String botName
    ) {
        super(token);
        this.botName = botName;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage().getText().equals(START_COMMAND)) {
                handleStartCommand(update);
            }
        } catch (Exception e) {
            throw new TelegramMessageException(CANT_HANDLE_START_MESSAGE, e);
        }
    }

    public void sendLogMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramMessageException(String.format(CANT_LOG_MESSAGE, text), e);
        }
    }

    private void handleStartCommand(Update update) {
        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setText(START_MESSAGE);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramMessageException(String.format(CANT_SEND_MESSAGE, chatId), e);
        }
    }

}
