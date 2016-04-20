package com.hhi.connected.platform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ConfigApiServiceImpl implements ConfigApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigApiServiceImpl.class);

    @Override
    public Object execute(String query, HttpMethod httpMethod) {
        try {
            LOGGER.debug("query : {}", new URI(query));
            return new RestTemplate().exchange(new URI(query), httpMethod, null, Object.class);
        } catch (URISyntaxException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
