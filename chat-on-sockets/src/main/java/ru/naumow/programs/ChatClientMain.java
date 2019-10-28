package ru.naumow.programs;


import ru.naumow.client.ChatClient;
import ru.naumow.server.dao.UserDao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class ChatClientMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);

        String dbUrl      = "jdbc:mysql://localhost:3306/javalab_chat?serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "12345";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        UserDao userDao = new UserDao(connection);

        System.out.println("your name:");
        String name     = sc.nextLine();
        System.out.println("your password:");
        String password = sc.nextLine();

        Optional<Integer> id = userDao.signIn(name, password);

        if (id.isPresent()) {
            System.out.println("Welcome to the club buddy");
            ChatClient chatClient = new ChatClient();
            chatClient.startConnection("127.0.0.1", 7000);
            while (true) {
                String message = sc.nextLine();
                chatClient.sendMessage(id.get() + "-" + message);
            }

        } else {
            connection.close();
            System.out.println("Login or password are incorrect");
        }


    }
}
