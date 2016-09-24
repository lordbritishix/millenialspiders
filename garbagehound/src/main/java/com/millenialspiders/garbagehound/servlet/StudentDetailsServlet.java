package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

/**
 * Displays the student details:
 * <ul>
 *  <li> Full Name</li>
 *  <li> Year </li>
 * </ul>
 * 
 */
@WebServlet("/studentInfo")
public class StudentDetailsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Injector injector = Guice.createInjector(new GarbageHoundModule());
    }
}
