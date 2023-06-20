package com.example.studyschedulebot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuKeyboards {
    public static ReplyKeyboardMarkup getUpdateSubjectMarkup (long subjId) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> key = new ArrayList<>();
        String buttonText =
                "/subjects/update \n" + "<ID: " + subjId + ">\n" +
                "<>\n" + "<>\n" + "<>\n" + "<>\n";
        key.add(new KeyboardRow(List.of(new KeyboardButton(buttonText))));
        markup.setKeyboard(key);
        markup.setOneTimeKeyboard(true);
        markup.setIsPersistent(false);

        return markup;
    }

    public static ReplyKeyboardMarkup getStartMenuMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> key = new ArrayList<>();

        KeyboardButton button1 = new KeyboardButton("/subjects/add");
        KeyboardButton button2 = new KeyboardButton("/tasks/add");

        KeyboardRow row = new KeyboardRow();
        row.addAll(List.of(button1, button2));
        key.add(row);
        markup.setKeyboard(key);
        markup.setIsPersistent(false);
        markup.setOneTimeKeyboard(true);

        return markup;
    }
}
