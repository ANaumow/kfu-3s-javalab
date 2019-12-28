package ru.naumow.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.naumow.context.Component;
import ru.naumow.dto.UserDto;
import ru.naumow.models.User;
import ru.naumow.repositories.UsersRepository;
import ru.naumow.services.SignInService;

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
