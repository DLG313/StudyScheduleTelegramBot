package com.example.studyschedulebot.repositories;

import com.example.studyschedulebot.entities.Subject;
import com.example.studyschedulebot.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public ArrayList<Task> getTasksByChatId(Long chatId);

    public Task getTaskById (Long taskId);

    public Boolean existsTaskById (Long taskId);

}
