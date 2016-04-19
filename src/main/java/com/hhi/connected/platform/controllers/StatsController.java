package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.handlers.TSDBHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class StatsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private TSDBHandler tsdbHandler;

    @RequestMapping(value = "/stats", method = RequestMethod.GET )
    public Object getStats() {

        // TODO add Validator for PathVariable
        return "/stats";
    }

    @RequestMapping(value = "/stats/products/equipments", method = RequestMethod.GET )
    public Object getEquipmentsProducts(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return "/stats/products/equipments";
    }

    @RequestMapping(value = "/stats/products/equipments/devices", method = RequestMethod.GET )
    public Object getDevicesProducts(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return "/stats/products/equipments/devices";
    }

    @RequestMapping(value = "/stats/accumulation/equipments", method = RequestMethod.GET )
    public Object getEquipmentsAccumulation(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return "/stats/accumulation/equipments";
    }

    @RequestMapping(value = "/stats/accumulation/equipments/devices", method = RequestMethod.GET )
    public Object getDevicesAccumulation(@RequestParam(value="vdm") String vdm) throws UnsupportedEncodingException {

        // TODO add Validator for PathVariable
        return "/stats/accumulation/equipments/devices";
    }
}
