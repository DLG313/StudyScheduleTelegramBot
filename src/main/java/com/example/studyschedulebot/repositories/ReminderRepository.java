package com.example.studyschedulebot.repositories;

import com.example.studyschedulebot.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    public ArrayList<Reminder> getReminderByTimeBetween (LocalDateTime time1, LocalDateTime time2);

    public ArrayList<Reminder> getRemindersByTimeLessThan (LocalDateTime time);

    @Modifying
    @Query(value = "DELETE FROM Reminder r WHERE r.task_id = :task_id", nativeQuery = true)
    void deleteRemindersByTaskId(@Param("task_id") long task_id);
}

