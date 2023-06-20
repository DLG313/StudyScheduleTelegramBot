package com.example.studyschedulebot.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "resources")
    private String resources;

    @Column (name = "score")
    private Integer score;

    @Column (name = "chatId")
    private long chatId;

    public Subject(long chatId) {
        this.name = "";
        this.description = "";
        this.resources = "";
        this.score = 0;
        this.chatId = chatId;
    }

    public Subject(String name, String description, String resources, int score, long chatId) {
        this.name = name;
        this.description = description;
        this.resources = resources;
        this.score = score;
        this.chatId = chatId;
    }

    public Subject() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
