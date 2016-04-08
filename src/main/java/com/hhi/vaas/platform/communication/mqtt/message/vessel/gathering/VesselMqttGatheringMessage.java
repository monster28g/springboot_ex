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

import com.hhi.vaas.platform.communication.mqtt.message.VesselMqttMessage;

/**
 * <pre>
 * This class represents a message for gathering data
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class VesselMqttGatheringMessage extends VesselMqttMessage {

    private String messageId;

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
//end of VesselMqttGatheringMessage.java
