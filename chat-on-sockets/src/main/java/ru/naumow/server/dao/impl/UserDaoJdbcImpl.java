package ru.naumow.server.dao.impl;

import ru.naumow.common.models.User;
import ru.naumow.server.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbcImpl extends AbstractDaoJdbcImpl<User> implements UserDao {

    private RowMapper<User> userRowMapper = resultSet -> new User()
            .setId(resultSet.getInt("id"))
            .setLogin(resultSet.getString("login"))
            .setPassword(resultSet.getString("password"));

    // language=MySQL
    public static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "" +
            "SELECT * FROM user WHERE login = ? AND password = ?";

    // language=MySQL
    public static final String SQL_FIND_USER_BY_LOGIN = "" +
            "SELECT * FROM user WHERE login = ?";

    // language=MySQL
    public static final String SQL_FIND_USER_BY_ID = "" +
            "SELECT * FROM user WHERE id = ?";

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> signIn(String login, String password) {
        try {
            return queryOfOne(userRowMapper, SQL_FIND_USER_BY_LOGIN_AND_PASSWORD, login, password);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public boolean save(User model) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            return queryOfOne(userRowMapper, SQL_FIND_USER_BY_ID, id);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean update(User model) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public boolean delete(Integer id) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return queryOfOne(userRowMapper, SQL_FIND_USER_BY_LOGIN, login);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
