package com.millenialspiders.garbagehound.servlet.com.millenialspiders.garbagehound.db;

/**
 * All DAO must extend this class
 */
public abstract class BaseDAO {
    private final GarbageHoundDataSource garbageHoundDataSource;

    public BaseDAO(GarbageHoundDataSource  dataSource) {
        this.garbageHoundDataSource = dataSource;
    }

    public GarbageHoundDataSource getDataSource() {
        return garbageHoundDataSource;
    }
}
