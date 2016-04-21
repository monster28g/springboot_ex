package com.hhi.connected.platform.handlers;

import org.springframework.http.HttpMethod;

import java.io.UnsupportedEncodingException;

public interface StatsTSDBHandler {
    Object getAllStats(HttpMethod httpMethod);

    Object getProductsOfEquipmets(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException;

    Object getProductsOfDevices(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException;

    Object getAccumulationOfEquipments(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException;

    Object getAccumulationOfDevices(String vdm, HttpMethod httpMethod) throws UnsupportedEncodingException;
}
