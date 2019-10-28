package ru.naumow.server.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer {
    private ServerSocket        serverSocket;
    private List<ClientHandler> clients;
    private MessageDao messageDao;
    private UserDao userDao;

    public MultiClientServer() {
        clients = new ArrayList<ClientHandler>();
        String dbUrl      = "jdbc:mysql://localhost:3306/javalab_chat?serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "12345";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            messageDao = new MessageDao(connection);
            userDao = new UserDao(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        for (; ; ) {
            try {
                ClientHandler handler =
                        new ClientHandler(serverSocket.accept());
                handler.start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket         clientSocket;
        private BufferedReader reader;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            clients.add(this);
            System.out.println("New client!");
        }

        @Override
        public void run() {
            System.out.println("in run");
            try {
                reader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    int id = Integer.parseInt(line.substring(0, line.indexOf("-")));
                    String mess = line.substring(line.indexOf("-") + 1);
                    messageDao.addMessage(id, mess);

                    String name = userDao.getNameById(id).orElse("unknown");

                    System.out.println(line);
                    for (ClientHandler client : clients) {
                        PrintWriter writer = new PrintWriter(
                                client.clientSocket.getOutputStream(), true);
                        writer.println(name + ": " + mess);
                    }
                }
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
