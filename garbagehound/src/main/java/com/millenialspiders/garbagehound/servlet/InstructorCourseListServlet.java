package com.millenialspiders.garbagehound.servlet;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.AccountDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructCourseList")
public class InstructorCourseListServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accDAO = injector.getInstance(AccountDAO.class);

        try {
            accDAO.getCourseInstructors();
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to get instructor list from db", e);
        }
    }

}
