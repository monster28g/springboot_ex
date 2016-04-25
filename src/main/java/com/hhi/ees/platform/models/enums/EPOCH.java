package com.hhi.ees.platform.models.enums;

import java.util.Arrays;

public enum EPOCH {
    NANO_SECONDS("ns"), MICRO_SECONDS("u"), MILLI_SECONDS("ms"), SECONDS("s"), MINUTES("m"), HOURS("h"), DAYS("d"), MONTHS("n"), YEARS("y");

    private String value;

    EPOCH(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String valueOf(EPOCH type) {
        return Arrays.asList(values())
                .stream()
                .filter(v -> v.equals(type))
                .findAny()
                .orElse(null).name();
    }
}
