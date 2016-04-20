package com.hhi.connected.platform.services;

import org.springframework.http.HttpMethod;

public interface RestApiService {
    Object exchange(String query, HttpMethod httpMethod);

    Object execute(String query, HttpMethod httpMethod);
}

