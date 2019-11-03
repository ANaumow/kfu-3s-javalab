package ru.naumow.server;

public class ServerStarter {

    public static void main(String[] args) {
        MultiClientServer multiClientServer = new MultiClientServer();
        multiClientServer.start(7000);
    }

}
