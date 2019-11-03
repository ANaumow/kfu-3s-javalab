package ru.naumow.server.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    List<T> findAll();

    boolean save(T model);
    Optional<T> find(Integer id);
    boolean update(T model);
    boolean delete(Integer id);

}
