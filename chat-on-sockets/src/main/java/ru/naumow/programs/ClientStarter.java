package ru.naumow.programs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.naumow.client.ChatClient;
import ru.naumow.common.dto.payloaders.GetMessagesCommandDto;
import ru.naumow.common.dto.payloaders.LoginDto;
import ru.naumow.common.dto.payloaders.LogoutDto;
import ru.naumow.common.dto.payloaders.MessageDto;
import ru.naumow.common.dto.Request;

import java.util.Scanner;

public class ClientStarter {

    private static String name;

    public static void main(String[] args) throws JsonProcessingException {

        ChatClient chatClient = new ChatClient();
        chatClient.startConnection("127.0.0.1", 7000);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            String message = scanner.nextLine();

            if (message.isEmpty())
                continue;

            if (message.charAt(0) != '/')
                message = "/message " + message;

            String[] input        = message.split(" ", 2);
            String   command      = input[0];
            String   afterCommand = input.length > 1 ? input[1] : "";

            if (command.equals("/exit"))
                break;

            Request request;
            switch (command) {
                case "/message":
                    MessageDto messageDto = new MessageDto()
                            .setLogin(name)
                            .setText(afterCommand);
                    request = new Request()
                            .setHeader("Message")
                            .setPayloader(messageDto);
                    break;
                case "/login":
                    String[] loginData = afterCommand.split(" ");
                    name = loginData[0];
                    LoginDto loginDto = new LoginDto()
                            .setLogin(loginData[0])
                            .setPassword(loginData[1]);
                    request = new Request()
                            .setHeader("Login")
                            .setPayloader(loginDto);
                    break;
                case "/get_messages":
                    String[] additionData = afterCommand.split(" ");
                    Integer page = Integer.parseInt(additionData[0]);
                    Integer size = Integer.parseInt(additionData[1]);

                    GetMessagesCommandDto getMessagesCDto = new GetMessagesCommandDto()
                            .setCommand("get messages")
                            .setPage(page)
                            .setSize(size);
                    request = new Request()
                            .setHeader("Command")
                            .setPayloader(getMessagesCDto);
                    break;
                case "/logout":
                    LogoutDto logoutDto = new LogoutDto()
                            .setLogin(name);
                    request = new Request()
                            .setHeader("Logout")
                            .setPayloader(logoutDto);
                    break;
                default:
                    System.out.println("can't recognize");
                    continue;
            }
            String json = new ObjectMapper().writeValueAsString(request);
            chatClient.sendMessage(json);
        }
    }

}
