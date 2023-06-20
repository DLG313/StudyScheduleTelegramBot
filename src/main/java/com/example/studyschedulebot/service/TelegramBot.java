package com.example.studyschedulebot.service;

import com.example.studyschedulebot.config.BotConfig;
import com.example.studyschedulebot.entities.Reminder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    private TextParserService textParserService;
    private SubjectService subjectService;
    private TaskService taskService;
    private ReminderService reminderService;

    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);


    @Autowired
    public TelegramBot(BotConfig config, SubjectService subjectService,
                       TextParserService textParserService, TaskService taskService, ReminderService reminderService) throws TelegramApiException {
        super(config.getToken());
        this.config = config;
        this.subjectService = subjectService;
        this.taskService = taskService;
        this.reminderService = reminderService;
        this.textParserService = textParserService;

    }

    @PostConstruct
    public void init() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @Override
    public  String getBotToken(){
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }





    @Override
    public void onUpdateReceived (Update update) {
        ArrayList<BotApiMethod<?>> responsesToSend = updateHandler(update);


        for (BotApiMethod<?> response : responsesToSend) {
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.getMessage();
            }
        }

    }

    //returns list of messages if update was handled successfully
    public ArrayList<BotApiMethod<?>> updateHandler(Update update) {
        ArrayList<BotApiMethod<?>> responsesToSend = new ArrayList<>();

        //заменить на первую строку из апдейта
        String messageText = textParserService.getCommandFromUpdate(update);

        try {
             if (update.hasCallbackQuery()) {
                messageText = update.getCallbackQuery().getData();
                Controller controller = config.getCommandToControllerMap().get(messageText);
                 responsesToSend = processUpdate(update, controller);
             }
             else if (config.getCommandToControllerMap().containsKey(messageText) && !update.hasCallbackQuery()) {
                Controller controller = config.getCommandToControllerMap().get(messageText);
                 responsesToSend = processUpdate(update, controller);
             }
             else {
                long chatId = update.getMessage().getChatId();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Не поддерживается");
                responsesToSend.add(sendMessage);
            }
        } catch (TelegramApiException e) {
            SendMessage sendMessage = new SendMessage();
            long chatId;
            if (update.hasCallbackQuery()){chatId = update.getCallbackQuery().getMessage().getChatId();}
            else {chatId = update.getMessage().getChatId();}
            sendMessage.setChatId(chatId);
            sendMessage.setText(e.getMessage());
            responsesToSend.add(sendMessage);
        }


        return responsesToSend;
    }



    private ArrayList<BotApiMethod<?>> processUpdate(Update update, Controller controller) throws TelegramApiException {
        return controller.controllerMethod(update);
    }



    public static ArrayList<SendMessage> notSupportedController(Update update) {
        ArrayList<SendMessage> messagesToSend = new ArrayList<>();

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText("Не поддерживается");
        messagesToSend.add(response);

        return messagesToSend;
    }

    @Scheduled(fixedRate = 60000)
    private void scheduleReminders() {

        reminderService.deleteOutdatedReminders();

        ArrayList<SendMessage> remindersToSend = new ArrayList<>();

        ArrayList<Reminder> reminders = reminderService.getRemindersToSend(LocalDateTime.now());

        for (Reminder reminder : reminders) {
            try {
                if (taskService.taskExist(reminder.getTask_id())) {
                    SendMessage msg1 = new SendMessage();
                    msg1.setChatId(reminder.getChat_id());
                    msg1.setText(textParserService.taskToString(taskService.getTaskById(reminder.getTask_id())));
                    remindersToSend.add(msg1);

                    SendMessage msg2 = new SendMessage();
                    msg2.setChatId(reminder.getChat_id());
                    msg2.setText("Пора бы заняться этой задачей");
                    remindersToSend.add(msg2);
                }
                else {
                    reminderService.deleteRemindersByTaskId(reminder.getTask_id());
                }

            } catch (TelegramApiException e) {
                e.getMessage();
            }
        }


        for (SendMessage remindersMsg : remindersToSend) {
            try {
                execute(remindersMsg);
            } catch (TelegramApiException e) {
                e.getMessage();
            }
        }
    }


}
