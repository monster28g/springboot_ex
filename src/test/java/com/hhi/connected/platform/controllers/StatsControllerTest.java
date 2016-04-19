package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class StatsControllerTest {

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

        mockMvc.perform(get("/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetEquipmentsProducts() throws Exception {

        mockMvc.perform(get("/stats/products/equipments")
                .param("vdm","/stats/products/equipments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetDevicesProducts() throws Exception {

        mockMvc.perform(get("/stats/products/equipments/devices")
                .param("vdm","/stats/products/equipments/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetEquipmentsAccumulation() throws Exception {

        mockMvc.perform(get("/stats/accumulation/equipments")
                .param("vdm","/stats/accumulation/equipments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetDevicesAccumulation() throws Exception {

        mockMvc.perform(get("/stats/accumulation/equipments/devices")
                .param("vdm","/stats/accumulation/equipments/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}