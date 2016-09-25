package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableMap;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

@WebServlet("/match")
public class MatchMakingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Preconditions.checkNotNull(username, "username must be set");

        JsonObject match1 =createMatchedAccount(
                StudentAccountDetails.StudentAccountDetailsBuilder.newInstance()
                    .withFirstName("Ron")
                    .withLastName("Weasley")
                    .withPhoneNo("123-456-789")
                    .withUsername("rweasley")
                    .withEmail("ron.weasley@gmail.com")
                    .build(),

                    ImmutableMap.of(
                        "COMP501", false,
                        "COMP100", true,
                        "COMP210", true
                    ),

                    ImmutableMap.of(
                        DayOfWeek.FRIDAY, false,
                        DayOfWeek.MONDAY, true,
                        DayOfWeek.SATURDAY, true
                    ));

        JsonObject match2 = createMatchedAccount(
                StudentAccountDetails.StudentAccountDetailsBuilder.newInstance()
                        .withFirstName("Harry")
                        .withLastName("Potter")
                        .withPhoneNo("898-123-2492")
                        .withUsername("hpotter")
                        .withEmail("harry.potter@gmail.com")
                        .build(),

                ImmutableMap.of(
                        "SCL122", true,
                        "SCD210", true,
                        "POA210", true
                ),

                ImmutableMap.of(
                        DayOfWeek.TUESDAY, true,
                        DayOfWeek.MONDAY, true,
                        DayOfWeek.SATURDAY, false
                ));

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(match1);
        jsonArray.add(match2);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.print(jsonArray.toString());
    }

    private JsonObject createMatchedAccount(
            StudentAccountDetails studentAccountDetails,
            Map<String, Boolean> courses,
            Map<DayOfWeek, Boolean> availability) {
        JsonObject ret = new JsonObject();

        ret.addProperty("username", studentAccountDetails.getUsername());
        ret.addProperty("firstname", studentAccountDetails.getFirstName());
        ret.addProperty("lastname", studentAccountDetails.getLastName());
        ret.addProperty("email", studentAccountDetails.getEmailAddress());

        JsonArray availabilityList = new JsonArray();
        availability.entrySet().stream().map(entrySet -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("dayOfWeek", entrySet.getKey().toString());
            jsonObject.addProperty("isMatched", entrySet.getValue());
            availabilityList.add(jsonObject);
            return jsonObject;
        }).collect(Collectors.toSet());

        JsonArray coursesList = new JsonArray();
        courses.entrySet().stream().map(entrySet -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("courseId", entrySet.getKey().toString());
            jsonObject.addProperty("isMatched", entrySet.getValue());
            coursesList.add(jsonObject);
            return jsonObject;
        }).collect(Collectors.toSet());

        ret.add("courses", coursesList);
        ret.add("availability", availabilityList);


        return ret;
    }
}
