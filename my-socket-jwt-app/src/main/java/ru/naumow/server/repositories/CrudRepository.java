package ru.naumow.server.repositories;

import java.util.Optional;

public interface CrudRepository<T,ID> {
    Optional<T> findOne(ID id);
    
    ID save(T t);

    // other CRUD methods but they are not needed yet
}
