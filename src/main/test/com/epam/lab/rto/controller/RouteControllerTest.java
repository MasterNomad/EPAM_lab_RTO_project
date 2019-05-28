package com.epam.lab.rto.controller;

import com.epam.lab.rto.RailwayTicketOffice;
import com.epam.lab.rto.dto.Locomotive;
import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.service.interfaces.IRouteService;
import com.epam.lab.rto.service.interfaces.ITrainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RailwayTicketOffice.class})
public class RouteControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ITrainService trainService;

    @MockBean
    private IRouteService routeService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    public void testWebContext() {
        ServletContext servletContext = context.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(context.getBean(RouteController.class));
    }

    @Test
    public void testGetRoutesController() throws Exception {
        this.mockMvc.perform(get("/admin/route"))
                .andExpect(status().isOk())
                .andExpect(view().name("route/route"))
                .andExpect(model().attributeExists("routes"));
    }

    @Test
    public void testRefreshCarriage() throws Exception {
        when(routeService.getRouteByTitle(any()))
                .thenReturn(new Route(any()));

        mockMvc.perform(get("/route/getstations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testRestGetLocomotiveSpeed() throws Exception {
        int expResult = 700;

        Locomotive locomotive = new Locomotive();
        locomotive.setAverageSpeed(expResult);

        when(trainService.getLocomotiveByName("name")).thenReturn(locomotive);

        int result = Integer.parseInt(mockMvc.perform(get("/route/getlocomotivespeed?locomotiveName=name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn()
                .getResponse().getContentAsString());

        assertEquals(expResult, result);
    }
}