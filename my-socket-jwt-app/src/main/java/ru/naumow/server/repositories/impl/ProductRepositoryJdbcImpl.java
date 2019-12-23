package ru.naumow.server.repositories.impl;

import ru.naumow.context.Component;
import ru.naumow.server.models.Product;
import ru.naumow.server.repositories.ProductRepository;
import ru.naumow.server.repositories.RowMapper;
import ru.naumow.server.repositories.connection.MysqlConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
public class ProductRepositoryJdbcImpl implements ProductRepository, Component {

    private MysqlConnectionPool connectionPool;

    private RowMapper<Product> productMapper = set -> new Product.Builder()
            .id(set.getLong("id"))
            .name(set.getString("name"))
            .build();

    //language=MySQL
    private final String GET_ALL_PRODUCTS = "" +
            "SELECT * FROM javalab_chat.product";

    //language=MySQL
    private final String GET_USER_PRODUCTS = "" +
            "SELECT p.id AS id, p.name AS name FROM javalab_chat.buying AS b " +
            "INNER JOIN javalab_chat.product AS p ON b.product_id = p.id WHERE b.user_id = ?";

    //language=MySQL
    private final String BUY_PRODUCT = "" +
            "INSERT INTO buying(USER_ID, PRODUCT_ID) VALUES(?, ?)";

    //language=MySQL
    private final String GET_PRODUCT = "" +
            "SELECT * FROM javalab_chat.product WHERE id = ?";

    //language=MySQL
    private final String ADD_PRODUCT = "" +
            "INSERT INTO product(name) VALUES(?)";

    //language=MySQL
    private final String GET_LAST_ID = "" +
            "SELECT max(id) as id FROM product";


    @Override
    public List<Product> findAll() {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(GET_ALL_PRODUCTS);
            ResultSet resultSet = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(productMapper.map(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Product> getUserProducts(long userId) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(GET_USER_PRODUCTS);
            stmt.setLong(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(productMapper.map(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean buy(long userId, long productId) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(BUY_PRODUCT);
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Product> findOne(Long productId) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(GET_PRODUCT);
            stmt.setLong(1, productId);
            ResultSet set = stmt.executeQuery();
            if (set.next())
                return Optional.of(productMapper.map(set));
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long save(Product product) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(ADD_PRODUCT);
            stmt.setString(1, product.getName());
            if (stmt.executeUpdate() > 0) {
                stmt.close();
                PreparedStatement stmt2 = connectionPool.getPooledConnection()
                        .getConnection().prepareStatement(GET_LAST_ID);
                ResultSet set = stmt2.executeQuery();
                set.next();
                return Long.parseLong(set.getString("id"));
            }
            throw new IllegalStateException("data base wasn't changed");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {
        return "ProductRepositoryJdbcImpl{" +
                "connectionPool=" + connectionPool +
                '}';
    }

    @Override
    public String getName() {
        return "productRepository";
    }
}
