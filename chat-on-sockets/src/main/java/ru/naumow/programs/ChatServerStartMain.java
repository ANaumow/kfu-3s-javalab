package ru.naumow.programs;


import ru.naumow.server.MultiClientServer;

public class ChatServerStartMain {
    public static void main(String[] args) {
        MultiClientServer multiClientServer =
                new MultiClientServer();
        multiClientServer.start(7000);
    }
}
