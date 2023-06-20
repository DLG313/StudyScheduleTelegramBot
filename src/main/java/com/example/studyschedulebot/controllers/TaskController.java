package com.example.studyschedulebot.controllers;

import com.example.studyschedulebot.entities.Reminder;
import com.example.studyschedulebot.entities.Task;
import com.example.studyschedulebot.keyboards.InlineKeyboards;
import com.example.studyschedulebot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class TaskController {

    private static TaskService taskService;
    private static TextParserService textParserService;
    private static ReminderService reminderService;


    @Autowired
    public TaskController(TaskService taskService, TextParserService textParserService, ReminderService reminderService) {
        this.taskService = taskService;
        this.textParserService = textParserService;
        this.reminderService = reminderService;
    }

    public static ArrayList<BotApiMethod<?>> tasksController (Update update){
        long chatid = update.getMessage().getChatId();
        ArrayList<BotApiMethod<?>> messagesToSend = taskService.getUsersTaskList(chatid);
        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> taskAddController(Update update) {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        long chatId = update.getMessage().getChatId();

        SendMessage response1 = new SendMessage();
        response1.setChatId(String.valueOf(chatId));
        response1.setText(CommandText.TASK_ADD_TEXT);
        messagesToSend.add(response1);

        SendMessage response2 = new SendMessage();
        response2.setChatId(String.valueOf(chatId));
        response2.setText(CommandText.TASK_ADD_PATTERN);
        messagesToSend.add(response2);

        return messagesToSend;
    };

    public static ArrayList<BotApiMethod<?>> taskNewController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();
        Task task = taskService.createTaskOutOfUpdate(update);

        SendMessage response = new SendMessage();
        long chatId = update.getMessage().getChatId();
        response.setChatId(String.valueOf(chatId));
        response.setText(textParserService.taskToString(task));
        messagesToSend.add(response);

        SendMessage response2 = new SendMessage();
        response2.setChatId(String.valueOf(chatId));
        response2.setText("Задача сохранена");
        messagesToSend.add(response2);

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> taskDeleteController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        long deleteId = textParserService.getIdOutOfMessage(update.getCallbackQuery().getMessage().getText());
        taskService.deleteTaskByID(deleteId);

        SendMessage response = new SendMessage();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        response.setChatId(String.valueOf(chatId));
        response.setText("Задача удалена");

        messagesToSend = taskService.getUsersTaskList(chatId);
        messagesToSend.add(response);

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> taskUpdateController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long updateId = textParserService.getIdOutOfMessage(update.getCallbackQuery().getMessage().getText());

            SendMessage response1 = new SendMessage();
            response1.setChatId(String.valueOf(chatId));
            response1.setText("Скопируйте следующее сообщение и заполните поля задачи заново");
            messagesToSend.add(response1);

            SendMessage response2= new SendMessage();
            response2.setChatId(String.valueOf(chatId));
            response2.setText("/tasks/update" + "\n <ID: " + updateId + ">\n" +
                    "<>\n" + "<>\n" + "<>\n");
            messagesToSend.add(response2);
        }
        else {
            long chatId = update.getMessage().getChatId();
            taskService.updateTaskByID(update);

            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            response.setText("Изменения сохранены");
            messagesToSend.add(response);

        }

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> taskSetTimerController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        CallbackQuery callbackQuery = update.getCallbackQuery();
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
        editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(InlineKeyboards.setTimerInlineMarkup());

        messagesToSend.add(editMessageReplyMarkup);

        return messagesToSend;
    }

    public static ArrayList<BotApiMethod<?>> createReminderController (Update update) throws TelegramApiException {
        ArrayList<BotApiMethod<?>> messagesToSend = new ArrayList<>();

        String timerType = update.getCallbackQuery().getData();

        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long taskId = textParserService.getIdOutOfMessage(update.getCallbackQuery().getMessage().getText());
        LocalDateTime deadlineTime = taskService.getTaskById(taskId).getDeadline();

        Reminder reminder = new Reminder(chatId, taskId, deadlineTime);;

        switch (timerType){
            case "/settimer/1":
                LocalDateTime reminderTime = deadlineTime.minusHours(1);
                reminder.setTime(reminderTime);
            break;

            case "/settimer/3":
                reminderTime = deadlineTime.minusHours(3);
                reminder.setTime(reminderTime);
            break;

            case "/settimer/6":
                reminderTime = deadlineTime.minusHours(6);
                reminder.setTime(reminderTime);
            break;

            case "/settimer/12":
                reminderTime = deadlineTime.minusHours(12);
                reminder.setTime(reminderTime);
            break;

            case "/settimer/24":
                reminderTime = deadlineTime.minusHours(24);
                reminder.setTime(reminderTime);
            break;
        }

        reminderService.save(reminder);

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("Напоминание создано");
        messagesToSend.add(response);

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(InlineKeyboards.getTasksInlineMarkup());

        messagesToSend.add(editMessageReplyMarkup);

        return messagesToSend;
    }



}
