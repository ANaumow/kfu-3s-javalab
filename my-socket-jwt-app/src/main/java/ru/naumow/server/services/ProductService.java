package ru.naumow.server.services;

import ru.naumow.server.dto.ListDto;
import ru.naumow.server.dto.ProductDto;

public interface ProductService {
    ListDto<ProductDto> getAllProducts();
    boolean buyProduct(long userId, long productId);
    ProductDto addProduct(String name);
    ListDto<ProductDto> getUserProducts(long userId);
}
