package com.example.studyschedulebot.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public interface Controller<T> {
    ArrayList<BotApiMethod<?>> controllerMethod (Update update) throws TelegramApiException;
}
