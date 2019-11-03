package ru.naumow.common.models;

import java.sql.Timestamp;

public class Message {

    private Integer   id;
    private Integer   userId;
    private Timestamp date;
    private String    text;

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Message setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Timestamp getDate() {
        return date;
    }

    public Message setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }
}
