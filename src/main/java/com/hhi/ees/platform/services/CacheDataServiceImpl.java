package com.hhi.ees.platform.services;

import com.hhi.ees.platform.models.BaseModel;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CacheDataServiceImpl implements CacheDataService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheDataServiceImpl.class);

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

    @Override
    public BaseModel get(String key) {
        return cache.get(key);
    }

    @Override
    public void update(Map.Entry<String, List<BaseModel>> e) {

        Optional<BaseModel> lastValue = e.getValue().stream().reduce((a, b) -> b);
        if(lastValue.isPresent()){
            cache.put(e.getKey(), lastValue.get());
        }


    }
}
