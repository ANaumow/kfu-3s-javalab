package ru.naumow.server.controllers;

import ru.naumow.context.Component;
import ru.naumow.server.dto.ListDto;
import ru.naumow.server.dto.ProductDto;
import ru.naumow.server.protocol.jwt.JwtRequest;
import ru.naumow.server.protocol.jwt.JwtResponse;
import ru.naumow.server.protocol.jwt.token.DecodedJwtToken;
import ru.naumow.server.services.ProductService;
import ru.naumow.server.util.PermissionVerifier;

public class ProductController implements Component {

    private ProductService productService;

    public void getAllProducts(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        ListDto<ProductDto>  productDtoList = productService.getAllProducts();
        response.setData(productDtoList);
    }

    public void buyProduct(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        long userId = Long.parseLong(token.getSubject());
        long productId = Long.parseLong(request.getParameter("id"));
        if (!productService.buyProduct(userId, productId))
            throw new IllegalStateException("have not bought");
    }

    public void getUserProducts(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        long userId = Long.parseLong(token.getSubject());
        response.setData(productService.getUserProducts(userId));
    }

    public void addProduct(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        String name = request.getParameter("name");
        response.setData(productService.addProduct(name));
    }

    @Override
    public String getName() {
        return "productController";
    }

}
