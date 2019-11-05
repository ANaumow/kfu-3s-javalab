package ru.naumow.common.dto.payloads;

import ru.naumow.common.dto.Payload;

public class LogoutDto extends Payload {

    private String login;

    public String getLogin() {
        return login;
    }

    public LogoutDto setLogin(String login) {
        this.login = login;
        return this;
    }
}
