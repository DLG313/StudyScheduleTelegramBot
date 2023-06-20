package com.example.studyschedulebot.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name ="creationTime")
    private LocalDateTime creationTime;

    @Column (name ="deadline")
    private LocalDateTime deadline;

    @Column (name = "chatId")
    private long chatId;

    public Task() {
    }

    public Task(String name, String description, LocalDateTime deadline, long chatId) {
        this.name = name;
        this.description = description;
        this.creationTime = LocalDateTime.now();
        this.deadline = deadline;
        this.chatId = chatId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
