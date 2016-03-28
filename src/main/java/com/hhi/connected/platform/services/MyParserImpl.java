package com.hhi.connected.platform.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.connected.platform.services.utils.ShipTopologyModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class MyParserImpl implements MyParser{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyParserImpl.class);

    @Autowired
    ShipTopologyModule shipTopologyModule;

    public void setShipTopologyModule(ShipTopologyModule shipTopologyModule) {
        this.shipTopologyModule = shipTopologyModule;
    }

    @Override
    public String parse(String message) {
        try {
            if(StringUtils.isEmpty(message)){
                return null;
            }
            return toJson(shipTopologyModule.refine(
                    new ObjectMapper().readValue(message, new TypeReference<Map<String, Object>>() {})
            ));
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }

    }

    private String toJson(Map<String, Object> message) {
        try {
            message.entrySet().stream().forEach(e -> {
                List list = (List) e.getValue();
                // diff
            });
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }
}
