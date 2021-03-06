package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.handlers.TSDBHandler;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@RestController
public class RestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(value = "/api/query/measurements/{db}", method = RequestMethod.GET )
    public Object getMeasurements(@PathVariable(value="db") String db) {

        // TODO add Validator for PathVariable
        return StringUtils.isEmpty(db) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : tsdbHandler.getMeasurements(db, HttpMethod.GET);

    }

    @RequestMapping(value = "/api/query", method = RequestMethod.GET )
    public Object queries(@RequestParam(value="db") String db,
                          @RequestParam(value="epoch") String epoch,
                          @RequestParam(value="q") String q) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : db= {}, epoch = {}, q = {}", db, epoch, q);
        return CollectionUtils.isEmpty(Arrays.asList(db, epoch, q))?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : tsdbHandler.queries(db, epoch, q, HttpMethod.GET);

    }
}
