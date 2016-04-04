package com.hhi.connected.platform.handlers;

import com.hhi.connected.platform.models.BaseModel;

import java.util.List;
import java.util.Map;

public interface ModelHandler {
    List<BaseModel> process(Map<String, Object> tweet);
}
