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
import com.millenialspiders.garbagehound.common.db.CourseDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

@WebServlet("/course")
public class CourseManagementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        String courseName = req.getParameter("courseName");
        
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        CourseDAO courseDAO = injector.getInstance(CourseDAO.class);
        
        try {
            courseDAO.createCourse(courseId, courseName);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to insert new course to the db", e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        CourseDAO courseDAO = injector.getInstance(CourseDAO.class);
        
        try {
            courseDAO.deleteCourse(courseId);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to delete new course to the db", e);
        }
    }
}
