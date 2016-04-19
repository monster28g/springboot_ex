package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.Application;
import com.hhi.connected.platform.handlers.TSDBHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class StatsControllerTest {

    @Mock
    TSDBHandler tsdbHandler;

    @InjectMocks
    private StatsController statsController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statsController).build();
    }

    @Test
    public void testGetStats() throws Exception {

    }

    @Test
    public void testGetEquipmentsProducts() throws Exception {

    }

    @Test
    public void testGetDevicesProducts() throws Exception {

    }

    @Test
    public void testGetEquipmentsAccumulation() throws Exception {

    }

    @Test
    public void testGetDevicesAccumulation() throws Exception {

    }

}