package com.hhi.connected.platform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestApiServiceImpl implements RestApiService{
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiServiceImpl.class);
    private static final java.lang.String MEASUMENTS = "http://10.100.16.66:8086/query?pretty=true&q=SHOW+MEASUREMENTS&db=hivaas";

    @Override
    public Object test() {
        try {
            return new RestTemplate().getForObject(new URI(MEASUMENTS), String.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
