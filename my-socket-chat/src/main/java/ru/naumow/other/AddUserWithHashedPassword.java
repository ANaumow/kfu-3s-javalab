package ru.naumow.other;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserWithHashedPassword {

    public static void main(String[] args) {
        String login = "user1";
        String password = "12345";
        String role = "user";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javalab_chat?serverTimezone=Europe/Moscow", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO javalab_chat.user(login, password, role) VALUES (?, ?, ?)");
            statement.setString(1, login);
            statement.setString(2, encoder.encode(password));
            statement.setString(3, role);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
