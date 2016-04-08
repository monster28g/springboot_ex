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
 * Bongjin Kwon	2015. 3. 25.		First Draft.
 */
package com.hhi.vaas.platform.communication.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * json convert util class.
 *
 * @author BongJin Kwon
 */
public abstract class JSONUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * <pre>
     * convert json string to class Object.
     * </pre>
     *
     * @param <T>
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T jsonToObj(String json, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * <pre>
     * convert Object to json String.
     * </pre>
     *
     * @param obj
     * @return
     */
    public static String objToJson(Object obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = OBJECT_MAPPER.getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
        OBJECT_MAPPER.writeValue(generator, obj);
        return outputStream.toString(JsonEncoding.UTF8.getJavaName());
    }

    /**
     * Method to deserialize JSON content as tree expressed using set of JsonNode instances.
     * @param json JSON content
     * @return
     */
    public static JsonNode readTree(String json){
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
