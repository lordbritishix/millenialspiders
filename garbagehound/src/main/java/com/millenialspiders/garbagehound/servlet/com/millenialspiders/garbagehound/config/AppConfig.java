package com.millenialspiders.garbagehound.servlet.com.millenialspiders.garbagehound.config;

import com.google.common.base.Preconditions;

/**
 * Application configuration
 */
public class AppConfig {
    private final String dbUsername;
    private final String dbPassword;
    private final String dbHostname;
    private final String dbConnectionString;

    //hostname, user, password
    private static final String CONNECTION_STRING = "jdbc:mysql://%s&user=%s&password=%s";

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

        public AppConfigBuilder withDbUserName(String dbUsername) {
            this.dbUsername = dbUsername;
            return this;
        }

        public AppConfigBuilder withDbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
            return this;
        }

        public AppConfigBuilder withHostname(String dbHostname) {
            this.dbHostname = dbHostname;
            return this;
        }

        public AppConfig build() {
            Preconditions.checkNotNull("dbHostname cannot be null", dbHostname);
            Preconditions.checkNotNull("dbPassword cannot be null", dbPassword);
            Preconditions.checkNotNull("dbUsername cannot be null", dbUsername);

            return new AppConfig(this);
        }
    }
}
