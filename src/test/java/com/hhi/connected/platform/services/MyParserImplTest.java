package com.hhi.connected.platform.services;

import com.hhi.connected.platform.handlers.ModelHandler;
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

    ShipTopologyModule shipTopologyModule;


    @Before
    public void setUp() throws Exception {
        myParser = new MyParserImpl();
        myParser.setShipTopologyModule(new ShipTopologyModule());
        myParser.setModelHandler(new ModelHandler());
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

        Map<String, List<BaseModel>> models = new HashMap<>();

        models.put("test0",
                Arrays.asList(
                        new BaseModel("test0", 1L, 1f, 1),
                        new BaseModel("test0", 2L, 1f, 1),
                        new BaseModel("test0", 3L, 3f, 1),
                        new BaseModel("test0", 4L, 1f, 1),
                        new BaseModel("test0", 5L, 5f, 1),
                        new BaseModel("test0", 6L, 5f, 1),
                        new BaseModel("test0", 7L, 5f, 1)
                )
        );

        List list = Optional.ofNullable(models.entrySet().stream().map(e -> myParser.removeSequentialDuplicates(e)).collect(Collectors.toList()).get(0))
                .orElse(null).getValue();
        assertNotNull(list);
        assertTrue(list.size() == 4);


    }
}