package com.hhi.ees.platform.handlers;

import com.hhi.ees.platform.models.enums.TSDBType;
import com.hhi.ees.platform.services.ConfigApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class ConfigTSDBHandlerImpl implements ConfigTSDBHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTSDBHandlerImpl.class);

    @Value("${influxdb.host}")
    private String host;

    @Value("${influxdb.port}")
    private String port;

    private final String MEASUREMENTS = "SHOW+MEASUREMENTS";

    @Autowired
    private ConfigApiService configApiService;
    private static final String query = "http://%s:%s/query?pretty=true&db=%s&q=%s";
    private static final String measurementsQuery = "http://%s:%s/query?pretty=true&q=%s&db=%s";

    @Override
    public Object getConfigLatestOne(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException {
        return configApiService.execute(String.format(query, host, port, TSDBType.CONFIG.getValue(),
                URLEncoder.encode("SELECT * FROM \""+vdm+"\" ORDER BY time DESC limit 1", "UTF-8")), httpMethod);
    }

    @Override
    public Object getAllConfigs(HttpMethod httpMethod) {
        LOGGER.debug("request query : {}", String.format(measurementsQuery, host, port, MEASUREMENTS, TSDBType.CONFIG.getValue()));
        return configApiService.execute(String.format(measurementsQuery, host, port, MEASUREMENTS, TSDBType.CONFIG.getValue()), httpMethod);
    }
}
