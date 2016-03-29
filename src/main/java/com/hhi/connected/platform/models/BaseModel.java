package com.hhi.connected.platform.models;

public class BaseModel {
    String key;
    float value;
    long timestamp;
    int valid;

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

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getValid() {
        return valid;
    }
    public void setValid(int valid) {
        this.valid = valid;
    }
}