package com.hhi.connected.platform.handlers;

import com.hhi.connected.platform.services.ConfigApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class ConfigTSDBHandlerImpl implements ConfigTSDBHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTSDBHandlerImpl.class);

    private final String db = "hivaas_config";
    private final String host = "10.100.16.66";
    private final String port = "8086";
    private final String MEASUREMENTS = "SHOW+MEASUREMENTS";

    @Autowired
    private ConfigApiService configApiService;
    private static final String query = "http://%s:%s/query?pretty=true&db=%s&q=%s";
    private static final String measurementsQuery = "http://%s:%s/query?pretty=true&q=%s&db=%s";

    @Override
    public Object getConfigLatestOne(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException {
        return configApiService.execute(String.format(query, host, port, db,
                URLEncoder.encode("SELECT * FROM \""+vdm+"\" ORDER BY time DESC limit 1", "UTF-8")), httpMethod);
    }

    @Override
    public Object getAllConfigs(HttpMethod httpMethod) {
        LOGGER.debug("request query : {}", String.format(measurementsQuery, host, port, MEASUREMENTS, db));
        return configApiService.execute(String.format(measurementsQuery, host, port, MEASUREMENTS, db), httpMethod);
    }
}
