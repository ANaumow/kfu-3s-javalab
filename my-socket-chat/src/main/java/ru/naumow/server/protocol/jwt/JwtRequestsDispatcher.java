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
            case "Sign in":
                authController.signIn(request, response);
                break;
            case "Get all products":
                productController.getAllProducts(request, response);
                break;
            case "Buy product":
                productController.buyProduct(request, response);
                break;
            case "Add product":
                productController.addProduct(request, response);
                break;
            case "Get user products":
                productController.getUserProducts(request, response);
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + request.getCommand());
        }
    }
}
