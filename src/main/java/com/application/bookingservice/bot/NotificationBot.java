package com.application.bookingservice.bot;

import com.application.bookingservice.dto.customer.CustomerLoginRequestDto;
import com.application.bookingservice.exception.TelegramMessageException;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.repository.customer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final Long CHAT_ID = -1002107651145L;
    private static final String START_COMMAND = "/start";
    private static final String AUTH_COMMAND = "/auth";
    private static final String CANCEL_COMMAND = "/cancel";
    private static final String CANT_LOG_MESSAGE = "Can't send logs to chat text: %s";
    private static final String CANT_SEND_MESSAGE = "Can't send message to user chatId: %d";
    private static final String START_MESSAGE = "Hello, I am a bot for logging in BINOV booking \n "
            + "please use /auth to start recive messages";
    private final String botName;
    private final CustomerRepository customerRepository;
    private CustomerLoginRequestDto requestDto;
    private final AuthenticationManager authenticationManager;
    private Map<Long, AuthState> authStates = new HashMap<>();
    private Map<Long, CustomerLoginRequestDto> loginData = new HashMap<>();

    private enum AuthState {
        STARTED,
        LOGIN,
        PASSWORD
    }

    public NotificationBot(
            @Value("${bot.token}") String token,
            @Value("${bot.name}") String botName,
            CustomerRepository customerRepository,
            AuthenticationManager authenticationManager
    ) {
        super(token);
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.botName = botName;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        sendTextMessageToAll(update.getMessage().getText());
        Long chatId = update.getMessage().getChatId();
        AuthState currentState = authStates.getOrDefault(chatId, AuthState.STARTED);
        String recivedText = update.getMessage().getText();
        if (recivedText.equals(START_COMMAND)) {
            handleStartCommand(update);
        } else if (authStates.containsKey(chatId)) {
            switch (currentState) {
                case STARTED:
                    sendTextMessage(chatId, "Please enter your login:");
                    authStates.put(chatId, AuthState.LOGIN);
                    break;
                case LOGIN:
                    requestDto = loginData.getOrDefault(chatId, new CustomerLoginRequestDto());
                    requestDto.setEmail(recivedText);
                    sendTextMessage(chatId, "Password:");
                    loginData.put(chatId, requestDto);
                    authStates.put(chatId, AuthState.PASSWORD);
                    break;
                case PASSWORD:
                    requestDto = loginData.getOrDefault(chatId, new CustomerLoginRequestDto());
                    requestDto.setPassword(recivedText);
                    try {
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                requestDto.getEmail(), requestDto.getPassword()
                        ));
                        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                                () -> new EntityNotFoundException("Can't find customer")
                        );
                        customer.setChatId(chatId);
                        customerRepository.save(customer);
                        sendTextMessage(chatId, "Successful, now you will receive "
                                + "notifications from BINOV_booking");
                    } catch (Exception e) {
                        sendTextMessage(chatId, "Wrong password or login please try again");
                    } finally {
                        authStates.remove(chatId);
                        requestDto.setPassword("");
                    }
                    break;
            }
        } else if (recivedText.equals(AUTH_COMMAND)) {
            sendTextMessage(chatId, "Please enter your login:");
            authStates.put(chatId, AuthState.LOGIN);
        } else if (recivedText.equals(CANCEL_COMMAND)) {
            Customer customer = customerRepository.findByChatId(chatId).orElseThrow(
                    () -> new EntityNotFoundException("can't find customer by chat id")
            );
            customer.setChatId(null);
            customerRepository.save(customer);
            sendTextMessage(chatId, "notification canceled successful");
        } else {
            sendTextMessage(chatId, "hello im BINOV_booking_bot use /auth command if you haven't done it yet to receive notification or /cancel to stop receive messages");
        }
    }

    private void sendTextMessageToAll(String text) {
        List<Customer> allByChatIdNotNull = customerRepository.findAllByChatIdNotNull();
        for (int i = 0; i < allByChatIdNotNull.size(); i++) {
            sendTextMessage(allByChatIdNotNull.get(i).getChatId(), text);
        }
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
               message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
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
