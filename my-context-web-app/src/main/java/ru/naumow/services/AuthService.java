package ru.naumow.services;

import ru.naumow.dto.UserDto;

public interface AuthService {
    UserDto signIn(String login, String password);

    void signUp(String login, String password);
}
