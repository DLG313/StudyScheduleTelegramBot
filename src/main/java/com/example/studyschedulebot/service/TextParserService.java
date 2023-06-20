package com.example.studyschedulebot.service;

import com.example.studyschedulebot.entities.Subject;
import com.example.studyschedulebot.entities.Task;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.vdurmont.emoji.EmojiParser;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextParserService {

    public String getCommandFromUpdate(Update update) {
        String firstLine = "";
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inputText = update.getMessage().getText();
            String[] lines = inputText.split("\n");
            firstLine = lines[0];
        }
        return firstLine;
    }

    public long getIdOutOfMessage (String body) {
        int startIndex = body.indexOf("ID: ");
        int endIndex = body.indexOf("\n", startIndex);
        String numberString = body.substring(startIndex + 4, endIndex).replaceAll("[^0-9]", "");
        long number = Long.parseLong(numberString);
        return number;
    }

    public ArrayList<String> extractUpdateBody(Update update) {
        String input;
        ArrayList<String> result = new ArrayList<>();

        if (update.getMessage().hasText()) {
            input = update.getMessage().getText();
            Pattern pattern = Pattern.compile("<(.*?)>");
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                result.add(matcher.group(1));
            }
        }
        return result;
    }

    public String subjectToString (Subject subject) {

        String id = Long.toString(subject.getId());
        String score = Long.toString(subject.getScore());
        String subjMessageText =  EmojiParser.parseToUnicode(
                "ID: " + id + "\n" +
                ":green_book: Название: " + "\n" + subject.getName() + "\n"  + "\n" +
                ":page_with_curl: Описание:" + "\n" + subject.getDescription() + "\n"  + "\n"+
                ":paperclip: Ресурсы: "  + "\n" + subject.getResources() + "\n"  + "\n" +
                ":dart: Баллы: " + score);


        return subjMessageText;
    }

    public String taskToString (Task task) {

        String id = Long.toString(task.getId());
        String taskMessageText = EmojiParser.parseToUnicode(
                "ID: " + id + "\n" +
                ":memo: Задача: " + "\n" + task.getName() + "\n" + "\n" +
                ":page_with_curl: Описание: " + "\n" + task.getDescription() + "\n" + "\n" +
                ":fire_extinguisher: Дедлайн: " + (task.getDeadline() == null ? "": task.getDeadline().toString().replace("T", " ")) + "\n" +
                ":calendar: Создано: " + task.getCreationTime().truncatedTo(ChronoUnit.SECONDS).
                        toString().replace("T", " "));

        return taskMessageText;
    }

}
