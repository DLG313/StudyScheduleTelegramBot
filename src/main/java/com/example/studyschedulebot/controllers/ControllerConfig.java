package com.example.studyschedulebot.controllers;

import com.example.studyschedulebot.service.Controller;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.LinkedHashMap;
import java.util.Set;

@Configuration
public class ControllerConfig {


    private LinkedHashMap<String, Controller<BotApiMethod<?>>> commandToControllerMap;

    {
        commandToControllerMap = new LinkedHashMap<>();

        Controller<BotApiMethod<?>> startController = DefaultCommandsController::startController;
        Controller helpController = DefaultCommandsController::helpController;
        Controller subjectsController= SubjectController::subjectsController;
        Controller tasksController = TaskController::tasksController;
        Controller subjAddController= SubjectController::subjAddController;
        Controller taskAddController= TaskController::taskAddController;
        Controller subjNewController= SubjectController::subjNewController;
        Controller taskNewController= TaskController::taskNewController;
        Controller subjDeleteController = SubjectController::subjDeleteController;
        Controller subjUpdateController = SubjectController::subjUpdateController;
        Controller taskDeleteController = TaskController::taskDeleteController;
        Controller taskUpdateController = TaskController::taskUpdateController;
        Controller taskSetTimerController = TaskController::taskSetTimerController;
        Controller createReminderController = TaskController::createReminderController;


        commandToControllerMap.put("/start", startController);
        commandToControllerMap.put("/help", helpController);
        commandToControllerMap.put("/subjects", subjectsController);
        commandToControllerMap.put("/tasks", tasksController);

        commandToControllerMap.put("/subjects/add", subjAddController);
        commandToControllerMap.put("/tasks/add", taskAddController);
        commandToControllerMap.put("/subjects/new", subjNewController);
        commandToControllerMap.put("/tasks/new", taskNewController);

        commandToControllerMap.put("/subjects/delete", subjDeleteController);
        commandToControllerMap.put("/subjects/update", subjUpdateController);
        commandToControllerMap.put("/tasks/delete", taskDeleteController);
        commandToControllerMap.put("/tasks/update", taskUpdateController);

        commandToControllerMap.put("/tasks/timer", taskSetTimerController);
        commandToControllerMap.put("/settimer/1", createReminderController);
        commandToControllerMap.put("/settimer/3", createReminderController);
        commandToControllerMap.put("/settimer/6", createReminderController);
        commandToControllerMap.put("/settimer/12", createReminderController);
        commandToControllerMap.put("/settimer/24", createReminderController);

    }

    public LinkedHashMap<String, Controller<BotApiMethod<?>>> getCommandToControllerMap() {
        return commandToControllerMap;
    }

    public Set<String> getCommandsSet() {
        return commandToControllerMap.keySet();
    }
}
