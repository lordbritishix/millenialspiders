package com.millenialspiders.garbagehound.common.db;

import com.google.inject.Inject;
import com.millenialspiders.garbagehound.common.config.AppConfig;

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


}
