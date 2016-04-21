package com.hhi.connected.platform.services;

import org.springframework.http.HttpMethod;

public interface StatsAPIService {
    Object execute(String query, HttpMethod httpMethod);
}
