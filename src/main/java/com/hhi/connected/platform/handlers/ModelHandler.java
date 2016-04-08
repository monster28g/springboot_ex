package com.hhi.connected.platform.handlers;

import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.models.enums.ModelType;

import java.util.List;
import java.util.Map;

public interface ModelHandler {
    List<BaseModel> process(List<Map<String, Object>> payload, ModelType type);
    List<BaseModel> process(Map<String, Object> payload, ModelType type);
}
