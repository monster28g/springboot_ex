package com.hhi.ees.platform.handlers;

import com.hhi.ees.platform.models.BaseModel;
import com.hhi.ees.platform.models.enums.ModelType;

import java.util.List;
import java.util.Map;

public interface ModelHandler {
    List<BaseModel> process(List<Map<String, Object>> payload, ModelType type);
    List<BaseModel> process(Map<String, Object> payload, ModelType type);
}
