package com.millenialspiders.garbagehound.model;

public class Account {
    public enum AccountType {
        TEACHER,
        STUDENT
    }

    private final String username;
    private final String password;
    private final AccountType accountType;

    public Account(String username, String password, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
