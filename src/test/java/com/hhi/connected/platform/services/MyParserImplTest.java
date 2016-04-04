package com.hhi.connected.platform.services;

import com.hhi.connected.platform.handlers.ModelHandlerImpl;
import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.services.utils.ShipTopologyModule;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class MyParserImplTest {

    MyParserImpl myParser;

    @Before
    public void setUp() throws Exception {
        myParser = new MyParserImpl();
        myParser.setShipTopologyModule(new ShipTopologyModule());
        myParser.setModelHandler(new ModelHandlerImpl());
        myParser.setCacheDataService(new CacheDataServiceImpl());
    }

    @Test
    public void testParse() throws Exception {
        String message = FileUtils.readFileToString(new File(this.getClass().getResource("/message.json").getFile()));
        System.out.println(myParser.parse(message));
        assertNotNull(myParser.parse(message));
        assertNull(myParser.parse(""));
        assertNull(myParser.parse(null));

    }

    @Test
    public void testRemoveSequentialDuplicates() throws Exception {

        Map<String, BaseModel> base = new HashMap<>();
        base.put("test0", new BaseModel("test0", 10L, 1f, 1));
        base.put("test1", new BaseModel("test1", 20L, 2f, 1));
        myParser.getCacheDataService().setLatest(base);

        Map<String, List<BaseModel>> models = new HashMap<>();

        models.put("test0",
                Arrays.asList(
                        new BaseModel("test0", 100L, 1f, 1),
                        new BaseModel("test0", 200L, 1f, 1),
                        new BaseModel("test0", 300L, 3f, 1),
                        new BaseModel("test0", 400L, 1f, 1),
                        new BaseModel("test0", 500L, 5f, 1),
                        new BaseModel("test0", 600L, 5f, 1),
                        new BaseModel("test0", 700L, 5f, 1)
                )
        );

        List<BaseModel> list = Optional.ofNullable(models.entrySet().stream().map(e -> myParser.removeSequentialDuplicates(e)).collect(Collectors.toList()).get(0))
                .orElse(null).getValue();
        assertNotNull(list);

        assertTrue(isNotDuplicated(list));

    }

    private boolean isNotDuplicated(List<BaseModel> list) {
        Set<Float> allItems = new HashSet<>();
        return list.stream().filter(e -> !allItems.add(e.getValue())).collect(Collectors.toSet()).isEmpty();
    }

    @Test
    public void testInsertFirst() throws Exception {
        String firstValue = "first";
        List l = new ArrayList<>();
        l.add("was a first");
        l.add(200);
        l.add("the last");

        l.add(0, firstValue);
        assertTrue(l.stream().findFirst().orElse(null).equals(firstValue));

        l.stream().forEach(System.out::println);
    }


}