package edu.esiea.stellarsystemapi.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.services.PlanetService;
import edu.esiea.stellarsystemapi.services.StarService;
import edu.esiea.stellarsystemapi.services.StellarSystemService;

@WebMvcTest(StellarSystemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class StellarSystemControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StellarSystemService sysService;

    @MockitoBean
    private StarService starService;

    @MockitoBean
    private PlanetService planetService;

    private StellarSystem sys;

    @BeforeEach
    void setUp() {
    			this.sys = new StellarSystem("Solar",1,2);
    			this.sys.setId(1);
    }

	@Test
	void shouldGetSystemById() throws Exception {
		when(this.sysService.getStellarSystem(1)).thenReturn(Optional.of(this.sys));
		this.mockMvc.perform(get("/api/StellarSystem/1")).andExpect(status().isOk());
		verify(this.sysService).getStellarSystem(1);
	}
	
	@Test
	void shouldGetAllSystems() throws Exception {
	    StellarSystem sys2 = new StellarSystem("Alpha", 3, 4);
	    sys2.setId(2); // ou 2L selon le type de votre ID
	    
	    List<StellarSystem> systems = List.of(this.sys, sys2);
	    
	    when(this.sysService.getStellarSystems()).thenReturn(systems);
	    
	    this.mockMvc.perform(get("/api/StellarSystem/all"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.length()").value(2))
	            .andExpect(jsonPath("$[0].name").value("Solar"))
	            .andExpect(jsonPath("$[1].name").value("Alpha"));
	            
	    verify(this.sysService).getStellarSystems();
	}
	
	@Test
	void shouldCreateSystem() throws Exception {
	    when(this.sysService.createStellarSystem(any())).thenReturn(this.sys);
	    
	    String json = """
	        {
	    			"name": "Solar",
	            "posX": 1,
	            "posY": 2
	        }
	        """;
	        
	    this.mockMvc.perform(post("/api/StellarSystem")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isCreated());
	            
	    verify(this.sysService).createStellarSystem(any());
	}

	@Test
	void shouldDeleteSystem() throws Exception {
	    when(this.sysService.getStellarSystem(1)).thenReturn(Optional.of(this.sys));
	    
	    this.mockMvc.perform(delete("/api/StellarSystem/1"))
	            .andExpect(status().isNoContent());
	            
	    verify(this.sysService).deleteStellarSystem(1);
	}
	
	

}
