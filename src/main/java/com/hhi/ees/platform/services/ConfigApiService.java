package com.hhi.ees.platform.services;

import org.springframework.http.HttpMethod;

public interface ConfigApiService {
    Object execute(String query, HttpMethod httpMethod);
}
