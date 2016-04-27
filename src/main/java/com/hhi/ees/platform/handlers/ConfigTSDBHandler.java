package com.hhi.ees.platform.handlers;

import org.springframework.http.HttpMethod;

import java.io.UnsupportedEncodingException;

public interface ConfigTSDBHandler {
    Object getConfigLatestOne(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException;

    Object getAllConfigs(HttpMethod httpMethod);
}
