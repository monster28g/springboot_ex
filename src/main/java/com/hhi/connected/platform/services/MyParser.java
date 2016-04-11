package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.enums.ModelType;

public interface MyParser {
    String parse(String message, ModelType type);
}
