package ru.naumow.client;

import ru.naumow.server.dao.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket         clientSocket;
    private PrintWriter    writer;
    private BufferedReader reader;
    private UserDao        userDao;


    public void startConnection(String ip, int port) {
        try {

            clientSocket = new Socket(ip, port);

            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //new Thread(receiveMessagesTask).start();
            new Thread(() -> {
                while (true) {
                    try {
                        String message = reader.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }).start();


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    private Runnable receiveMessagesTask = new Runnable() {
        public void run() {
            while (true) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };


}









