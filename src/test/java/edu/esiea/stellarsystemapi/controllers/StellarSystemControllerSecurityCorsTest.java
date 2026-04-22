package edu.esiea.stellarsystemapi.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.services.PlanetService;
import edu.esiea.stellarsystemapi.services.StarService;
import edu.esiea.stellarsystemapi.services.StellarSystemService;

import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "test", "JWTtest" })
class StellarSystemControllerSecurityCorsTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

    @MockitoBean
    private StellarSystemService sysService;

    @MockitoBean
    private edu.esiea.stellarsystemapi.security.filters.JwtAuthenticationFilter jwtAuthenticationFilter;


    @BeforeEach
    void setup() throws Exception {
        doAnswer(invocation -> {
            jakarta.servlet.FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldAllowAccessForUser() throws Exception {
        when(this.sysService.getStellarSystem(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/StellarSystem/1")
                .header("Origin", "http://localhost:4200"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void shouldDenyAccessForWrongRole() throws Exception {
        when(this.sysService.getStellarSystem(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/StellarSystem/1")
                .header("Origin", "http://localhost:4200"))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldAllowCorsForConfiguredOrigin() throws Exception {
        when(this.sysService.getStellarSystem(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/system/1").header("Origin", "http://localhost:4200"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldRejectCorsForUnknownOrigin() throws Exception {
        when(this.sysService.getStellarSystem(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/system/1").header("Origin", "http://couscous.com"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
	
}
