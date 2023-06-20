package com.example.studyschedulebot.keyboards;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboards {

    public static InlineKeyboardMarkup getSubjectsInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //buttons
        InlineKeyboardButton updateSubjButton = new InlineKeyboardButton();
        updateSubjButton.setText(EmojiParser.parseToUnicode(":pencil: Редактировать"));
        updateSubjButton.setCallbackData("/subjects/update");

        InlineKeyboardButton deleteSubjButton = new InlineKeyboardButton();
        deleteSubjButton.setText(EmojiParser.parseToUnicode(":wastebasket: Удалить"));
        deleteSubjButton.setCallbackData("/subjects/delete");

        //rows
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        buttonsRow.add(updateSubjButton);
        buttonsRow.add(deleteSubjButton);

        //rows list
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(buttonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;

    }

    public static InlineKeyboardMarkup getTasksInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //buttons
        InlineKeyboardButton updateTaskButton = new InlineKeyboardButton();
        updateTaskButton.setText(EmojiParser.parseToUnicode(":pencil: Редактировать"));
        updateTaskButton.setCallbackData("/tasks/update");

        InlineKeyboardButton deleteTaskButton = new InlineKeyboardButton();
        deleteTaskButton.setText(EmojiParser.parseToUnicode(":wastebasket: Удалить"));
        deleteTaskButton.setCallbackData("/tasks/delete");

        InlineKeyboardButton setTimerButton = new InlineKeyboardButton();
        setTimerButton.setText(EmojiParser.parseToUnicode(":alarm_clock: Установить напоминание"));
        setTimerButton.setCallbackData("/tasks/timer");

        //rows
        List<InlineKeyboardButton> updDelRow = new ArrayList<>();
        List<InlineKeyboardButton> timerRow = new ArrayList<>();
        timerRow.add(setTimerButton);
        updDelRow.add(updateTaskButton);
        updDelRow.add(deleteTaskButton);

        //rows list
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(timerRow);
        rowList.add(updDelRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;

    }

    public static InlineKeyboardMarkup setTimerInlineMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton setOneHour = new InlineKeyboardButton();
        setOneHour.setText(EmojiParser.parseToUnicode(":clock1: За час"));
        setOneHour.setCallbackData("/settimer/1");

        InlineKeyboardButton setThreeHours = new InlineKeyboardButton();
        setThreeHours.setText(EmojiParser.parseToUnicode(":clock3: За 3 часа"));
        setThreeHours.setCallbackData("/settimer/3");

        InlineKeyboardButton setSixHours = new InlineKeyboardButton();
        setSixHours.setText(EmojiParser.parseToUnicode(":clock6: За 6 часов"));
        setSixHours.setCallbackData("/settimer/6");

        InlineKeyboardButton setTwelveHours = new InlineKeyboardButton();
        setTwelveHours.setText(EmojiParser.parseToUnicode(":clock12: За 12 часов"));
        setTwelveHours.setCallbackData("/settimer/12");

        InlineKeyboardButton setOneDay = new InlineKeyboardButton();
        setOneDay.setText(EmojiParser.parseToUnicode(":sun_with_face: За 24 часа"));
        setOneDay.setCallbackData("/settimer/24");

        List<InlineKeyboardButton> firstRow = List.of(setOneHour, setThreeHours, setSixHours);
        List<InlineKeyboardButton> secondRow = List.of(setTwelveHours, setOneDay);


        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(firstRow);
        rowList.add(secondRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
