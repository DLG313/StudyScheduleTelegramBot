package com.example.studyschedulebot.service;

public class CommandText {

    public static String START_TEXT = """
            Привет!
            Я бот, который поможет тебе удобно хранить данные об учебе
            и не обосраться на сессии.
            Нажми /help , чтобы увидеть список комманд с описанием
            """;

    public static String HELP_TEXT = """
            /subjects - выводит список предметов, нажав на кнопки под каждым предметом его можно редактировать или удалить
            Нажав на кнопки под полем ввода, можно заново получить список или добавить новый предмет.
            
            /subjects/add - инструкции по добавлению нового предмета
           
            /tasks/add -инструкции по добавлению новой задачи
            
            /tasks - выводит список задач, с кнопками такая же тема
            
            /thisweektasks - выводит задачи на эту неделю
            
            """;

    public static String SUBJECT_ADD_TEXT = """
            /subjects/add - Если вы хотите добавить новый предмет, скопируйте следующее сообщение и заполните.
            Вы можете вставить туда описание лаб, количество часов, ссылку на журнал и тд.
         
            Сообщение должно содержать команду и 4 блока текста в <>, если какие-то поля вам не требуются, оставьте <> пустыми.
            """;

    public static String SUBJECT_ADD_PATTERN = """
            /subjects/new
            <Название>
            <Описание>
            <Ссылки>
            <Текущее количество баллов>
            """;

    public static String TASK_ADD_TEXT = """
            /tasks/add - Если вы хотите добавить новую задачу, скопируйте следующее сообщение и заполните.
            Вы так же можете установить напоминание на задачу. 
            Сообщение должно содержать команду и 3 блока текста в <>, если какие-то поля вам не требуются, оставьте <> пустыми.
            """;

    public static String TASK_ADD_PATTERN = """
            /tasks/new
            <Название>
            <Описание>
            <Дедлайн в формате dd.MM.yyyy HH:mm>
            """;

}
