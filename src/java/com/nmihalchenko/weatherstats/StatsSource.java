package com.nmihalchenko.weatherstats;

import java.util.List;

public interface StatsSource {
    List<List<String>> getStats(String cityName);
    void close();
}