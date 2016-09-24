package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;
import com.millenialspiders.garbagehound.model.Account;
import com.millenialspiders.garbagehound.model.InstructorAccountDetails;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

/**
 * DAO for account registration
 */
public class AccountDAO extends GarbageHoundDataSource {
    @Inject
    public AccountDAO(AppConfig appConfig) {
        super(appConfig);
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
    
    public int createInstructorAccount(String username, InstructorAccountDetails instructor) 
    throws SQLException {
        try (Connection conn = getConnection()) {
            int accountId = findAccountId(username);
            
            if (accountId == 0)
                return 0;
            PreparedStatement stmt = null;
            String query = "INSERT INTO instructor_account_detail VALUES(DEFAULT, ?, ?, ?, ?, ?)";
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
            
            if (accountId == 0)
                return 0;
            
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
            
            if (accountId == 0)
                return 0;
            
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
            
            if (accountId == 0)
                return 0;
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
            int tutorId  = findAccountId(tutorUsername);
            
            if (studentId == 0 | tutorId == 0)
                return 0;
            
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
            int tutorId  = findAccountId(tutorUsername);
            
            if (studentId == 0 | tutorId == 0)
                return 0;
            
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
}