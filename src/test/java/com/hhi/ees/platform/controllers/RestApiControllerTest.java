package com.hhi.ees.platform.controllers;

import com.hhi.ees.platform.Application;
import com.hhi.ees.platform.handlers.TSDBHandler;
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
public class RestApiControllerTest {

    @Mock
    TSDBHandler tsdbHandler;

    @InjectMocks
    private RestApiController restApiController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restApiController).build();
    }

    @Test
    public void testGetMeasurements() throws Exception {
        when(tsdbHandler.getMeasurements(isA(String.class), isA(HttpMethod.class))).thenReturn(String.class);

        mockMvc.perform(get("/api/query/measurements/{db}", "db")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testQueries() throws Exception {
        when(tsdbHandler.queries(isA(String.class), isA(String.class), isA(String.class), isA(HttpMethod.class))).thenReturn(String.class);

        mockMvc.perform(get("/api/query")
                .param("db","hivaas")
                .param("epoch","ms")
                .param("q","queries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}