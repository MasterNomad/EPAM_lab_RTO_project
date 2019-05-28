package com.epam.lab.rto.controller;

import com.epam.lab.rto.RailwayTicketOffice;
import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.repository.interfaces.IUserRepository;
import com.epam.lab.rto.service.interfaces.IRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RailwayTicketOffice.class})
public class PersonalAreaControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private IRequestService requestService;

    @MockBean
    private IUserRepository userRepository;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testUserHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/personal-area"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testAdminHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/requests"));
    }

    @Test
    @WithMockUser
    public void testPersonalArea() throws Exception {
        when(requestService.getActiveRequestsByUser(any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.getUserByEmail(any()))
                .thenReturn(new User());

        this.mockMvc.perform(get("/personal-area"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/bills"))
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void testHistoryPersonalArea() throws Exception {
        when(requestService.getActiveRequestsByUser(any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.getUserByEmail(any()))
                .thenReturn(new User());

        this.mockMvc.perform(get("/personal-area?history=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/history"))
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attributeExists("user"));
    }
}