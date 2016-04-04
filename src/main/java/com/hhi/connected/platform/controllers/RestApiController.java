package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.handlers.TSDBHandler;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(value = "/api/query/measurements/{table}", method = RequestMethod.GET )
    public Object getMeasurements(@PathVariable(value="table") String table) {

        // TODO add Validator for PathVariable
        return StringUtils.isEmpty(table) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : tsdbHandler.getMeasurements(table);

    }
}
