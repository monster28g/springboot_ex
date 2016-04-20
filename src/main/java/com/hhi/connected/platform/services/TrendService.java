package com.hhi.connected.platform.services;

import java.io.UnsupportedEncodingException;

public interface TrendService {
    Object getMeasurements(String db);
    Object getTrendsLast(String db, String vdm, String last, String unit) throws UnsupportedEncodingException;
    Object getTrends(String db, String vdm, Long from, Long to, String unit) throws UnsupportedEncodingException;
}
