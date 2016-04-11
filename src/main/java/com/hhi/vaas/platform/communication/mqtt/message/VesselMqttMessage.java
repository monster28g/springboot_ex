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
 * Sang-cheon Park	2015. 9. 10.		First Draft.
 */
package com.hhi.vaas.platform.communication.mqtt.message;

import com.hhi.vaas.platform.communication.mqtt.message.vessel.VesselMqttHeader;

/**
 * <pre>
 * This class represents a message, this is the basic message class for vessel
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class VesselMqttMessage extends VaasMqttMessage {

    private VesselMqttHeader header;
    private Long timestamp;
    private String vdmVer;
    private String shipVer;

    /**
     * @return the header
     */
    public VesselMqttHeader getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(VesselMqttHeader header) {
        this.header = header;
    }

    /**
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the vdmVer
     */
    public String getVdmVer() {
        return vdmVer;
    }

    /**
     * @param vdmVer the vdmVer to set
     */
    public void setVdmVer(String vdmVer) {
        this.vdmVer = vdmVer;
    }

    /**
     * @return the shipVer
     */
    public String getShipVer() {
        return shipVer;
    }

    /**
     * @param shipVer the shipVer to set
     */
    public void setShipVer(String shipVer) {
        this.shipVer = shipVer;
    }
}
//end of VesselMqttMessage.java