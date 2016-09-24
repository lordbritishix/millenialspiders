package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/match")
public class MatchMakingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Preconditions.checkNotNull(username, "username must be set");

        JsonArray matches = new JsonArray();

        JsonArray match1Courses = new JsonArray();
        match1Courses.add("CS101");
        match1Courses.add("CS500");
        match1Courses.add("MATLAB1");

        JsonArray match1Availability = new JsonArray();
        match1Availability.add("MONDAY");
        match1Availability.add("WEDNESDAY");
        match1Availability.add("THURSDAY");

        JsonObject match1 = new JsonObject();
        match1.addProperty("username", "tutor1");
        match1.addProperty("firstname", "Harry");
        match1.addProperty("firstname", "Potter");
        match1.addProperty("email", "harry.potter@gmail.com");
        match1.addProperty("phoneNo", "123-456-789");

        match1.add("courses", match1Courses);
        match1.add("availability", match1Availability);



        JsonArray match2Courses = new JsonArray();
        match2Courses.add("WIZ500");
        match2Courses.add("MAG200");
        match2Courses.add("WAR2123");

        JsonArray match2Availability = new JsonArray();
        match2Availability.add("SATURDAY");
        match2Availability.add("SUNDAY");

        JsonObject match2 = new JsonObject();
        match2.addProperty("username", "tutor2");
        match2.addProperty("firstname", "Ron");
        match2.addProperty("firstname", "Weasley");
        match2.addProperty("email", "ron.weasley@gmail.com");
        match2.addProperty("phoneNo", "567-456-789");

        match2.add("courses", match2Courses);
        match2.add("availability", match2Availability);

        matches.add(match1);
        matches.add(match2);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.print(matches.toString());
    }
}
