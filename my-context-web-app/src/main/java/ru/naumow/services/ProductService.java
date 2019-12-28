package ru.naumow.services;

import ru.naumow.dto.ListDto;
import ru.naumow.dto.ProductDto;

public interface ProductService {
    ListDto<ProductDto> getAllProducts();
    boolean buyProduct(long userId, long productId);
    ProductDto addProduct(String name);
    ListDto<ProductDto> getUserProducts(long userId);
}
