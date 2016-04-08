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
 * Sang-cheon Park	2015. 8. 26.		First Draft.
 */
package com.hhi.vaas.platform.communication.mqtt.message.vessel.gathering;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.vaas.platform.communication.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * This class represents a message for gathering alarm data
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class AlarmsDataGatheringMessage extends VesselMqttGatheringMessage {

    private static final Logger logger = LoggerFactory.getLogger(AlarmsDataGatheringMessage.class);

    /** alarmSampleContent */
    private List<Map<String, Object>> alarmSampleContent;

    /**
     * @return the alarmSampleContent
     */
    public List<Map<String, Object>> getAlarmSampleContent() {
        return alarmSampleContent;
    }

    /**
     * @param alarmSampleContent  the alarmSampleContent  to set
     */
    public void setAlarmSampleContent(List<Map<String, Object>> alarmSampleContent) {
        this.alarmSampleContent = alarmSampleContent;
    }

    /**
     * @param alarmSampleContent the alarmSampleContent to set
     */
    @SuppressWarnings("unchecked")
    public void setAlarmSampleContentStr(String alarmSampleContent) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(alarmSampleContent);

            if (node.isArray()) {
                this.alarmSampleContent = mapper.readValue(alarmSampleContent,
                        new TypeReference<ArrayList<Map<String, Object>>>() { });
            } else {
                this.alarmSampleContent = new ArrayList<Map<String, Object>>();
                this.alarmSampleContent.add(JSONUtil.jsonToObj(alarmSampleContent, Map.class));
            }
        } catch (Exception e) {
            logger.error("an error occurred during json processing ", e);
        }
    }
}
//end of AlarmsDataGatheringMessage.java
