package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.AccountDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Preconditions.checkNotNull(username, "username must be provided");
        Preconditions.checkNotNull(password, "password must be provided");

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accountDAO = injector.getInstance(AccountDAO.class);

        try {
            boolean accountExists = accountDAO.isUserExists(username, password);
            ServletUtils.writeSuccess(resp, accountExists);
        } catch (SQLException e) {
            throw new ServletException("Unable to login", e);
        }
    }
}
