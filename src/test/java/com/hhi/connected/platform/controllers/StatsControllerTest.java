package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.Application;
import com.hhi.connected.platform.handlers.StatsTSDBHandler;
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
public class StatsControllerTest {

    @Mock
    StatsTSDBHandler statsTSDBHandler;

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
        when(statsTSDBHandler.getAllStats(isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetEquipmentsProducts() throws Exception {
        when(statsTSDBHandler.getProductsOfEquipments(isA(String.class), isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/stats/products/equipments")
                .param("vdm","foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetDevicesProducts() throws Exception {
        when(statsTSDBHandler.getProductsOfDevices(isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/stats/products/equipments/devices")
                .param("vdm","foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetEquipmentsAccumulation() throws Exception {
        when(statsTSDBHandler.getAccumulationOfEquipments(isA(String.class), isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/stats/accumulation/equipments")
                .param("vdm","foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetDevicesAccumulation() throws Exception {
        when(statsTSDBHandler.getAccumulationOfDevices(isA(HttpMethod.class))).thenReturn(null);

        mockMvc.perform(get("/stats/accumulation/equipments/devices")
                .param("vdm","foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}