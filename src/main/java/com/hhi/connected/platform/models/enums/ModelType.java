package com.hhi.connected.platform.models.enums;

import java.util.Arrays;

public enum ModelType {
    DATA(0), ALARM(1), CONFIG(2);

    private Integer value;

    ModelType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static String valueOf(ModelType data) {
        return Arrays.asList(values())
                .stream()
                .filter(v -> v.equals(data))
                .findAny()
                .orElse(null).name();
    }


    public static ModelType valueOf(Integer value) {
        return Arrays.asList(values())
                .stream()
                .filter(v -> v.getValue().equals(value))
                .findAny().orElse(null);
    }
}
