package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.AccountDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

@WebServlet("/accountAvailability")
public class AccountAvailabilityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String day = req.getParameter("day");
        
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accDAO = injector.getInstance(AccountDAO.class);
        try {
            accDAO.addAccountDay(username, day);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to insert instructor day info to the db", e);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String day = req.getParameter("day");
        
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accDAO = injector.getInstance(AccountDAO.class);
        try {
            accDAO.deleteAccountDay(username, day);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to delete instructor day info to the db", e);
        }
    }
}
