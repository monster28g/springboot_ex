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
import java.util.Map;

/**
 * <pre>
 * This class represents a message for gathering vdm data
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class VDMDataGatheringMessage extends VesselMqttGatheringMessage {

    private static final Logger logger = LoggerFactory.getLogger(VDMDataGatheringMessage.class);

    /** vdmSampleContent */
    private Object vdmSampleContent;

    /**
     * @return the vdmSampleContent
     */
    public Object getVdmSampleContent() {
        return vdmSampleContent;
    }

    /**
     * @param vdmSampleContent the vdmSampleContent to set
     */
    public void setVdmSampleContent(Object vdmSampleContent) {
        this.vdmSampleContent = vdmSampleContent;
    }

    /**
     * @param vdmSampleContent the vdmSampleContent to set
     */
    public void setVdmSampleContentStr(String vdmSampleContent) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(vdmSampleContent);

            if (node.isArray()) {
                this.vdmSampleContent = mapper.readValue(vdmSampleContent,
                        new TypeReference<ArrayList<Map<String, String>>>() { });
            } else {
                this.vdmSampleContent = JSONUtil.jsonToObj(vdmSampleContent, Map.class);
            }
        } catch (Exception e) {
            logger.error("an error occurred during json processing ", e);
        }
    }
}
//end of VDMDataGatheringMessage.java
