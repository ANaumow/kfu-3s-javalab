package ru.naumow.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionPool {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (Objects.isNull(connection) || connection.isClosed())
                instantiate();
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void instantiate() {
        try {
            String dbUrl      = "jdbc:mysql://localhost:3306/javalab_chat?serverTimezone=UTC";
            String dbUsername = "root";
            String dbPassword = "12345";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}