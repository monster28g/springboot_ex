package com.hhi.connected.platform.models;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PushServiceInfo {
    
    /** API Endpoints */
    public static final String tokenApiUrl = "https://10.100.16.82:8343/token";
    public static final String cepApiUrl = "http://10.100.16.82:8380/cep/1.0";
    //    private String registerApiUrl = "http://10.100.16.82:8380/register/v1.0";
    public static final String registerApiUrl = "http://10.100.16.82:8080/auth/api/registCertificate_file";
    public static final String pushApiUrl = "ws://10.100.16.82:9000/websocket";

    /** App Info */
    public static final String confFileName = "apiinfo.json";
    public static final String registerAppName = "hhivaasdev3";
    public static final String dplFileName = "hhivaasdev3_dpl.txt";
    public static final String jksFileName = "hhivaasdev3.jks";
    public static final String keyFileName = "hhivaasdev3_privateKey.key";
    public static final String passPhrase = "changeit";

    public static String getSensorRuleName() {
        try {
            return String.format("%s-%s",sensorRuleName, getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return sensorRuleName;
        }
//        return sensorRuleName;
    }

    private static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();

    }

    public static String getAlarmRuleName() {
        try {
            return String.format("%s-%s",alarmRuleName, getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return alarmRuleName;
        }
//        return alarmRuleName;
    }

    /** Push rule name which is created beforehand */
    public static final String sensorRuleName = "sensor";

    /** Push rule name which is created beforehand */
    public static final String alarmRuleName = "alarm";
}
