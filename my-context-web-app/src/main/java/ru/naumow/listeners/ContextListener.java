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
        PresenterFactory factory = new PresenterFactory(sce.getServletContext());
        factory.configure(sce.getServletContext().getRealPath("templates.properties"));
        context.setAttribute("presenterFactory", factory);
        sce.getServletContext().setAttribute("appContext", context);
    }
}
