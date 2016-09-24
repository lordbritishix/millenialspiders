package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Preconditions;
import com.millenialspiders.garbagehound.model.Account;

/**
 * Handles user registration. Expects the parameters:
 * <ol>
 *     <li>username</li>
 *     <li>password</li>
 *     <li>accountType - TEACHER or STUDENT</li>
 * </ol>
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String accountType = req.getParameter("accountType");

        Preconditions.checkNotNull(username, "username is required");
        Preconditions.checkNotNull(password, "password is required");
        Preconditions.checkNotNull(accountType, "accountType is required");
        Preconditions.checkArgument(
                        accountType.equals(Account.AccountType.STUDENT.toString())
                        || accountType.equals(Account.AccountType.TEACHER.toString()), "Invalid account type provided");

        Account account = new Account(username, password, Account.AccountType.valueOf(accountType));
        // do something with the account

        ServletUtils.writeSuccess(resp);
    }
}
