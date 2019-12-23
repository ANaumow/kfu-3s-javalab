package ru.naumow.server.services;

import ru.naumow.server.dto.UserDto;

public interface SignInService {
    UserDto signIn(String login, String password);
}
