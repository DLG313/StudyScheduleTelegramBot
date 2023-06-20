package com.example.studyschedulebot.config;

import com.example.studyschedulebot.controllers.ControllerConfig;
import com.example.studyschedulebot.service.Controller;
import com.example.studyschedulebot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    private ControllerConfig controllerConfig;


    private List<BotCommand> menuCommandList;

    {
        menuCommandList = new ArrayList<>();
        menuCommandList.add(new BotCommand("/start", "бот скажет привет"));
        menuCommandList.add(new BotCommand("/subjects","список предметов"));
        menuCommandList.add(new BotCommand("/tasks","список задач"));
        menuCommandList.add(new BotCommand("/subjects/add","добавить предмет"));
        menuCommandList.add(new BotCommand("/tasks/add","добавить задачу"));
        menuCommandList.add(new BotCommand("/help","press it if u dumb"));

    }



    public BotConfig() {
        this.botName = botName;
        this.token = token;
        this.controllerConfig = new ControllerConfig();
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBotName() {
        return botName;
    }


    public String getToken() {
        return token;
    }

    public LinkedHashMap<String, Controller<BotApiMethod<?>>> getCommandToControllerMap() {
        return controllerConfig.getCommandToControllerMap();
    }

    public List<BotCommand> getMenuCommandList() {
        return menuCommandList;
    }
}
