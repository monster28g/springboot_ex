package com.hhi.connected.platform.services;

import java.util.Map;

public interface CacheDataService {

    public Map getLatest();
    public void setLatest(Map map);

}
