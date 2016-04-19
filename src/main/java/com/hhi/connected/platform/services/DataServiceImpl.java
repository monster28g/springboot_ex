package com.hhi.connected.platform.services;

import com.hhi.connected.platform.handlers.TSDBHandler;
import com.hhi.connected.platform.models.enums.TSDBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class DataServiceImpl implements DataService{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @Override
    public Object getMeasurements(String db) {
        return tsdbHandler.getMeasurements(db, HttpMethod.GET);
    }

    @Override
    public Object getTrendsLast(String vdm, String last, String unit) throws UnsupportedEncodingException {
        return tsdbHandler.queries(TSDBType.DATA.getValue(), unit, String.format("SELECT value from \"%s\" where time > now() - %s", vdm, last), HttpMethod.GET);
    }

    @Override
    public Object getTrends(String vdm, Long from, Long to, String unit) throws UnsupportedEncodingException {
        return tsdbHandler.queries(TSDBType.DATA.getValue(), unit, String.format("select value from \"%s\" where time > from AND time < to - %s", vdm, from, to), HttpMethod.GET);
    }
}
