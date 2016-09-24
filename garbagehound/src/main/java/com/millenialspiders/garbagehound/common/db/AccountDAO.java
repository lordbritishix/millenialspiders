package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public int createInstructorAccount(String username, InstructorAccountDetails instructor) 
    throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;
            String query = "SELECT id FROM "
                    + "account WHERE username LIKE ?";
            //no additional check for whether or not they are an instructor
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
                return 0;
            rs.first();
            query = "INSERT INTO instructor_account_detail VALUES(DEFAULT, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, rs.getInt("id"));
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

    public int createStudentAccountForAccount(
            Account account, StudentAccountDetails studentAccountDetails) throws SQLException {
        try (Connection conn = getConnection()) {
            //Statement statement = conn.createStatement();
        }

        return 0;
    };
}