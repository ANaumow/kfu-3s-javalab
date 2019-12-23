package ru.naumow.server.models;

public class Product {
    private Long   id;
    private String name;

    private Product(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
    }

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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private Long   id;
        private String name;

        public Long getId() {
            return id;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

}
