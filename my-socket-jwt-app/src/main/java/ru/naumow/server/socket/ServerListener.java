package ru.naumow.server.socket;

import ru.naumow.server.protocol.Request;
import ru.naumow.server.protocol.Response;

public interface ServerListener {

    void onMessage(Request request, Response response);

}
