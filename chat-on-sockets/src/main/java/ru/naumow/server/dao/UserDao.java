package ru.naumow.server.dao;

import ru.naumow.common.models.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User>, AuthDao<User> {

    Optional<User> findByLogin(String login);

}
