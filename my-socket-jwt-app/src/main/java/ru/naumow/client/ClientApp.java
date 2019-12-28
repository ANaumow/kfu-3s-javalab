package ru.naumow.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.naumow.client.json.DispatchUnit;
import ru.naumow.client.json.JsonDispatcher;
import ru.naumow.client.json.JsonWrapper;
import ru.naumow.client.socket.Client;
import ru.naumow.client.socket.ClientMessageListener;
import ru.naumow.client.socket.TokenSaver;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp implements Runnable, ClientMessageListener {

    private String         token;
    private Client         client;
    private JsonWrapper    wrapper;
    private Scanner        scanner;

    public static void main(String[] args) {
        ClientApp clientApp = new ClientApp();
        clientApp.configure();
        clientApp.run();
    }

    private void configure() {
        this.client = new Client();
        this.client.addClientMessageListener(this);
        this.wrapper = new JsonWrapper();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        client.startConnection("localhost", 7321);

        String[] input;
        String command;
        String[] args;
        String json;

        while (true) {
            try {
                input = scanner.nextLine().split(" ", 2);
                command = input[0];
                switch (command) {
                    case "/sign_in":
                        args = input[1].split(" ");
                        String login = args[0];
                        String password = args[1];
                        json = wrapper.getSignIn(login, password);
                        break;
                    case "/get_products":
                        String token = this.token;
                        json = wrapper.getAllProducts(token);
                        break;
                    case "/add_product":
                        args = input[1].split(" ");
                        token = this.token;
                        String name = args[0];
                        json = wrapper.getAddProduct(name, token);
                        break;
                    case "/buy_product":
                        args = input[1].split(" ");
                        token = this.token;
                        int id = Integer.parseInt(args[0]);
                        json = wrapper.getBuyProduct(id, token);
                        break;
                    case "/get_my_products":
                        token = this.token;
                        json = wrapper.getGetUserProducts(token);
                        break;
                    case "/stop":
                        return;
                    default:
                        throw new IllegalArgumentException("unrecognizable");
                }
                client.sendMessage(json);
            } catch (Exception e) {
                System.out.println("invalid command");
            }

        }

    }

    @Override
    //можно было и получше
    public void onMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode node = mapper.readValue(message, ObjectNode.class);
            JsonNode payload = node.get("payload");
            if (payload != null) {
                JsonNode tokenNode = payload.get("token");
                if (tokenNode != null) {
                    this.token = tokenNode.asText();
                    System.out.println("new token");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        System.out.println(message);
    }
}
