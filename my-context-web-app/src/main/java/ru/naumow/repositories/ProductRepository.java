package ru.naumow.repositories;

import ru.naumow.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();

    List<Product> getUserProducts(long userId);

    boolean buy(long userId, long productId);

}
