package com.millenialspiders.garbagehound.common.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.millenialspiders.garbagehound.common.config.AppConfig;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * DAO to check DB health
 */
@Singleton
public class AdminDAO extends GarbageHoundDataSource {
    @Inject
    public AdminDAO(AppConfig appConfig) {
        super(appConfig);
    }

    public boolean isDbAlive() throws SQLException {
        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT 1");
            rs.first();

            return rs.getInt(1) == 1;
        }
    }

    public void patchDb() throws SQLException, LiquibaseException {
        try (Connection conn = getConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
            Liquibase liquibase = new Liquibase("liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    public void rollbackDb() throws LiquibaseException, SQLException {
        try (Connection conn = getConnection()) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
            Liquibase liquibase = new Liquibase("liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.rollback(Date.from(Instant.ofEpochSecond(1L)), new Contexts(), new LabelExpression());
        }
    }
}
