package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.handlers.TSDBHandler;
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
@RequestMapping(value = "/api/measurements/configs")
public class ConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(method = RequestMethod.GET )
    public Object getMeasurementsConfigs(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : "/api/measurements/configs";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET )
    public Object getAllConfigs() {

        return "/api/measurements/configs/all";
    }
}