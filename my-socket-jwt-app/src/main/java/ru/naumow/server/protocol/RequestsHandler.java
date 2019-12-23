package ru.naumow.server.protocol;

public interface RequestsHandler<I extends Request, O extends Response> {

    O handleRequest(I request);

}
