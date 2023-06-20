package com.example.studyschedulebot.service;

import com.example.studyschedulebot.entities.Subject;
import com.example.studyschedulebot.keyboards.InlineKeyboards;
import com.example.studyschedulebot.repositories.SubjectRepository;
import javassist.expr.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;
    private TextParserService textParserService;



    @Autowired
    public SubjectService(SubjectRepository subjectRepository, TextParserService textParserService) {
        this.textParserService = textParserService;
        this.subjectRepository = subjectRepository;
    }

    public ArrayList<BotApiMethod<?>> getUsersSubjectList(Long chatId) {
        ArrayList<BotApiMethod<?>> subjectListToSend = new ArrayList<>();

        ArrayList<Subject> subjectListToConvert = subjectRepository.getSubjectsByChatId(chatId);

        for(Subject subject : subjectListToConvert) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(textParserService.subjectToString(subject));
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(InlineKeyboards.getSubjectsInlineMarkup());
            subjectListToSend.add(sendMessage);
        }

        return subjectListToSend;
    }


    public Subject createSubjectOutOfUpdate(Update update) throws TelegramApiException {
        ArrayList<String> updateBody = textParserService.extractUpdateBody(update);
        Subject subject;


        if (updateBody.size() == 4) {
            String name = updateBody.get(0);
            String desc = updateBody.get(1);
            String res = updateBody.get(2);
            String score = updateBody.get(3).replaceAll("[^0-9]", "");


            subject= new Subject(name, desc, res,
                    score != "" ? Integer.parseInt(updateBody.get(3)) : 0,
                    update.getMessage().getChatId());}
        else{
            throw new TelegramApiException("Ошибка при создании предмета");
        }

        save(subject);
        return subject;
    }

    public void deleteSubjectByID(long id) throws TelegramApiException {
        Subject subject = subjectRepository.getSubjectById(id);

        if (subject != null){
            subjectRepository.delete(subject);
        }

        else{
            throw new TelegramApiException("Ошибка при удалении предмета");
        }
    }

    public void updateSubjectByID (Update update) throws TelegramApiException {
        String msgText = update.getMessage().getText();
        long id = textParserService.getIdOutOfMessage(msgText);
        ArrayList<String> body = textParserService.extractUpdateBody(update);

        Subject subject = subjectRepository.getSubjectById(id);

        if (subject != null){
            subject.setName(body.get(1));
            subject.setDescription(body.get(2));
            subject.setResources(body.get(3));
            subject.setScore(Integer.parseInt(body.get(4)));
            save(subject);
        }

        else{
            throw new TelegramApiException("Ошибка при обновлении предмета");
        }

    }

    public void save (Subject subject) {
        subjectRepository.save(subject);
    }
}
