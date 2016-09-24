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
    public int addInstructorDay(String username, String day) throws SQLException {
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
    
    public int deleteInstructorDay(String username, String day) throws SQLException {
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

    public int createStudentAccountForAccount(
            Account account, StudentAccountDetails studentAccountDetails) throws SQLException {
        try (Connection conn = getConnection()) {
            //Statement statement = conn.createStatement();
        }

        return 0;
    };
}