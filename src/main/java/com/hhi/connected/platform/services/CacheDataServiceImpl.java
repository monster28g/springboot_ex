package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.BaseModel;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheDataServiceImpl implements CacheDataService{
    private Map<String, BaseModel> cache;

    public Map<String, BaseModel> getCache() {
        return cache;
    }
    public void setCache(Map<String, BaseModel> cache) {
        this.cache = cache;
    }

    public CacheDataServiceImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public Map getLatest() {
        return this.cache;
    }

    @Override
    public void setLatest(Map map) {
        if(MapUtils.isNotEmpty(map)) {
            cache.putAll(map);
        }
    }
}
