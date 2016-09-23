package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.HealthDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;

@WebServlet("/health")
public class HealthServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Injector injector = Guice.createInjector(new GarbageHoundModule());
        HealthDAO healthDAO = injector.getInstance(HealthDAO.class);

        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        JsonObject ret = new JsonObject();

        ret.addProperty("serviceName", "GarbageHound");
        ret.addProperty("javaVersion", System.getProperty("java.version"));
        ret.addProperty("serverInfo", getServletContext().getServerInfo());

        try {
            ret.addProperty("isDbAlive", healthDAO.isDbAlive());
        } catch (SQLException e) {
            throw new ServletException("Error checking if DB is alive", e);
        }

        out.write(ret.toString());
    }
}
