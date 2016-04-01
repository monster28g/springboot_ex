package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.services.RestApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private RestApiService restApiService;

    @RequestMapping(value = "/api/query/measurements", method = RequestMethod.GET )
    public Object getMeasurements() {
        return restApiService.test();

    }
}
