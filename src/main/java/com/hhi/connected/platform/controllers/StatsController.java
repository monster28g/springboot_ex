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
@RequestMapping(value = "/stats")
public class StatsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(method = RequestMethod.GET )
    public Object getStats() {

        // TODO add Validator for PathVariable
        return "/stats";
    }

    @RequestMapping(value = "/products/equipments", method = RequestMethod.GET )
    public Object getEquipmentsProducts(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : "/stats/products/equipments";
    }

    @RequestMapping(value = "/products/equipments/devices", method = RequestMethod.GET )
    public Object getDevicesProducts(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : "/stats/products/equipments/devices";
    }

    @RequestMapping(value = "/accumulation/equipments", method = RequestMethod.GET )
    public Object getEquipmentsAccumulation(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : "/stats/accumulation/equipments";
    }

    @RequestMapping(value = "/accumulation/equipments/devices", method = RequestMethod.GET )
    public Object getDevicesAccumulation(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        LOGGER.debug("query : vdm = {}", vdm);
        return StringUtils.isEmpty(vdm) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : "/stats/accumulation/equipments/devices";
    }
}
