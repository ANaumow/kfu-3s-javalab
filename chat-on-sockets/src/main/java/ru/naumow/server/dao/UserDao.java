package ru.naumow.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    Connection connection;

    // language=MySQL
    public static final String SQL_GET_USER_ID_BY_NAME_AND_PASSWORD = "" +
            "SELECT iduser FROM user WHERE name = ? AND password = ?";

    // language=MySQL
    public static final String SQL_GET_USER_NAME_BY_ID = "" +
            "SELECT name FROM user WHERE iduser = ?";

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Integer> signIn(String name, String password) throws IllegalArgumentException {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_USER_ID_BY_NAME_AND_PASSWORD);
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return Optional.of(resultSet.getInt("iduser"));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<String> getNameById(Integer id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_USER_NAME_BY_ID);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return Optional.of(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
