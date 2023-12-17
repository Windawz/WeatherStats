package com.nmihalchenko.weatherstats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/** @noinspection ALL*/
public class MysqlStatsSource implements StatsSource {
    private static boolean isDriverInitialized = false;
    private Connection connection;

    public MysqlStatsSource(String connectionString) {
        if (!isDriverInitialized) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            isDriverInitialized = true;
        }

        try {
            this.connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> getStats(String cityName) {
        return new HashMap<>();
    }
}
