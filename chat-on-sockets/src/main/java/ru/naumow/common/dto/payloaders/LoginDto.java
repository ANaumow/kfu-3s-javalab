package ru.naumow.common.dto.payloaders;

import ru.naumow.common.dto.Payloader;

public class LoginDto extends Payloader {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public LoginDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
