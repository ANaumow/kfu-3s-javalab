package ru.naumow.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.naumow.common.dto.Request;
import ru.naumow.common.dto.payloaders.GetMessagesCommandDto;
import ru.naumow.common.dto.payloaders.LoginDto;
import ru.naumow.common.dto.payloaders.LogoutDto;
import ru.naumow.common.dto.payloaders.MessageDto;
import ru.naumow.common.models.Message;
import ru.naumow.common.models.User;
import ru.naumow.server.services.ServiceFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiClientServer {
    private ServerSocket        serverSocket;
    private List<ClientHandler> clients;

    private String serverName = "server";

    public MultiClientServer() {
        clients = new ArrayList<>();
    }

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        try {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    private class ClientHandler extends Thread {
        private final Socket         clientSocket;
        private       BufferedReader reader;
        private       PrintWriter    writer;

        private boolean isEntered;

        public ClientHandler(Socket clientSocket) {
            System.out.println("New client!");
            this.clientSocket = clientSocket;
            this.isEntered = false;
            try {
                this.reader =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer =
                        new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("In run");
            try (clientSocket) {

                while (true) {
                    String json = reader.readLine();
                    System.out.println(json);

                    ObjectMapper mapper     = new ObjectMapper();
                    ObjectNode   objectNode = mapper.readValue(json, ObjectNode.class);
                    String       header     = objectNode.get("header").asText();
                    JsonNode     payloader  = objectNode.get("payloader");

                    if (!isEntered) {
                        if (header.equals("Login")) {
                            LoginDto loginDto = mapper
                                    .convertValue(payloader, LoginDto.class);
                            Optional<User> user = ServiceFactory.auth()
                                    .signIn(loginDto.getLogin(), loginDto.getPassword());
                            if (user.isPresent()) {
                                // if logged in
                                isEntered = true;
                                System.out.println(user.get().getLogin() + " logged in");
                                MessageDto messageDto = new MessageDto()
                                        .setLogin(serverName)
                                        .setText("Welcome to the club!");
                                Request request = new Request()
                                        .setHeader("Message")
                                        .setPayloader(messageDto);
                                writer.println(mapper.writeValueAsString(request));
                            } else {
                                // if did not log in
                                System.out.println("bad attempt to log in");
                                MessageDto messageDto = new MessageDto()
                                        .setLogin(serverName)
                                        .setText("Incorrect login or password");
                                Request request = new Request()
                                        .setHeader("Message")
                                        .setPayloader(messageDto);
                                writer.println(mapper.writeValueAsString(request));
                            }
                        } else {
                            continue;
                        }
                    }


                    switch (header) {
                        case "Message":
                            MessageDto messageDto = mapper
                                    .convertValue(payloader, MessageDto.class);
                            User user = ServiceFactory.users()
                                    .getUserByLogin(messageDto.getLogin())
                                    .get();
                            Message message = new Message()
                                    .setUserId(user.getId())
                                    .setText(messageDto.getText());
                            ServiceFactory.messages()
                                    .save(message);
                            for (ClientHandler client : clients) {
                                if (client.isEntered)
                                    client.writer.println(json);
                            }
                            break;
                        case "Logout":
                            LogoutDto logoutDto = mapper
                                    .convertValue(payloader, LogoutDto.class);
                            System.out.println(logoutDto.getLogin() + " is logged out");
                            isEntered = false;
                            break;
                        case "Command":
                            GetMessagesCommandDto getMsgsCmndDto = mapper
                                    .convertValue(payloader, GetMessagesCommandDto.class);
                            List<Message> messages = ServiceFactory.messages()
                                    .getMessagesOnPage(getMsgsCmndDto.getPage(), getMsgsCmndDto.getSize());
                            ObjectNode node = mapper.createObjectNode();
                            ArrayNode arrayNode = node.putArray("data");
                            for (Message msg : messages) {
                                arrayNode.add(mapper.convertValue(msg, ObjectNode.class));
                            }
                            System.out.println(node);
                            writer.println(node.toString());
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
