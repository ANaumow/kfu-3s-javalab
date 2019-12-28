package ru.naumow.controller;

import ru.naumow.context.ApplicationContext;
import ru.naumow.presenters.Presenter;
import ru.naumow.presenters.PresenterFactory;
import ru.naumow.services.ProductService;
import ru.naumow.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductController extends HttpServlet {

    private ProductService   productService;
    private PresenterFactory presenterFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("appContext");
        this.productService = context.getComponent(ProductService.class, "signInService");
        this.presenterFactory = context.getAttribute("presenterFactory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = (int) req.getSession().getAttribute("user_id");
        String role = (String) req.getSession().getAttribute("role");
        String userName = (String) req.getSession().getAttribute("user_name");

        Presenter presenter = presenterFactory.createPresenter("sign_in");
        //presenter.put("user");
        presenter.render(resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /*public void getAllProducts(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        ListDto<ProductDto>  productDtoList = productService.getAllProducts();
        response.setData(productDtoList);
    }

    public void buyProduct(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        long userId = Long.parseLong(token.getSubject());
        long productId = Long.parseLong(request.getParameter("id"));
        if (!productService.buyProduct(userId, productId))
            throw new IllegalStateException("have not bought");
    }

    public void getUserProducts(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        long userId = Long.parseLong(token.getSubject());
        response.setData(productService.getUserProducts(userId));
    }

    public void addProduct(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        String name = request.getParameter("name");
        response.setData(productService.addProduct(name));
    }*/


}
