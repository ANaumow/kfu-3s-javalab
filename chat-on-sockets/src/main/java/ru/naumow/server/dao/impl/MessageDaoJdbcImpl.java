package ru.naumow.server.dao.impl;

import ru.naumow.common.models.Message;
import ru.naumow.server.dao.AbstractDaoJdbcImpl;
import ru.naumow.server.dao.MessageDao;
import ru.naumow.server.dao.RowMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MessageDaoJdbcImpl extends AbstractDaoJdbcImpl<Message> implements MessageDao {

    private RowMapper<Message> messageRowMapper = resultSet -> new Message()
            .setId(resultSet.getInt("id"))
            .setUserId(resultSet.getInt("user_id"))
            .setDate(resultSet.getTimestamp("date"))
            .setText(resultSet.getString("text"));

    //language=MySQL
    public static final String SQL_SAVE_MESSAGE_NOW = "" +
            "INSERT INTO message(user_id, date, text) VALUES(?, NOW(), ?)";

    //language=MySQL
    public static final String SQL_FIND_MESSAGE_ON_PAGE_BY_SIZE = "" +
            "SELECT * FROM message LIMIT ? OFFSET ?;";


    public MessageDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<Message> findAll() {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public boolean save(Message model) {
        try {
            return update(SQL_SAVE_MESSAGE_NOW, model.getUserId(), model.getText()) > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Message> find(Integer id) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public boolean update(Message model) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public boolean delete(Integer id) {
        throw new IllegalStateException("not implemented yet");
    }

    @Override
    public List<Message> getMessagesOnPage(Integer page, Integer size) {
        try {
            return queryOfMany(messageRowMapper, SQL_FIND_MESSAGE_ON_PAGE_BY_SIZE, size, (page - 1) * size);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
