package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;

/**
 * DAO for Course creation and management
 *
 */
public class CourseDAO extends GarbageHoundDataSource {
    @Inject
    public CourseDAO(AppConfig appConfig) {
        super(appConfig);
    }
    
    public void createCourse(String courseId, String courseName) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "INSERT INTO course VALUES(?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseId.toUpperCase().replaceAll("\\s", ""));
            stmt.setString(2, courseName);
            stmt.executeUpdate();
        }
    }

    public void deleteCourse(String courseId) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "DELETE FROM course WHERE course_id=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseId.toUpperCase().replaceAll("\\s", ""));
            stmt.executeUpdate();
        }
    }

    public int createPreference(String username, String courseId) throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);
            if (accountId == 0)
                return 0;
            PreparedStatement stmt = null;
            String query = "INSERT INTO account_course VALUES(DEFAULT, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setString(2, courseId.toUpperCase().replaceAll("\\s", ""));
            stmt.executeUpdate();
            return 1;
        }
    }

    public int deletePreference(String username, String courseId) throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);
            if (accountId == 0)
                return 0;
            PreparedStatement stmt = null;
            String query = "DELETE FROM account_course WHERE "
                    + "account_id = ? AND "
                    + "course_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            stmt.setString(2, courseId.toUpperCase().replaceAll("\\s", ""));
            stmt.executeUpdate();
            return 1;
        }
    }

    private int findAccountId(String username) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "SELECT id FROM account WHERE username LIKE ?";
            stmt  = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
                return 0;
            rs.first();
            return rs.getInt("id");
        }
    }
}
