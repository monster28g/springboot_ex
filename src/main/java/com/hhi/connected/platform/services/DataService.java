package com.hhi.connected.platform.services;

import java.io.UnsupportedEncodingException;

public interface DataService {
    Object getMeasurements(String db);

    Object getTrendsLast(String vdm, String last, String unit) throws UnsupportedEncodingException;

    Object getTrends(String vdm, Long from, Long to, String unit) throws UnsupportedEncodingException;
}
