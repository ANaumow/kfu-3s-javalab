package ru.naumow.repositories;

import ru.naumow.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

}
