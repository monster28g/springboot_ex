package com.hhi.connected.platform.models;

import java.util.Map;

public class BaseModel {
    private String key;
    private Long timestamp;
    private Map<String, Object> values;

    public BaseModel(String key, Long timestamp, Map<String, Object> values) {
        this.key = key;
        this.timestamp = timestamp;
        this.values = values;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getValues() {
        return values;
    }
    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}