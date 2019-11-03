package ru.naumow.common.dto.payloaders;

import ru.naumow.common.dto.Payloader;

public class LogoutDto extends Payloader {

    private String login;

    public String getLogin() {
        return login;
    }

    public LogoutDto setLogin(String login) {
        this.login = login;
        return this;
    }
}
