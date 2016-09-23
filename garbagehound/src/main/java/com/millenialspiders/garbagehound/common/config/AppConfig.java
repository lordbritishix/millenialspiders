package com.millenialspiders.garbagehound.common.config;

import com.google.common.base.Preconditions;

/**
 * Application configuration
 */
public class AppConfig {
    //hostname, user, password
    private static final String CONNECTION_STRING = "jdbc:mysql://%s&user=%s&password=%s";

    private final String dbUsername;
    private final String dbPassword;
    private final String dbHostname;
    private final String dbConnectionString;

    public AppConfig(AppConfigBuilder builder) {
        this.dbHostname = builder.dbHostname;
        this.dbPassword = builder.dbPassword;
        this.dbUsername = builder.dbUsername;
        this.dbConnectionString = String.format(CONNECTION_STRING, dbHostname, dbUsername, dbPassword);
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHostname() {
        return dbHostname;
    }

    public String getDbConnectionString() {
        return dbConnectionString;
    }

    public static class AppConfigBuilder {
        private String dbUsername;
        private String dbPassword;
        private String dbHostname;

        public static AppConfigBuilder newBuilder() {
            return new AppConfigBuilder();
        }

        public AppConfigBuilder withDbUserName(String username) {
            this.dbUsername = username;
            return this;
        }

        public AppConfigBuilder withDbPassword(String password) {
            this.dbPassword = password;
            return this;
        }

        public AppConfigBuilder withHostname(String hostname) {
            this.dbHostname = hostname;
            return this;
        }

        public AppConfig build() {
            Preconditions.checkNotNull(dbUsername, "dbUsername cannot be null");
            Preconditions.checkNotNull(dbPassword, "dbPassword cannot be null");
            Preconditions.checkNotNull(dbHostname, "dbHostname cannot be null");

            return new AppConfig(this);
        }
    }
}
