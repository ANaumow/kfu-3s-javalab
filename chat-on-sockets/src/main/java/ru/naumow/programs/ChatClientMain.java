package ru.naumow.programs;


import ru.naumow.client.ChatClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class ChatClientMain {
    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();
        chatClient.startConnection("127.0.0.1", 7000);

        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            chatClient.sendMessage(message);
        }

    }
}
