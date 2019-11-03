package ru.naumow.server.services.impl;

import ru.naumow.common.models.User;
import ru.naumow.server.ConnectionPool;
import ru.naumow.server.dao.impl.UserDaoJdbcImpl;

import java.util.Optional;

public class Users {

    public Optional<User> getUserByLogin(String login) {
        return new UserDaoJdbcImpl(ConnectionPool.getConnection()).findByLogin(login);
    }

    public Optional<User> find(Integer id) {
        return new UserDaoJdbcImpl(ConnectionPool.getConnection()).find(id);
    }

}
