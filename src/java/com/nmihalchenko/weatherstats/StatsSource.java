package com.nmihalchenko.weatherstats;

import java.util.Map;

public interface StatsSource {
    Map<String, String> getStats(String cityName);
}