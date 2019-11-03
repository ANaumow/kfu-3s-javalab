package ru.naumow.common.dto.payloaders;

import ru.naumow.common.dto.Payloader;

public class MessageDto extends Payloader {

    private String login;
    private String text;

    public String getLogin() {
        return login;
    }

    public MessageDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageDto setText(String text) {
        this.text = text;
        return this;
    }

}
