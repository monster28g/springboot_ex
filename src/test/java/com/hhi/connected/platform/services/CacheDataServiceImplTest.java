package com.hhi.connected.platform.services;

import com.google.common.collect.ImmutableMap;
import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.services.utils.MyMapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;


public class CacheDataServiceImplTest {

    CacheDataService cacheDataService;

    @Before
    public void setUp() throws Exception {
        cacheDataService = new CacheDataServiceImpl();
        init();
    }

    private void init() {
        Map<String, BaseModel> base = new HashMap<>();
        base.put("test0", new BaseModel("test0", 1L, ImmutableMap.<String, Object>builder().put("value", 1f).put("valid", 1).build()));
        cacheDataService.setLatest(base);
    }

    @Test
    public void getLatest() throws Exception {

        Map<String, BaseModel> expectedMap = new HashMap<>();

        expectedMap.put("test1", new BaseModel("test1", 2L, ImmutableMap.<String, Object>builder().put("value", 2f).put("valid", 1).build()));
        assertTrue(MyMapUtils.mapDifference(expectedMap, cacheDataService.getLatest()).size() != 0);

        assertNull(cacheDataService.getLatest().get("test1"));

        cacheDataService.setLatest(expectedMap);

        assertNotNull(cacheDataService.getLatest().get("test0"));
        assertNotNull(cacheDataService.getLatest().get("test1"));

    }

    @Test
    public void setLatest() throws Exception {
        Map<String, BaseModel> expectedMap = new HashMap<>();
        expectedMap.put("test0", new BaseModel("test0", 100L, ImmutableMap.<String, Object>builder().put("value", 100f).put("valid", 1).build()));
        expectedMap.put("test1", new BaseModel("test1", 200L, ImmutableMap.<String, Object>builder().put("value", 200f).put("valid", 1).build()));
        cacheDataService.setLatest(expectedMap);

        assertTrue(MyMapUtils.mapDifference(expectedMap, cacheDataService.getLatest()).size() == 0);
    }

    @Test
    public void lastElementStream() {

        Optional<String> optionalJava = Stream.of("a", "b", "c").reduce(
                (a, b) -> b);

        assertEquals("c", optionalJava.get());

        String lastValue = Stream.of("a", "b", "c").reduce((a, b) -> b)
                .orElse("false");

        assertEquals("c", lastValue);
    }
}