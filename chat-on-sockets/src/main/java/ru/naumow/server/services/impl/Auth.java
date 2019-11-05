package ru.naumow.server.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.naumow.common.models.User;
import ru.naumow.server.ConnectionPool;
import ru.naumow.server.dao.AuthDao;
import ru.naumow.server.dao.impl.UserDaoJdbcImpl;

import java.util.Optional;

public class Auth implements AuthDao<User> {

    @Override
    public Optional<User> signIn(String login, String password) {
        //return new UserDaoJdbcImpl(ConnectionPool.getConnection()).signIn(login, password);
        Optional<User> user = new UserDaoJdbcImpl(ConnectionPool.getConnection()).findByLogin(login);

        if (user.isPresent()) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();

    }

}
