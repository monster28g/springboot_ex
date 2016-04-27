package com.hhi.ees.platform.services;

import com.hhi.ees.platform.models.enums.ModelType;

public interface MyParser {
    String parse(String message, ModelType type);
}
