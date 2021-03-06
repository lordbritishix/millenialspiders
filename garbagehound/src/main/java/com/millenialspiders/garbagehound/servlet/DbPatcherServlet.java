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
import com.millenialspiders.garbagehound.common.db.AdminDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;
import liquibase.exception.LiquibaseException;

/**
 * Patches the DB using liquibase
 */
@WebServlet("/patchDb")
public class DbPatcherServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AdminDAO adminDAO = injector.getInstance(AdminDAO.class);

        try {
            adminDAO.patchDb();
            ServletUtils.writeSuccess(resp, true);
        } catch (SQLException | LiquibaseException e) {
            throw new ServletException("Unable to patch the db", e);
        }
    }
}
