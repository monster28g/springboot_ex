package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.Application;
import com.hhi.ees.platform.handlers.ConfigTSDBHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ConfigControllerTest {

    @Mock
    ConfigTSDBHandler configTSDBHandler;

    @InjectMocks
    private ConfigController configController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(configController).build();
    }

    @Test
    public void testGetMeasurementsConfigs() throws Exception {
        when(configTSDBHandler.getAllConfigs(isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/configs")
                .param("vdm","foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllConfigs() throws Exception {
        when(configTSDBHandler.getConfigLatestOne(isA(String.class), isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/configs/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}