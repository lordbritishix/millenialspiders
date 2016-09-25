package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.AccountDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;
import org.apache.commons.io.IOUtils;

public class UploadPhotoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        String username = req.getParameter("username");
        Preconditions.checkNotNull(username, "username must be provided");

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accountDAO = injector.getInstance(AccountDAO.class);

        accountDAO.uploadProfilePhoto(username, ByteBuffer.wrap(IOUtils.toByteArray(req.getInputStream())));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        String username = req.getParameter("username");
        Preconditions.checkNotNull(username, "username must be provided");
    }
}
