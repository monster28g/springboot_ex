package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.BaseModel;

import java.util.List;
import java.util.Map;

public interface CacheDataService {

    Map getLatest();
    void setLatest(Map map);

    BaseModel get(String key);

    void update(Map.Entry<String, List<BaseModel>> e);
}
