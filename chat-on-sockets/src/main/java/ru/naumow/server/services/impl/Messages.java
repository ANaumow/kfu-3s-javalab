package ru.naumow.server.services.impl;

import ru.naumow.common.models.Message;
import ru.naumow.server.ConnectionPool;
import ru.naumow.server.dao.MessageDao;
import ru.naumow.server.dao.impl.MessageDaoJdbcImpl;

import java.util.List;
import java.util.Optional;

public class Messages implements MessageDao {

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public boolean save(Message model) {
        return new MessageDaoJdbcImpl(ConnectionPool.getConnection()).save(model);
    }

    @Override
    public Optional<Message> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Message model) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }


    @Override
    public List<Message> getMessagesOnPage(Integer page, Integer size) {
        return new MessageDaoJdbcImpl(ConnectionPool.getConnection()).getMessagesOnPage(page, size);
    }
}
