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
import com.millenialspiders.garbagehound.model.InstructorAccountDetails;

@WebServlet("/instructorInfo")
public class InstructorDetailsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNo = req.getParameter("phoneNo");
        
        InstructorAccountDetails instructor = 
                InstructorAccountDetails.InstructorAccountDetailsBuilder.newInstance()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPhoneNo(phoneNo)
                .build();
        
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accDAO = injector.getInstance(AccountDAO.class);
        try {
            accDAO.createInstructorAccount(username, instructor);
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException e) {
            throw new ServletException("Unable to insert instructor info to the db", e);
        }
    }
}
