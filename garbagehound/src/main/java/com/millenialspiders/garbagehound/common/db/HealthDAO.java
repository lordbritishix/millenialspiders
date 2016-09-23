package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;

/**
 * DAO to check DB health
 */
public class HealthDAO extends GarbageHoundDataSource {
    @Inject
    public HealthDAO(AppConfig appConfig) {
        super(appConfig);
    }

    public boolean isDbAlive() throws SQLException {
        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT 1");
            rs.first();

            return rs.getInt(1) == 1;
        }
    }
}
