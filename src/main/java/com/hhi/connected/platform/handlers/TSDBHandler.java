package com.hhi.connected.platform.handlers;

import java.io.UnsupportedEncodingException;

public interface TSDBHandler {
    Object getMeasurements(String table);

    Object queries(String db, String epoch, String q) throws UnsupportedEncodingException;
}
