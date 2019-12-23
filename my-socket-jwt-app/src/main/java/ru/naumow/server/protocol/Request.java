package ru.naumow.server.protocol;

public interface Request {

    String getCommand();

    String getParameter(String name);

}
