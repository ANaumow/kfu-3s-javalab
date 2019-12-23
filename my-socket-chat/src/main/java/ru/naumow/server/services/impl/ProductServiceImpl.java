package ru.naumow.server.services.impl;

import ru.naumow.server.context.Component;
import ru.naumow.server.dto.ListDto;
import ru.naumow.server.dto.ProductDto;
import ru.naumow.server.models.Product;
import ru.naumow.server.repositories.ProductRepository;
import ru.naumow.server.repositories.UsersRepository;
import ru.naumow.server.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService, Component {
    private ProductRepository productRepository;
    private UsersRepository   usersRepository;

    @Override
    public ListDto<ProductDto> getAllProducts() {
        return ListDto.fromList(productRepository.findAll(), ProductDto::from);
    }

    @Override
    public boolean buyProduct(long userId, long productId) {
        List<Product> productList = productRepository.getUserProducts(userId);
        for (Product product : productList)
            if (product.getId() == productId)
                throw new IllegalStateException("you have already bought it");
        return productRepository.buy(userId, productId);
    }

    @Override
    public ProductDto addProduct(String name) {
        Product product = new Product.Builder().name(name).build();
        long id = productRepository.save(product);
        System.out.println(id);
        product.setId(id);
        return ProductDto.from(product);
    }

    @Override
    public ListDto<ProductDto> getUserProducts(long userId) {
        return ListDto.fromList(productRepository.getUserProducts(userId), ProductDto::from);
    }

    @Override
    public String getName() {
        return "productService";
    }

    @Override
    public String toString() {
        return "ProductServiceImpl{" +
                "productRepository=" + productRepository +
                '}';
    }

}
