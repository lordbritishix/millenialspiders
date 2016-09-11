package com.millenialspiders.garbagehound;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/check")
public class MainServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter out = resp.getWriter();
    out.println("I am the Garbage Hound service... and I am alive!");
    out.println("Java version: " + System.getProperty("java.version"));
    out.println("Server info: " + getServletContext().getServerInfo());
  }
}
