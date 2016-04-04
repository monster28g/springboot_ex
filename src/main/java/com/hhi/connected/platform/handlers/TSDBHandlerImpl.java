package com.hhi.connected.platform.handlers;

import com.hhi.connected.platform.services.RestApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TSDBHandlerImpl implements TSDBHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TSDBHandlerImpl.class);

    private String host = "10.100.16.66";
    private String port = "8086";
    private String MEASUREMENTS = "SHOW+MEASUREMENTS";

    @Autowired
    RestApiService restApiService;
    private static final String query = "http://%s:%s/query?pretty=true&db=%s&epoch=%s&q=%s";
    private static final String measurementsQuery = "http://%s:%s/query?pretty=true&q=%s&db=%s";

    @Override
    public Object getMeasurements(String db) {
        LOGGER.debug("request query : {}", String.format(measurementsQuery, host, port, MEASUREMENTS, db));
        return restApiService.execute(String.format(measurementsQuery, host, port, MEASUREMENTS, db));
    }

    @Override
    public Object queries(String db, String epoch, String q) {
        String request = String.format(query, host, port, db, epoch, q);
        LOGGER.debug("request query : {}", request);
        return restApiService.execute(request);
    }
}
