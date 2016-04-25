package com.hhi.ees.platform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class RestApiServiceImpl implements RestApiService{
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiServiceImpl.class);

    @Override
    public Object exchange(String query, HttpMethod httpMethod) {
        try {
            LOGGER.info("query : {}", query);
            return new RestTemplate().exchange(new URI(query), httpMethod, null, Object.class);
        } catch (URISyntaxException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Object execute(String query, HttpMethod httpMethod) {
        try {
            LOGGER.info("query : {}", query);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.execute(new URI(query), httpMethod, null, new ApiCallResponseExtractor(Object.class, restTemplate.getMessageConverters()));
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