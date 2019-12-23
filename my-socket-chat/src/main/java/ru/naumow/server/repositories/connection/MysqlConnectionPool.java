package ru.naumow.server.repositories.connection;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import ru.naumow.server.context.Component;

public class MysqlConnectionPool extends MysqlConnectionPoolDataSource implements Component {

    public MysqlConnectionPool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        this.setUrl("jdbc:mysql://localhost:3306/javalab_chat?serverTimezone=Europe/Moscow");
        this.setUser("root");
        this.setPassword("12345");
    }

    @Override
    public String getName() {
        return "mysqlConnectionPool";
    }
}
