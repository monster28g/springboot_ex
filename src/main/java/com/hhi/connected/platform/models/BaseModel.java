package com.hhi.connected.platform.models;

public class BaseModel {
    String key;
    Float value;
    Long timestamp;
    Integer valid;

    public BaseModel(String key, Long timestamp, Float value, Integer valid) {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
        this.valid = valid;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public Float getValue() {
        return value;
    }
    public void setValue(Float value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getValid() {
        return valid;
    }
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}