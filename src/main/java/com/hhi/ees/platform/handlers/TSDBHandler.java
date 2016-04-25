package com.hhi.ees.platform.handlers;

import org.springframework.http.HttpMethod;

import java.io.UnsupportedEncodingException;

public interface TSDBHandler {
    Object getMeasurements(String table, HttpMethod httpMethod);

    Object queries(String db, String epoch, String q, HttpMethod httpMethod) throws UnsupportedEncodingException;
}
