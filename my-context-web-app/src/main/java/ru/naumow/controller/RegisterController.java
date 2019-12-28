package ru.naumow.controller;

import ru.naumow.context.ApplicationContext;
import ru.naumow.presenters.Presenter;
import ru.naumow.presenters.PresenterFactory;
import ru.naumow.services.AuthService;
import ru.naumow.services.ProductService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private AuthService authService;
    private PresenterFactory presenterFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("appContext");
        this.authService = context.getComponent(AuthService.class, "authService");
        this.presenterFactory = context.getAttribute("presenterFactory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Presenter presenter = presenterFactory.createPresenter("register");
        presenter.render(resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        this.authService.signUp(login, password);
        resp.sendRedirect(req.getContextPath() + "/sign_in");
    }
}
