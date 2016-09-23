package com.millenialspiders.garbagehound.servlet.com.millenialspiders.garbagehound.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import com.millenialspiders.garbagehound.servlet.com.millenialspiders.garbagehound.config.AppConfig;

/**
 * Rudimentary data source ofr GarbageHound. Users must close the connection returned by this DataSource
 */
public class GarbageHoundDataSource implements DataSource {
    private final AppConfig appConfig;

    public GarbageHoundDataSource(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(appConfig.getDbConnectionString());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}
