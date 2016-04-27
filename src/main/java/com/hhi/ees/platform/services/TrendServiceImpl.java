package com.hhi.ees.platform.services;

import com.hhi.ees.platform.handlers.TSDBHandler;
import com.hhi.ees.platform.services.utils.MyTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class TrendServiceImpl implements TrendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrendServiceImpl.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @Override
    public Object getMeasurements(String db) {
        return tsdbHandler.getMeasurements(db, HttpMethod.GET);
    }

    @Override
    public Object getTrendsLast(String db, String vdm, String last, String unit) throws UnsupportedEncodingException {
        return tsdbHandler.queries(db, unit, String.format("SELECT value from \"%s\" where time > now() - %s", vdm, last), HttpMethod.GET);
    }

    @Override
    public Object getTrends(String db, String vdm, Long from, Long to, String unit) throws UnsupportedEncodingException {

        return tsdbHandler.queries(db, unit, String.format("select \"value\" from \"%s\" where time > %s AND time < %s", vdm, MyTimeUtils.getTime(from), MyTimeUtils.getTime(to)), HttpMethod.GET);
    }
}
