package com.hhi.connected.platform.services;

import com.hhi.connected.platform.services.utils.ShipTopologyModule;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

public class MyParserImplTest {

    MyParserImpl myParser;

    ShipTopologyModule shipTopologyModule;


    @Before
    public void setUp() throws Exception {
        myParser = new MyParserImpl();
        myParser.setShipTopologyModule(new ShipTopologyModule());
    }

    @Test
    public void parse() throws Exception {
        String message = FileUtils.readFileToString(new File(this.getClass().getResource("/message.json").getFile()));
        assertNotNull(myParser.parse(message));
        assertNull(myParser.parse(""));
        assertNull(myParser.parse(null));

    }
}