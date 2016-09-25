package com.millenialspiders.garbagehound.common.db;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.api.client.util.Sets;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;
import com.millenialspiders.garbagehound.model.Account;
import com.millenialspiders.garbagehound.model.Course;
import com.millenialspiders.garbagehound.model.InstructorAccountDetails;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

/**
 * DAO for account registration
 */
public class AccountDAO extends GarbageHoundDataSource {
    private static final String BUCKET_NAME = "garbagehound-142922.appspot.com";
    private final GcsService gcsService;

    @Inject
    public AccountDAO(AppConfig appConfig, GcsService gcsService) {
        super(appConfig);
        this.gcsService = gcsService;
    }

    public void registerAccount(Account account) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "SELECT id FROM "
                    + "account_type WHERE accountType LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, account.getAccountType().name());
            ResultSet rs = stmt.executeQuery();
            rs.first();
            query = "INSERT INTO account VALUES(DEFAULT, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, rs.getInt("id"));
            stmt.executeUpdate();
        }
    }

    private int findAccountId(String username) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "SELECT id FROM account WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return 0;
            }
            rs.first();
            return rs.getInt("id");
        }
    }

    public int createInstructorAccount(String username, InstructorAccountDetails instructor)
            throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);

            if (accountId <= 0) {
                throw new SQLException("account not found for username: " + username);
            }
            PreparedStatement stmt = null;
            String query = "INSERT INTO instructor_account_detail VALUES(DEFAULT, ?, ?, ?, ?, ?, DEFAULT)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setString(2, instructor.getFirstName());
            stmt.setString(3, instructor.getLastName());
            stmt.setString(4, instructor.getEmailAddress());
            stmt.setString(5, instructor.getPhoneNo());
            stmt.executeUpdate();
            return 1;
        }
    }

    public boolean isUserExists(String username, String password) throws SQLException {
        try (Connection conn = getConnection()) {
            String query = "SELECT COUNT(id) AS idCount FROM account WHERE username = ? AND PASSWORD = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (!rs.first()) {
                return false;
            }

            return rs.getInt("idCount") >= 1;
        }
    }

    public int addAccountDay(String username, String day) throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);

            if (accountId == 0) {
                return 0;
            }

            PreparedStatement stmt = null;
            String query = "INSERT INTO account_day_of_week VALUES(DEFAULT, ?, ?)";

            //getting day of week number
            DayOfWeek dayNo = DayOfWeek.valueOf(day.toUpperCase());

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setInt(2, dayNo.getValue());

            stmt.executeUpdate();
            return 1;
        }
    }

    public int deleteAccountDay(String username, String day) throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);

            if (accountId == 0) {
                return 0;
            }

            PreparedStatement stmt = null;
            String query = "DELETE FROM account_day_of_week WHERE "
                    + "account_id = ? AND "
                    + "day_of_week_id = ?";

            //getting day of week number
            DayOfWeek dayNo = DayOfWeek.valueOf(day.toUpperCase());

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setInt(2, dayNo.getValue());

            stmt.executeUpdate();
            return 1;
        }

    }

    public int createStudentDetails(String username, StudentAccountDetails student) throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);

            if (accountId == 0) {
                return 0;
            }
            PreparedStatement stmt = null;
            String query = "INSERT INTO student_account_detail VALUES(DEFAULT, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getEmailAddress());
            stmt.setString(5, student.getPhoneNo());
            stmt.executeUpdate();
            return 1;
        }
    }

    public int createStudentTutorLink(String studentUsername,
                                      String tutorUsername) throws SQLException {
        try (Connection conn = getConnection()) {
            int studentId = findAccountId(studentUsername);
            int tutorId = findAccountId(tutorUsername);

            if (studentId == 0 | tutorId == 0) {
                return 0;
            }

            PreparedStatement stmt = null;
            String query = "INSERT INTO account_account VALUES(DEFAULT, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, tutorId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
            return 1;
        }
    }

    public int deleteStudentTutorLink(String studentUsername,
                                      String tutorUsername) throws SQLException {
        try (Connection conn = getConnection()) {
            int studentId = findAccountId(studentUsername);
            int tutorId = findAccountId(tutorUsername);

            if (studentId == 0 | tutorId == 0) {
                return 0;
            }

            PreparedStatement stmt = null;
            String query = "DELETE FROM account_account WHERE instructor_account_id=?"
                    + " AND student_account_id=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, tutorId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
            return 1;
        }
    }

    public ResultSet getCourseInstructors() throws SQLException {
        try (Connection conn = getConnection()) {
            String query = "SELECT account_course.account_id, account_course.course_id FROM account_course"
                    + " INNER JOIN instructor_account_detail ON account_course.account_id="
                    + "instructor_account_detail.account_id";
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                return null;
            }

            return rs;
        }
    }

    public ResultSet getCourseStudents() throws SQLException {
        try (Connection conn = getConnection()) {
            String query = "SELECT account_course.account_id, account_course.course_id FROM account_course"
                    + " INNER JOIN student_account_detail ON account_course.account_id="
                    + "student_account_detail.account_id";
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                return null;
            }

            return rs;
        }
    }

    public void uploadProfilePhoto(String username, ByteBuffer rawPhoto) throws IOException {
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        GcsFilename fileName = new GcsFilename(BUCKET_NAME, username);
        gcsService.createOrReplace(fileName, instance, rawPhoto);
    }

    public InstructorAccountDetails getInstructor(String username) throws SQLException {
        int accountId = findAccountId(username);

        InstructorAccountDetails.InstructorAccountDetailsBuilder builder =
                InstructorAccountDetails.InstructorAccountDetailsBuilder.newInstance();

        if (!populateBuilderWithInstructor(accountId, rs ->
                builder.withUsername(rs.getString("username"))
                        .withFirstName(rs.getString("firstname"))
                        .withLastName(rs.getString("lastname"))
                        .withEmail(rs.getString("emailaddress"))
                        .withPhoneNo(rs.getString("mainphone"))
                        .withPhotoLocation(rs.getString("photoLocation")))) {
            return null;
        }

        populateBuilderWithCourses(accountId, rs -> {
            Set<Course> courses = Sets.newHashSet();

            while (rs.next()) {
                Course course = new Course(rs.getString("course_id"), rs.getString("course_name"));
                courses.add(course);
            }

            builder.withCourses(courses);
        });

        populateBuilderWithAvailability(accountId, rs -> {
            Set<DayOfWeek> availability = Sets.newHashSet();

            while (rs.next()) {
                availability.add(DayOfWeek.valueOf(rs.getString("name")));
            }

            builder.withPreferredDaySlot(availability);
        });

        return builder.build();
    }

    public StudentAccountDetails getStudent(String username) throws SQLException {
        int accountId = findAccountId(username);

        StudentAccountDetails.StudentAccountDetailsBuilder builder =
                StudentAccountDetails.StudentAccountDetailsBuilder.newInstance();

        if (!populateBuilderWithStudent(accountId, rs ->
                builder.withUsername(rs.getString("username"))
                        .withFirstName(rs.getString("firstname"))
                        .withLastName(rs.getString("lastname"))
                        .withEmail(rs.getString("emailaddress"))
                        .withPhoneNo(rs.getString("mainphone"))
                        .withPhotoLocation(rs.getString("photoLocation")))) {
            throw new IllegalArgumentException("Cannot do match making for students whose profiles are not yet filled up");
        }

        populateBuilderWithCourses(accountId, rs -> {
            Set<Course> courses = Sets.newHashSet();

            while (rs.next()) {
                Course course = new Course(rs.getString("course_id"), rs.getString("course_name"));
                courses.add(course);
            }

            builder.withCourses(courses);
        });

        populateBuilderWithAvailability(accountId, rs -> {
            Set<DayOfWeek> availability = Sets.newHashSet();

            while (rs.next()) {
                availability.add(DayOfWeek.valueOf(rs.getString("name")));
            }

            builder.withPreferredDaySlot(availability);
        });

        return builder.build();
    }

    private int getAccountTypeId(Account.AccountType accountType) throws SQLException {
        Set<String> instructorUserNames = Sets.newHashSet();

        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "id " +
                            "FROM account_type " +
                            "WHERE accountType=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setString(1, accountType.toString());

            ResultSet rs = ps.executeQuery();

            if (!rs.first()) {
                return -1;
            }

            return rs.getInt("id");
        }
    }

    // Yea, no paging - it sucks
    public Set<InstructorAccountDetails> getAllInstructors() throws SQLException {
        Set<String> instructorUserNames = Sets.newHashSet();

        int accountTypeId = getAccountTypeId(Account.AccountType.TEACHER);

        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "username " +
                            "FROM account " +
                            "WHERE account_type_id=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setInt(1, accountTypeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                instructorUserNames.add(rs.getString("username"));
            }
        }

        return instructorUserNames.stream()
                .map(username -> {
                    try {
                        return getInstructor(username);
                    } catch (SQLException e) {
                        throw new RuntimeException("Unable to get instructors");
                    }
                })
                .filter(instructor -> instructor != null)
                .collect(Collectors.toSet());
    }

    private boolean populateBuilderWithInstructor(
            int accountId, ConsumerThrowingException<ResultSet> consumer) throws SQLException {
        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "account.username, " +
                            "instructor_account_detail.firstname, " +
                            "instructor_account_detail.lastname, " +
                            "instructor_account_detail.emailaddress, " +
                            "instructor_account_detail.mainphone,  " +
                            "instructor_account_detail.photoLocation " +
                            "FROM instructor_account_detail INNER JOIN account ON " +
                            "instructor_account_detail.account_id = account.id " +
                            "WHERE account.id=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (!rs.first()) {
                return false;
            }

            consumer.accept(rs);

            return true;
        }
    }

    private boolean populateBuilderWithStudent(
            int accountId, ConsumerThrowingException<ResultSet> consumer) throws SQLException {
        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "account.username, " +
                            "student_account_detail.firstname, " +
                            "student_account_detail.lastname, " +
                            "student_account_detail.emailaddress, " +
                            "student_account_detail.mainphone,  " +
                            "student_account_detail.photoLocation " +
                            "FROM student_account_detail INNER JOIN account ON " +
                            "student_account_detail.account_id = account.id " +
                            "WHERE account.id=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (!rs.first()) {
                return false;
            }

            consumer.accept(rs);

            return true;
        }
    }

    private void populateBuilderWithCourses(
            int accountId, ConsumerThrowingException<ResultSet> consumer) throws SQLException {
        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "course.course_id, " +
                            "course.course_name " +
                            "FROM course INNER JOIN account_course ON " +
                            "course.course_id = account_course.course_id " +
                            "WHERE account_course.account_id=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            consumer.accept(rs);
        }
    }

    private void populateBuilderWithAvailability(
            int accountId, ConsumerThrowingException<ResultSet> consumer) throws SQLException {
        try (Connection connection = getConnection()) {
            String query =
                    "SELECT " +
                            "days_of_the_week.name " +
                            "FROM account_day_of_week INNER JOIN days_of_the_week ON " +
                            "account_day_of_week.day_of_week_id = days_of_the_week.id " +
                            "WHERE account_day_of_week.account_id=?";

            PreparedStatement ps = connection.prepareCall(query);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            consumer.accept(rs);
        }
    }

    private interface ConsumerThrowingException<T> {
        void accept(T rs) throws SQLException;
    }
}