package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.models.enums.TSDBType;
import com.hhi.connected.platform.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/measurements/data")
public class DataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object getMeasurements() {
        return dataService.getMeasurements(TSDBType.DATA.getValue());
    }

    @RequestMapping(value = "/trends", method = RequestMethod.GET)
    public Object getTrends(@RequestParam(value = "vdm") String vdm,
                            @RequestParam(value = "from", required = false) Long from,
                            @RequestParam(value = "to", required = false) Long to,
                            @RequestParam(value = "last", required = false) String last,
                            @RequestParam(value = "epoch", defaultValue = "m") String epoch ) throws UnsupportedEncodingException {

        if(StringUtils.isEmpty(vdm)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(from != null && to != null) {
            if(from > to){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else {
                return dataService.getTrends(vdm, from, to, epoch);
            }
        }
        else if (last != null) {
            return dataService.getTrendsLast(vdm, last, epoch);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
