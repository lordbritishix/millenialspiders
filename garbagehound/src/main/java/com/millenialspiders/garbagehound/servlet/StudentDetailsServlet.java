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
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

/**
 * Handles the student details input:
 * <ul>
 *  <li>First Name</li>
 *  <li>Last Name</li>
 *  <li>Email</li>
 *  <li>Phone</li>
 * </ul>
 * 
 */
@WebServlet("/studentInfo")
public class StudentDetailsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        StudentAccountDetails student = StudentAccountDetails
                .StudentAccountDetailsBuilder
                .newInstance()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPhoneNo(phone)
                .build();

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accDAO = injector.getInstance(AccountDAO.class);

        try {
            accDAO.createStudentDetails(username, student);
            ServletUtils.writeSuccess(resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to insert student details to the db", e);
        }

    }
}
