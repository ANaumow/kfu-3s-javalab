package ru.naumow.server;

import ru.naumow.context.ApplicationContext;
import ru.naumow.context.ApplicationContextReflectionBased;
import ru.naumow.server.controllers.AuthController;
import ru.naumow.server.controllers.ProductController;
import ru.naumow.server.protocol.RequestsHandler;
import ru.naumow.server.protocol.jwt.*;
import ru.naumow.server.protocol.jwt.token.JwtTokenCoderAuth0Based;
import ru.naumow.server.socket.Server;

public class ServerApp implements Runnable {

    public static void main(String[] args) {
        new ServerApp().run();
    }

    @Override
    public void run() {
        String tokenSecret = "I like pizza";
        ApplicationContext context = new ApplicationContextReflectionBased();
        context.setAttribute("secret", tokenSecret);
        ProductController productController
                = context.getComponent(ProductController.class, "productController");
        AuthController authController
                = context.getComponent(AuthController.class, "authController");
        RequestsHandler<JwtRequest, JwtResponse> requestsHandler
                = new JwtRequestsHandler(new JwtRequestsDispatcher(productController, authController));
        Server server
                = new Server(requestsHandler, new JsonParserJacksonBased(new JwtTokenCoderAuth0Based(tokenSecret)));
        server.start(7321);
    }
}
