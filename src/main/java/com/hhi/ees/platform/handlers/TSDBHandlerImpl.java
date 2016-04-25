package com.hhi.ees.platform.handlers;

import com.hhi.ees.platform.services.RestApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class TSDBHandlerImpl implements TSDBHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TSDBHandlerImpl.class);

    // TODO make this class to utils
    
    @Value("${influxdb.host}")
    private String host;

    @Value("${influxdb.port}")
    private int port;

    private final String MEASUREMENTS = "SHOW+MEASUREMENTS";

    @Autowired
    private RestApiService restApiService;
    private static final String query = "http://%s:%s/query?pretty=true&db=%s&epoch=%s&q=%s";
    private static final String measurementsQuery = "http://%s:%s/query?pretty=true&q=%s&db=%s";

    @Override
    public Object getMeasurements(String db, HttpMethod httpMethod) {
        return restApiService.exchange(String.format(measurementsQuery, host, port, MEASUREMENTS, db), httpMethod);
    }

    @Override
    public Object queries(String db, String epoch, String q, HttpMethod httpMethod) throws UnsupportedEncodingException {
        return restApiService.execute(String.format(query, host, port, db, epoch, URLEncoder.encode(q, "UTF-8")), httpMethod);
    }
}
