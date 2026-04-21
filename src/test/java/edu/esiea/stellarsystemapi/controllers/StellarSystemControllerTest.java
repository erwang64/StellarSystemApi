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
	
	@Test
	void shouldReturn404WhenSystemNotFound() throws Exception {
	    // On configure le Mock pour qu'il ne renvoie rien.
	    when(this.sysService.getStellarSystem(1)).thenReturn(Optional.empty());
	    
	    this.mockMvc.perform(get("/api/system/1"))
	            .andExpect(status().isNotFound());
	}

	@Test
	void shouldReturn400WhenDtoIsInvalid() throws Exception {
	    // json avec un nom vide et des positions négatives (supposées invalides)
	    String invalidJson = """
	        {
	            "name": "",
	            "posX": -1,
	            "posY": -5
	        }
	        """;
	        
	    this.mockMvc.perform(post("/api/StellarSystem")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(invalidJson))
	            // On attend un statut BAD_REQUEST (400)
	            .andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldUpdateSystemWithPut() throws Exception {
	    // 1. Il FAUT simuler que le système existe déjà, sinon le contrôleur renvoie 404 (ligne 88)
	    when(this.sysService.getStellarSystem(1)).thenReturn(Optional.of(this.sys));
	    
	    // 2. On simule la mise à jour
	    when(this.sysService.updateStellarSystem(any())).thenReturn(this.sys);
	    
	    // Un JSON complet et valide
	    String updatedJson = """
	        {
	            "name": "Solar Updated",
	            "posX": 10,
	            "posY": 20
	        }
	        """;
	        
	    // Attention à l'URL : /api/StellarSystem/1
	    this.mockMvc.perform(put("/api/StellarSystem/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(updatedJson))
	            .andExpect(status().isOk()); 
	            
	    verify(this.sysService).updateStellarSystem(any());
	}
	
	@Test
	void shouldPartialUpdateSystemWithPatch() throws Exception {
	    // 1. Comme pour le PUT, on simule d'abord que le système existe (ligne 106)
	    when(this.sysService.getStellarSystem(1)).thenReturn(Optional.of(this.sys));
	    
	    // 2. On simule la mise à jour
	    when(this.sysService.updateStellarSystem(any())).thenReturn(this.sys); 
	    
	    // À cause de votre annotation @Valid (ligne 103), il faut envoyer un JSON 
	    // qui respecte vos règles (pas de nom vide, etc.), sinon Spring renvoie 400
	    String patchJson = """
	        {
	            "name": "Nouveau Nom Patch",
	            "posX": 1,
	            "posY": 2
	        }
	        """;
	        
	    // Attention à l'URL : /api/StellarSystem/1
	    this.mockMvc.perform(patch("/api/StellarSystem/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(patchJson))
	            .andExpect(status().isOk());
	            
	    verify(this.sysService).updateStellarSystem(any());
	}
	
	

}
