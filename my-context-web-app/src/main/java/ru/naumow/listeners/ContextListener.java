package ru.naumow.listeners;

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
        context.setAttribute("presenterFactory", new PresenterFactory(sce.getServletContext()));
        sce.getServletContext().setAttribute("appContext", context);
    }
}
