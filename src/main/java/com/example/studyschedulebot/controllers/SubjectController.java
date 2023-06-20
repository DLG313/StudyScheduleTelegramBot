package com.example.studyschedulebot.controllers;

import com.example.studyschedulebot.entities.Subject;
import com.example.studyschedulebot.service.CommandText;
import com.example.studyschedulebot.service.SubjectService;
import com.example.studyschedulebot.service.TextParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

@Controller
public class SubjectController {


    private static SubjectService subjectService;
    private static TextParserService textParserService;

    @Autowired
    public SubjectController(SubjectService subjectService, TextParserService textParserService) {
        this.subjectService = subjectService;
        this.textParserService = textParserService;
    }

    public static ArrayList<BotApiMethod<?>> subjAddController(Update update) {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        long chatId = update.getMessage().getChatId();

        SendMessage response1 = new SendMessage();
        response1.setChatId(String.valueOf(chatId));
        response1.setText(CommandText.SUBJECT_ADD_TEXT);
        messagesToSend.add(response1);

        SendMessage response2 = new SendMessage();
        response2.setChatId(String.valueOf(chatId));
        response2.setText(CommandText.SUBJECT_ADD_PATTERN);
        messagesToSend.add(response2);


        return messagesToSend;
    };

    public static ArrayList<BotApiMethod<?>>  subjectsController (Update update){
        long chatid = update.getMessage().getChatId();
        ArrayList<BotApiMethod<?>> messagesToSend = subjectService.getUsersSubjectList(chatid);
        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> subjNewController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();
        Subject subject = subjectService.createSubjectOutOfUpdate(update);

        SendMessage response = new SendMessage();
        long chatId = update.getMessage().getChatId();
        response.setChatId(String.valueOf(chatId));
        response.setText(textParserService.subjectToString(subject));
        messagesToSend.add(response);

        SendMessage response2 = new SendMessage();
        response2.setChatId(String.valueOf(chatId));
        response2.setText("Предмет сохранен");
        messagesToSend.add(response2);

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> subjDeleteController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        long deleteId = textParserService.getIdOutOfMessage(update.getCallbackQuery().getMessage().getText());
        subjectService.deleteSubjectByID(deleteId);

        SendMessage response = new SendMessage();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        response.setChatId(String.valueOf(chatId));
        response.setText("Предмет удален");

        messagesToSend = subjectService.getUsersSubjectList(chatId);
        messagesToSend.add(response);

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> subjUpdateController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();


        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long updateId = textParserService.getIdOutOfMessage(update.getCallbackQuery().getMessage().getText());

            SendMessage response1 = new SendMessage();
            response1.setChatId(String.valueOf(chatId));
            response1.setText("Скопируйте следующее сообщение и заполните поля предмета заново");
            messagesToSend.add(response1);

            SendMessage response2= new SendMessage();
            response2.setChatId(String.valueOf(chatId));
            response2.setText("/subjects/update" + "\n <ID: " + updateId + ">\n" +
                    "<>\n" + "<>\n" + "<>\n" + "<>\n");
            messagesToSend.add(response2);
        }
        else {
            long chatId = update.getMessage().getChatId();
            subjectService.updateSubjectByID(update);

            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            response.setText("Изменения сохранены");
            messagesToSend.add(response);

        }


        return messagesToSend;
    }


}
