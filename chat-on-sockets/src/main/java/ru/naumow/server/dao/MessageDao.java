package ru.naumow.server.dao;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDao {

    Connection connection;

    public MessageDao(Connection connection) {
        this.connection = connection;
    }

    //language=MySQL
    public static final String SQL_ADD_MESSAGE_NOW = "" +
            "INSERT INTO history(user_id, date, message) VALUES(?, NOW(), ?)";

    public void addMessage(Integer userId, String message) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_ADD_MESSAGE_NOW);
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
