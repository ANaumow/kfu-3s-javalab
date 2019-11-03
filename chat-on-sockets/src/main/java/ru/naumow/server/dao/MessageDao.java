package ru.naumow.server.dao;

import ru.naumow.common.models.Message;

import java.util.List;

public interface MessageDao extends CrudDao<Message> {

    List<Message> getMessagesOnPage(Integer page, Integer size);

}
