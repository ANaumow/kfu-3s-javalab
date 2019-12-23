package ru.naumow.server.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.naumow.context.Component;
import ru.naumow.server.dto.UserDto;
import ru.naumow.server.models.User;
import ru.naumow.server.repositories.UsersRepository;
import ru.naumow.server.services.SignInService;

import java.util.Optional;

public class SignInServiceImpl implements SignInService, Component {
    private UsersRepository usersRepository;

    @Override
    public UserDto signIn(String login, String password) {
        Optional<User> userCandidate = usersRepository.findByLogin(login);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = userCandidate.get().getPassword();
            if (encoder.matches(password, hash)) {
                return UserDto.from(user);
            }
        }
        throw new IllegalArgumentException("incorrect login or password");
    }

    @Override
    public String getName() {
        return "signInService";
    }

    @Override
    public String toString() {
        return "SignInServiceImpl{" +
                "usersRepository=" + usersRepository +
                '}';
    }
}
