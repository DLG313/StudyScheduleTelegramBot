package com.example.studyschedulebot.service;

import com.example.studyschedulebot.entities.Subject;
import com.example.studyschedulebot.entities.Task;
import com.example.studyschedulebot.keyboards.InlineKeyboards;
import com.example.studyschedulebot.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TextParserService textParserService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TextParserService textParserService) {
        this.taskRepository = taskRepository;
        this.textParserService = textParserService;
    }

    public ArrayList<BotApiMethod<?>> getUsersTaskList(Long chatId) {
        ArrayList<BotApiMethod<?>> subjectListToSend = new ArrayList<>();

        ArrayList<Task> taskListToConvert = taskRepository.getTasksByChatId(chatId);

        for(Task task : taskListToConvert) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(textParserService.taskToString(task));
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(InlineKeyboards.getTasksInlineMarkup());
            subjectListToSend.add(sendMessage);
        }

        return subjectListToSend;
    }

    public Task createTaskOutOfUpdate(Update update) throws TelegramApiException {
        ArrayList<String> updateBody = textParserService.extractUpdateBody(update);
        Task task;

        if (updateBody.size() == 3) {
            String name = updateBody.get(0);
            String desc = updateBody.get(1);
            String deadlineStr = updateBody.get(2).replaceAll("[^0-9:.\\s]", "");

            String pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.[0-9]{4} (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime deadline = deadlineStr.matches(pattern) ? LocalDateTime.parse(deadlineStr, formatter) : null;

            task = new Task(name, desc, deadline, update.getMessage().getChatId());}
        else{
            throw new TelegramApiException("Ошибка при создании задачи");
        }

        save(task);
        return task;
    }

    public void updateTaskByID (Update update) throws TelegramApiException {
        String msgText = update.getMessage().getText();
        long id = textParserService.getIdOutOfMessage(msgText);
        ArrayList<String> body = textParserService.extractUpdateBody(update);

        Task task = taskRepository.getTaskById(id);

        if (task != null){
            task.setName(body.get(1));
            task.setDescription(body.get(2));

            String pattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.[0-9]{4} (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
            if (body.get(3).replaceAll("[^0-9:.\\s]", "").matches(pattern)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                task.setDeadline(LocalDateTime.parse(body.get(3).replaceAll("[^0-9:.\\s]", ""), formatter));
            }
            save(task);
        }

        else{
            throw new TelegramApiException("Ошибка при обновлении задачи");
        }

    }

    public void deleteTaskByID(long id) throws TelegramApiException {
        Task task = taskRepository.getTaskById(id);

        if (task != null){
            taskRepository.delete(task);
        }

        else{
            throw new TelegramApiException("Ошибка при удалении задачи");
        }
    }

    public void save (Task task) {taskRepository.save(task);}

    public Task getTaskById(long taskId) throws TelegramApiException {
        Task task = taskRepository.getTaskById(taskId);
        if (task != null) {return task;}
        else {throw new TelegramApiException("Ошибка");}

    }

    public Boolean taskExist (long taskId) {
        return taskRepository.existsTaskById(taskId);

    }
}
