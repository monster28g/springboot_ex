package com.hhi.ees.platform.services;

public interface PushService {
    boolean registerSoftware() throws Exception;
    void startAlarm() throws Exception;
    void startSensor() throws Exception;

    boolean isConnected();
    void reStart() throws Exception;
}
