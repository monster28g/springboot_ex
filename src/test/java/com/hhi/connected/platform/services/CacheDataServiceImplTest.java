package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.services.utils.MyMapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class CacheDataServiceImplTest {

    CacheDataService cacheDataService;

    @Before
    public void setUp() throws Exception {
        cacheDataService = new CacheDataServiceImpl();
        init();
    }

    private void init() {
        Map<String, BaseModel> base = new HashMap<>();
        base.put("test0", new BaseModel("test0", 1L, 1f, 1));
        cacheDataService.setLatest(base);
    }

    @Test
    public void getLatest() throws Exception {

        Map<String, BaseModel> expectedMap = new HashMap<>();

        expectedMap.put("test1", new BaseModel("test1", 2L, 2f, 1));
        assertTrue(MyMapUtils.mapDifference(expectedMap, cacheDataService.getLatest()).size() != 0);

        assertNull(cacheDataService.getLatest().get("test1"));

        cacheDataService.setLatest(expectedMap);

        assertNotNull(cacheDataService.getLatest().get("test0"));
        assertNotNull(cacheDataService.getLatest().get("test1"));

    }

    @Test
    public void setLatest() throws Exception {
        Map<String, BaseModel> expectedMap = new HashMap<>();
        expectedMap.put("test0", new BaseModel("test0", 100L, 100f, 1));
        expectedMap.put("test1", new BaseModel("test1", 200L, 200f, 1));
        cacheDataService.setLatest(expectedMap);

        assertTrue(MyMapUtils.mapDifference(expectedMap, cacheDataService.getLatest()).size() == 0);
    }
}