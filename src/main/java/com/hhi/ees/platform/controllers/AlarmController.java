package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.models.enums.TSDBType;
import com.hhi.ees.platform.services.AlarmService;
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
@RequestMapping(value = "/api/measurements/alarms")
public class AlarmController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private AlarmService alarmService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object getMeasurements() {
        return alarmService.getMeasurements(TSDBType.ALARM.getValue());
    }

    @RequestMapping(value = "/trends", method = RequestMethod.GET)
    public Object getTrends(@RequestParam(value = "vdm") String vdm,
                            @RequestParam(value = "from", required = false) Long from,
                            @RequestParam(value = "to", required = false) Long to,
                            @RequestParam(value = "last", defaultValue = "1d") String last,
                            @RequestParam(value = "epoch", defaultValue = "ms") String epoch ) throws UnsupportedEncodingException {

        if(StringUtils.isEmpty(vdm)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(from != null && to != null) {
            if(from > to){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else {
                return alarmService.getTrends(TSDBType.ALARM.getValue(), vdm, from, to, epoch);
            }
        }

        return alarmService.getTrendsLast(TSDBType.ALARM.getValue(), vdm, last, epoch);

    }
}
