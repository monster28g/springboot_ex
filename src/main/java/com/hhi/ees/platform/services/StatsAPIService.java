package com.hhi.ees.platform.services;

import org.springframework.http.HttpMethod;

public interface StatsAPIService {
    Object execute(String query, HttpMethod httpMethod);
}
