package ru.naumow.connection;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import ru.naumow.context.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MysqlConnectionPool extends MysqlConnectionPoolDataSource{

    public MysqlConnectionPool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void loadProperties(String propertiesPath) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(propertiesPath)));
            this.setUrl(properties.getProperty("url"));
            this.setUser(properties.getProperty("user"));
            this.setPassword(properties.getProperty("password"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
