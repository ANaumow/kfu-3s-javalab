package ru.naumow.services;

import ru.naumow.dto.UserDto;

public interface SignInService {
    UserDto signIn(String login, String password);
}
