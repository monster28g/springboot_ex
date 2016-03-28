package com.hhi.connected.platform.services;

public interface PushService {
    public boolean registerSoftware() throws Exception;
    public void startAlarm() throws Exception;
    public void startSensor() throws Exception;

    public boolean isConnected();
    public void reStart() throws Exception;
}
