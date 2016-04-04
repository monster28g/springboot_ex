package com.hhi.connected.platform.handlers;

public interface TSDBHandler {
    Object getMeasurements(String table);

    Object queries(String db, String epoch, String q);
}
