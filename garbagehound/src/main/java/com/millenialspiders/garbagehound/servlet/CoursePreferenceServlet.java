package com.millenialspiders.garbagehound.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.CourseDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/coursePreference")
public class CoursePreferenceServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        String username = req.getParameter("username");
        String courseId = req.getParameter("courseId");

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        CourseDAO courseDAO = injector.getInstance(CourseDAO.class);

        try {
            courseDAO.createPreference(username, courseId);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to insert new course preference to the db", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        String username = req.getParameter("username");
        String courseId = req.getParameter("courseId");

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        CourseDAO courseDAO = injector.getInstance(CourseDAO.class);

        try {
            courseDAO.deletePreference(username, courseId);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to delete course preference from the db", e);
        }
    }

}
