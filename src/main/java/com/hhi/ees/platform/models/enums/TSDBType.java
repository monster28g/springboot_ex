package com.hhi.ees.platform.models.enums;

import java.util.Arrays;

public enum TSDBType {
    DATA("hivaas"), ALARM("hivaas_alarm"), CONFIG("hivaas_config"),
    // FIXME need to be changed for general stats
    STATS("equipment_alarm");

    private String value;

    TSDBType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String valueOf(TSDBType type) {
        return Arrays.asList(values())
                .stream()
                .filter(v -> v.equals(type))
                .findAny()
                .orElse(null).name();
    }
}
