package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;
import com.millenialspiders.garbagehound.model.Account;
import com.millenialspiders.garbagehound.model.StudentAccountDetails;

/**
 * DAO for account registration
 */
public class AccountDAO extends GarbageHoundDataSource {
    @Inject
    public AccountDAO(AppConfig appConfig) {
        super(appConfig);
    }

    public void registerAccount() {

    }

    public int createStudentAccountForAccount(
            Account account, StudentAccountDetails studentAccountDetails) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
        }

        return 0;
    };
}