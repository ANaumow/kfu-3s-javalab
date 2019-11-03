package ru.naumow.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.naumow.common.dto.payloaders.MessageDto;
import ru.naumow.common.models.User;
import ru.naumow.server.services.ServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class ChatClient {
    private Socket         clientSocket;
    private PrintWriter    writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(receiveMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    private String receiveMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Runnable receiveMessagesTask = () -> {
        try {
            while (true) {
                String json = reader.readLine();
                if (json == null) {
                    break;
                }

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
                boolean    hasHeader  = objectNode.has("header");

                if (hasHeader) {
                    String   header    = objectNode.get("header").asText();
                    JsonNode payloader = objectNode.get("payloader");

                    //System.out.println("header " + header);
                    //System.out.println("payloader " + payloader);

                    if (header.equals("Message")) {
                        MessageDto messageDto =
                                mapper.convertValue(payloader, MessageDto.class);
                        String login = messageDto.getLogin();
                        String text  = messageDto.getText();
                        System.out.println(login + "> " + text);
                    }
                } else {
                    System.out.println("History: ");
                    for (JsonNode objNode : objectNode.get("data")) {
                        Integer id = objNode.get("id").asInt();
                        Integer userId = objNode
                                .get("userId")
                                .asInt();
                        String login = ServiceFactory.users()
                                .find(userId)
                                .get()
                                .getLogin();
                        Date date = new Date(objNode.get("date").asLong());
                        String text = objNode.get("text").asText();
                        System.out.println(date.toString() + ": " + login + "> " + text);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

}









