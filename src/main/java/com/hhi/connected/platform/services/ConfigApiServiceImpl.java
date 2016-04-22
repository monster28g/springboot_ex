package com.hhi.connected.platform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class ConfigApiServiceImpl implements ConfigApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigApiServiceImpl.class);

    @Override
    public Object execute(String query, HttpMethod httpMethod) {
        try {
            LOGGER.info("query : {}", query);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.execute(new URI(query), httpMethod, null, new ConfigApiServiceImpl.ApiCallResponseExtractor(Object.class, restTemplate.getMessageConverters()));
        } catch (URISyntaxException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private class ApiCallResponseExtractor extends HttpMessageConverterExtractor<Object> {

        public ApiCallResponseExtractor (Class<Object> responseType,
                                         List<HttpMessageConverter<?>> messageConverters) {
            super(responseType, messageConverters);
        }
    }
}
