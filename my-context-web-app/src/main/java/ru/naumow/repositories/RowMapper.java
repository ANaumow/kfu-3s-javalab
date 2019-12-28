package ru.naumow.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T map(ResultSet set) throws SQLException;

}
