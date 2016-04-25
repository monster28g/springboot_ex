package com.hhi.ees.platform.handlers;

import com.hhi.ees.platform.models.enums.TSDBType;
import com.hhi.ees.platform.services.StatsAPIService;
import com.hhi.ees.platform.services.utils.InsertBackslash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class StatsTSDBHandlerImpl implements StatsTSDBHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsTSDBHandlerImpl.class);

    @Value("${influxdb.host}")
    private String host;

    @Value("${influxdb.port}")
    private String port;

    private final String column = "status";
    private final String MEASUREMENTS = "SHOW+MEASUREMENTS";

    @Autowired
    private StatsAPIService statsAPIService;
    private static final String query = "http://%s:%s/query?pretty=true&db=%s&q=%s";
    private static final String measurementsQuery = "http://%s:%s/query?pretty=true&q=%s&db=%s";

    @Override
    public Object getAllStats(HttpMethod httpMethod) {

        return statsAPIService.execute(String.format(measurementsQuery, host, port, MEASUREMENTS, TSDBType.ALARM.getValue()), httpMethod);
    }

    @Override
    public Object getProductsOfEquipments(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException {

        //TODO DB change
        return statsAPIService.execute(String.format(query, host, port, TSDBType.ALARM.getValue(),
                URLEncoder.encode("SELECT count("+column+") FROM "+ new InsertBackslash().insertBackslash(vdm)+"/", "UTF-8")), httpMethod);
    }

    @Override
    public Object getProductsOfDevices(HttpMethod httpMethod) throws UnsupportedEncodingException {

        return statsAPIService.execute(String.format(query, host, port, TSDBType.ALARM.getValue(),
                URLEncoder.encode("SELECT count("+column+") FROM /.*/", "UTF-8")), httpMethod);
    }

    @Override
    public Object getAccumulationOfEquipments(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException {

        return statsAPIService.execute(String.format(query, host, port, TSDBType.ALARM.getValue(),
                URLEncoder.encode("SELECT count("+column+") FROM "+new InsertBackslash().insertBackslash(vdm)+"/", "UTF-8")), httpMethod);
    }

    @Override
    public Object getAccumulationOfDevices(HttpMethod httpMethod) throws UnsupportedEncodingException {

        return statsAPIService.execute(String.format(query, host, port, TSDBType.ALARM.getValue(),
                URLEncoder.encode("SELECT count("+column+") FROM /.*/", "UTF-8")), httpMethod);
    }
}
