package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.handlers.TSDBHandler;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(value = "/api/query/measurements/{db}", method = RequestMethod.GET )
    public Object getMeasurements(@PathVariable(value="db") String db) {

        // TODO add Validator for PathVariable
        return StringUtils.isEmpty(db) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : tsdbHandler.getMeasurements(db);

    }

    @RequestMapping(value = "/api/query", method = RequestMethod.GET )
    public Object queries(@RequestParam(value="db") String db,
                          @RequestParam(value="epoch") String epoch,
                          @RequestParam(value="q") String q) {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : db= {}, epoch = {}, q = {}", db, epoch, q);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

//        return CollectionUtils.isEmpty(Arrays.asList(db, epoch, q))?
//                new ResponseEntity<>(HttpStatus.BAD_REQUEST)
//                : tsdbHandler.queries(db, epoch, q);

    }
}
