package ru.naumow.server.dao;

import java.util.Optional;

public interface AuthDao<T> {

    Optional<T> signIn(String login, String password);

}
