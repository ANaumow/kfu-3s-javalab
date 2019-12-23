package ru.naumow.server.dto;

import ru.naumow.server.models.Product;

public class ProductDto implements Dto {
    private Long   id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO: вместо конструктора Builder
    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} ";
    }

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName());
    }

}
