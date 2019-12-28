package ru.naumow.listeners;

import ru.naumow.connection.MysqlConnectionPool;
import ru.naumow.context.ApplicationContext;
import ru.naumow.context.ApplicationContextReflectionBased;
import ru.naumow.presenters.PresenterFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ApplicationContextReflectionBased();

        PresenterFactory factory = new PresenterFactory(sce.getServletContext());
        MysqlConnectionPool connectionPool = new MysqlConnectionPool();

        factory.loadProperties(sce.getServletContext().getRealPath("WEB-INF/properties/templates.properties"));
        connectionPool.loadProperties(sce.getServletContext().getRealPath("WEB-INF/properties/db.properties"));

        context.setAttribute("presenterFactory", factory);
        context.setAttribute("connectionPool", connectionPool);

        sce.getServletContext().setAttribute("appContext", context);
    }
}
