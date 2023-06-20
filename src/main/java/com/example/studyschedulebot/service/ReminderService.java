package com.example.studyschedulebot.service;

import com.example.studyschedulebot.entities.Reminder;
import com.example.studyschedulebot.repositories.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ReminderService {

    private ReminderRepository reminderRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void save (Reminder reminder) {
        reminderRepository.save(reminder);
    }

    public void deleteOutdatedReminders () {
        ArrayList<Reminder> reminders = reminderRepository.getRemindersByTimeLessThan(LocalDateTime.now());
        for (Reminder reminder:reminders) {
            reminderRepository.delete(reminder);
        }
    }
    public void deleteRemindersByTaskId (long taskId) {
        reminderRepository.deleteRemindersByTaskId(taskId);
    }

    public  ArrayList<Reminder> getRemindersToSend (LocalDateTime time) {
        LocalDateTime time2 = time.plusMinutes(1);
        return reminderRepository.getReminderByTimeBetween(time, time2);
    }


}
