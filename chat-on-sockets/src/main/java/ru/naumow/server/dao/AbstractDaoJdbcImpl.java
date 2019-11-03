package ru.naumow.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDaoJdbcImpl<T> {

    private final Connection connection;

    public AbstractDaoJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    protected int update(String sql, Object... objects) throws SQLException {
        try (connection) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++)
                stmt.setObject(i + 1, objects[i]);
            return stmt.executeUpdate();
        }
    }

    private ResultSet query(String sql, Object... objects) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++)
            stmt.setObject(i + 1, objects[i]);
        return stmt.executeQuery();
    }

    protected List<T> queryOfMany(RowMapper<T> mapper, String sql, Object... objects) throws SQLException {
        try (connection) {
            ResultSet resultSet = query(sql, objects);
            List<T>   results   = new ArrayList<>();
            while (resultSet.next())
                results.add(mapper.map(resultSet));
            return results;
        }

    }

    protected Optional<T> queryOfOne(RowMapper<T> mapper, String sql, Object... objects) throws SQLException {
        try (connection) {
            ResultSet resultSet = query(sql, objects);
            if (resultSet.next())
                return Optional.of(mapper.map(resultSet));
            else return Optional.empty();
        }
    }

}
