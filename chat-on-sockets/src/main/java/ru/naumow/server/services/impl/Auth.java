package ru.naumow.server.services.impl;

import ru.naumow.common.models.User;
import ru.naumow.server.ConnectionPool;
import ru.naumow.server.dao.AuthDao;
import ru.naumow.server.dao.impl.UserDaoJdbcImpl;

import java.util.Optional;

public class Auth implements AuthDao<User> {

    @Override
    public Optional<User> signIn(String login, String password) {
        return new UserDaoJdbcImpl(ConnectionPool.getConnection()).signIn(login, password);
    }

}
