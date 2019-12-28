package ru.naumow.repositories.impl;

import ru.naumow.connection.MysqlConnectionPool;
import ru.naumow.context.ApplicationContext;
import ru.naumow.context.Component;
import ru.naumow.models.User;
import ru.naumow.repositories.RowMapper;
import ru.naumow.repositories.UsersRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
public class UsersRepositoryJdbcImpl implements UsersRepository, Component {

    private MysqlConnectionPool connectionPool;

    private RowMapper<User> userRowMapper = set -> new User.Builder()
            .id(set.getLong("id"))
            .login(set.getString("login"))
            .password(set.getString("password"))
            .role(set.getString("role"))
            .build();

    //language=MySQL
    private final String ADD_USER = "" +
            "INSERT INTO user(login, password, role) VALUES(?, ?, ?)";

    //language=MySQL
    private final String GET_USER = "" +
            "SELECT * FROM javalab_chat.product WHERE id = ?";

    //language=MySQL
    private final String GET_LAST_ID = "" +
            "SELECT max(id) as id from user";

    //language=MySQL
    private final String GET_USER_BY_LOGIN = "" +
            "SELECT * FROM user where login = ?";

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(GET_USER_BY_LOGIN);
            stmt.setString(1, login);
            ResultSet set = stmt.executeQuery();
            if (set.next())
                return Optional.of(userRowMapper.map(set));
            else return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findOne(Long userId) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(GET_USER);
            stmt.setLong(1, userId);
            ResultSet set = stmt.executeQuery();
            set.next();
            return Optional.of(userRowMapper.map(set));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long save(User user) {
        try {
            PreparedStatement stmt = connectionPool.getPooledConnection()
                    .getConnection().prepareStatement(ADD_USER);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            if (stmt.executeUpdate() > 0) {
                PreparedStatement stmt2 = connectionPool.getPooledConnection()
                        .getConnection().prepareStatement(GET_LAST_ID);
                ResultSet set = stmt2.executeQuery();
                set.next();
                return Long.valueOf(set.getString("id"));
            }
            throw new IllegalStateException("data base wasn't changed");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getName() {
        return "usersRepository";
    }

    @Override
    public String toString() {
        return "UsersRepositoryFakeImpl{" +
                "ru.naumow.connection=" + connectionPool +
                '}';
    }

    @Override
    public void saveContext(ApplicationContext context) {
        this.connectionPool = context.getAttribute("connectionPool");
    }

}
