package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.Application;
import com.hhi.ees.platform.services.DataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource({
        "/properties/gateway.properties",
        "/properties/db.properties",
        "/properties/push_service.properties"
})
public class DataControllerTest {

    @Mock
    DataService dataService;

    @InjectMocks
    private DataController dataController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }

    @Test
    public void getMeasurements() throws Exception {
        when(dataService.getMeasurements(isA(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/data/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getTrends() throws Exception {
        when(dataService.getTrends(isA(String.class), isA(String.class), isA(Long.class), isA(Long.class), isA(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/data/trends")
                .param("vdm", "foo/bar")
                .param("from", "100")
                .param("to", "200")
                .param("epoch", "m")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getTrendsLast() throws Exception {
        when(dataService.getTrendsLast(isA(String.class), isA(String.class), isA(String.class), isA(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/data/trends")
                .param("vdm", "foo/bar")
                .param("last", "5h")
                .param("epoch", "m")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getTrendsFail() throws Exception {
        when(dataService.getTrends(isA(String.class), isA(String.class), isA(Long.class), isA(Long.class), isA(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/data/trends")
                .param("vdm", "foo/bar")
                .param("from", "200")
                .param("to", "100")
                .param("epoch", "m")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void getTrendsDefault() throws Exception {
        when(dataService.getTrends(isA(String.class), isA(String.class), isA(Long.class), isA(Long.class), isA(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/measurements/data/trends")
                .param("vdm", "foo/bar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}