package ru.naumow.server.protocol.jwt;

import ru.naumow.server.controllers.AuthController;
import ru.naumow.server.controllers.ProductController;
import ru.naumow.server.protocol.RequestDispatcher;

public class JwtRequestsDispatcher implements RequestDispatcher<JwtRequest, JwtResponse> {

    private ProductController productController;
    private AuthController    authController;

    public JwtRequestsDispatcher(ProductController productController, AuthController authController) {
        this.productController = productController;
        this.authController = authController;
    }

    @Override
    public void doDispatch(JwtRequest request, JwtResponse response) {
        switch (request.getCommand()) {
            case "Sign in" -> authController.signIn(request, response);
            case "Get all products" -> productController.getAllProducts(request, response);
            case "Buy product" -> productController.buyProduct(request, response);
            case "Add product" -> productController.addProduct(request, response);
            case "Get user products" -> productController.getUserProducts(request, response);
            default -> throw new IllegalArgumentException("Unknown command: " + request.getCommand());
        }
    }
}
