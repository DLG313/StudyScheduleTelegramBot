package com.example.studyschedulebot.repositories;

import com.example.studyschedulebot.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public ArrayList<Subject> getSubjectsByChatId(Long chatId);

    public Subject getSubjectById(Long subjId);

}
