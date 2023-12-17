package com.nmihalchenko.weatherstats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<List<String>> getStats(String cityName) {
        Statement statement = null;
        ResultSet resultSet = null;
        var list = new ArrayList<List<String>>();
        list.add(new ArrayList<>());
        list.get(0).add("Date");
        list.add(new ArrayList<>());
        list.get(1).add("Humidity");
        list.add(new ArrayList<>());
        list.get(2).add("Cloudiness");

        try {
            statement = connection.createStatement();

            String query = new StringBuilder()
                    .append("SELECT weather_date, weather_humidity, weather_cloudiness")
                    .append("\nFROM weathers")
                    .append("\nINNER JOIN cities")
                    .append("\nON weathers.city_id = cities.city_id")
                    .append("\nWHERE city_name = '" + cityName + "'")
                    .append("\nORDER BY weather_date;")
                    .toString();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                list.get(0).add(resultSet.getDate("weather_date").toString());
                list.get(1).add(Float.toString(resultSet.getFloat("weather_humidity")));
                list.get(2).add(Float.toString(resultSet.getFloat("weather_cloudiness")));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) { }
                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) { }
                statement = null;
            }
        }
    }

    public void close() {
        if (this.connection != null) {
            try {
                if (!this.connection.isClosed()) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                this.connection = null;
            }
        }
    }
}
