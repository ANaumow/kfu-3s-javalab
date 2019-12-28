package ru.naumow.server.socket;

import ru.naumow.server.protocol.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private ServerSocket serverSocket;

    private List<ClientHandler> clients;

    private RequestsHandler<? extends Request, ? extends Response> requestsHandler;

    private StringParser<Request, Response> stringParser;

    public Server(
            RequestsHandler<? extends Request, ? extends Response> requestsHandler,
            StringParser<? extends Request, ? extends Response> stringParser) {
        this.stringParser = (StringParser<Request, Response>) stringParser;
        this.requestsHandler = requestsHandler;
        // Список для работы с многопоточностью
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // запускаем бесконечный цикл
        while (true) {
            try {
                // запускаем обработчик сообщений для каждого подключаемого клиента
                new ClientHandler(this.serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        // связь с одним клиентом
        private Socket         clientSocket;
        private BufferedReader fromClient;
        private PrintWriter    toClient;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
        }

        @Override
        public void run() {
            try {
                // получем входной поток для конкретного клиента
                this.fromClient =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.toClient =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = fromClient.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        break;
                    } else {
                        System.out.println("new request");
                        System.out.println(inputLine);
                        Request request = stringParser.buildRequest(inputLine);
                        Response response = ((RequestsHandler<Request,Response>)requestsHandler).handleRequest(request);
                        this.toClient.println(stringParser.toJson(response));
                        System.out.println("response is " + stringParser.toJson(response));
                    }
                }

                fromClient.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
