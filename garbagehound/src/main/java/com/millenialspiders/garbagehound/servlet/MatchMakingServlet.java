package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.millenialspiders.garbagehound.common.db.AccountDAO;
import com.millenialspiders.garbagehound.common.guice.GarbageHoundModule;
import com.millenialspiders.garbagehound.model.InstructorAccountDetails;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

@WebServlet("/match")
public class MatchMakingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.writeAccessControl(resp);

        String username = req.getParameter("username");
        Preconditions.checkNotNull(username, "username must be set");

        Injector injector = Guice.createInjector(new GarbageHoundModule());
        AccountDAO accountDAO = injector.getInstance(AccountDAO.class);

        try {
            StudentAccountDetails student = accountDAO.getStudent(username);

            if (student == null) {
                throw new IllegalArgumentException("Unable to find the username for: " + username);
            }

            Set<InstructorAccountDetails> instructors = accountDAO.getAllInstructors();
            List<CompatibilityScore> score = ServletUtils.matchMake(student, instructors);

            JsonArray returnValue = new JsonArray();

            score.stream().forEach(compatibilityScore -> {
                JsonObject match = createMatchedAccount(
                        compatibilityScore.getInstructor(),
                        compatibilityScore.getMarkedCourses(),
                        compatibilityScore.getMarkedAvailability(),
                        compatibilityScore.getScore()
                        );
                returnValue.add(match);
            });

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.print(returnValue.toString());
        } catch (SQLException e) {
            throw new ServletException("Unable to perform matchmaking for user: " + username);
        }
    }

    private JsonObject createMatchedAccount(
            InstructorAccountDetails instructorAccountDetails,
            Map<String, Boolean> courses,
            Map<DayOfWeek, Boolean> availability,
            int score) {
        JsonObject ret = new JsonObject();

        ret.addProperty("username", instructorAccountDetails.getUsername());
        ret.addProperty("firstname", instructorAccountDetails.getFirstName());
        ret.addProperty("lastname", instructorAccountDetails.getLastName());
        ret.addProperty("email", instructorAccountDetails.getEmailAddress());
        ret.addProperty("photo", instructorAccountDetails.getPhotoLocation());

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
        ret.addProperty("score", score);

        return ret;
    }

//    Backup hardcoded data
//    JsonObject match1 = createMatchedAccount(
//            InstructorAccountDetails.InstructorAccountDetailsBuilder.newInstance()
//                    .withFirstName("Ron")
//                    .withLastName("Weasley")
//                    .withPhoneNo("123-456-789")
//                    .withUsername("rweasley")
//                    .withEmail("ron.weasley@gmail.com")
//                    .withPhotoLocation("https://placeholdit.imgix.net/~text?txtsize=20&txt=Add+Photo&w=300&h=300&txttrack=0")
//                    .build(),
//
//            ImmutableMap.of(
//                    "COMP501", false,
//                    "COMP100", true,
//                    "COMP210", true
//            ),
//
//            ImmutableMap.of(
//                    DayOfWeek.FRIDAY, false,
//                    DayOfWeek.MONDAY, true,
//                    DayOfWeek.SATURDAY, true
//            ));
//
//    JsonObject match2 = createMatchedAccount(
//            InstructorAccountDetails.InstructorAccountDetailsBuilder.newInstance()
//                    .withFirstName("Harry")
//                    .withLastName("Potter")
//                    .withPhoneNo("898-123-2492")
//                    .withUsername("hpotter")
//                    .withEmail("harry.potter@gmail.com")
//                    .withPhotoLocation("https://placeholdit.imgix.net/~text?txtsize=20&txt=Add+Photo&w=300&h=300&txttrack=0")
//                    .build(),
//
//            ImmutableMap.of(
//                    "SCL122", true,
//                    "SCD210", true,
//                    "POA210", true
//            ),
//
//            ImmutableMap.of(
//                    DayOfWeek.TUESDAY, true,
//                    DayOfWeek.MONDAY, true,
//                    DayOfWeek.SATURDAY, false
//            ));
//
//    JsonArray jsonArray = new JsonArray();
//        jsonArray.add(match1);
//        jsonArray.add(match2);
//
//    PrintWriter out = resp.getWriter();
//        resp.setContentType("application/json");
//        out.print(jsonArray.toString());
}
