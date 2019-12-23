package ru.naumow.server.repositories;

import ru.naumow.server.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

}
