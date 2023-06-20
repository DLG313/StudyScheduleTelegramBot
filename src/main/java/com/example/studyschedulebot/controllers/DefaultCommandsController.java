package com.example.studyschedulebot.controllers;

import com.example.studyschedulebot.keyboards.MenuKeyboards;
import com.example.studyschedulebot.service.CommandText;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.ArrayList;

public class DefaultCommandsController {

    public static ArrayList<BotApiMethod<?>> startController(Update update) {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        SendMessage response = new SendMessage();
        response.setReplyMarkup(new ReplyKeyboardRemove(true));
        response.setChatId(String.valueOf(chatId));
        response.setText(CommandText.START_TEXT);
        response.setReplyMarkup(MenuKeyboards.getStartMenuMarkup());
        messagesToSend.add(response);

        return messagesToSend;
    };

    public static ArrayList<BotApiMethod<?>> helpController(Update update) {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(CommandText.HELP_TEXT);
        messagesToSend.add(response);

        return messagesToSend;
    };

}
