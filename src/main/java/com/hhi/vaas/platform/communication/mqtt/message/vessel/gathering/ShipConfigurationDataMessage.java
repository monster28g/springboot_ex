/* Copyright (C) 2015~ Hyundai Heavy Industries. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Hyundai Heavy Industries
 * You shall not disclose such Confidential Information and shall use it only 
 * in accordance with the terms of the license agreement
 * you entered into with Hyundai Heavy Industries.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * Sang-cheon Park	2015. 8. 27.		First Draft.
 */
package com.hhi.vaas.platform.communication.mqtt.message.vessel.gathering;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.vaas.platform.communication.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * This class represents a message for gathering ship config data
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class ShipConfigurationDataMessage extends VesselMqttGatheringMessage {

    private static final Logger logger = LoggerFactory.getLogger(ShipConfigurationDataMessage.class);

    /** shipVerDate */
    private Date shipVerDate;
    /** shipTopology */
    private Object shipTopology;

    /**
     * @return the shipVerDate
     */
    public Date getShipVerDate() {
        return new Date(shipVerDate.getTime());
    }

    /**
     * @param shipVerDate the shipVerDate to set
     */
    public void setShipVerDate(Date shipVerDate) {
        this.shipVerDate = (Date) shipVerDate.clone();
    }

    /**
     * @return the shipTopology
     */
    public Object getShipTopology() {
        return shipTopology;
    }

    /**
     * @param shipTopology the shipTopology to set
     */
    public void setShipTopology(Object shipTopology) {
        this.shipTopology = shipTopology;
    }

    /**
     * @param shipTopology the shipTopology to set
     */
    public void setShipTopologyStr(String shipTopology) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(shipTopology);

            if (node.isArray()) {
                this.shipTopology = mapper.readValue(shipTopology, new TypeReference<ArrayList<Map<String, String>>>() { });
            } else {
                this.shipTopology = JSONUtil.jsonToObj(shipTopology, Map.class);
            }
        } catch (Exception e) {
            logger.error("an error occurred during json processing ", e);
        }
    }
}
//end of ShipConfigurationDataMessage.java
