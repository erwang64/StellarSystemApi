package edu.esiea.stellarsystemapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.esiea.stellarsystemapi.exceptions.ServiceException;
import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;

@ExtendWith(MockitoExtension.class)
class StellarSystemServiceTest {
	
	@Mock
	private StellarSystemRepository repo;
	
	@InjectMocks
	private StellarSystemService service;
	
	private static final String SYS_NAME = "Tchoupi";
	private static final int SYS_POS_X = 22;
	private static final int SYS_POS_Y = 35;

	StellarSystem sys;

	@BeforeEach
	void setUp() throws Exception {
	    this.sys = new StellarSystem(SYS_NAME, SYS_POS_X, SYS_POS_Y);
	}
	
	private void configMock() {
	    when(this.repo.findByName(SYS_NAME)).thenReturn(Optional.of(this.sys));
	    when(this.repo.findByPosXAndPosY(SYS_POS_X, SYS_POS_Y))
	            .thenReturn(Optional.of(this.sys));
	}

	@Test
	void testCreateStellarSystem() throws ServiceException {
	    // 1. Configuration pour le cas normal : la base est "vide"
	    when(this.repo.findByName(SYS_NAME)).thenReturn(Optional.empty());
	    when(this.repo.findByPosXAndPosY(SYS_POS_X, SYS_POS_Y)).thenReturn(Optional.empty());
	    when(this.repo.save(this.sys)).thenReturn(this.sys);
	    
	    // Cas normal
	    StellarSystem result = this.service.createStellarSystem(this.sys);
	    assertNotNull(result);
	    verify(this.repo).save(this.sys);
	    
	    // 2. On change la config : maintenant les éléments existent
	    configMock(); 
	    
	    // Cas où le système a déjà un id
	    this.sys.setId(1);
	    assertThrows(ServiceException.class, () -> {
	        this.service.createStellarSystem(this.sys);
	    }, "Créer un système ayant déjà un id devrait être impossible");
	    
	    // Tentative de création avec un nom déjà existant
	    StellarSystem sysDuplicate = new StellarSystem(SYS_NAME, 3, 4);
	    assertThrows(ServiceException.class, () -> {
	        this.service.createStellarSystem(sysDuplicate);
	    }, "Créer un système un nom préexistant devrait être impossible");
	    
	    // Tentative de création à une position déjà existante
	    StellarSystem sys2 = new StellarSystem("Toto", SYS_POS_X, SYS_POS_Y);
	    assertThrows(ServiceException.class, () -> {
	        this.service.createStellarSystem(sys2);
	    }, "Créer un système à une position préexistante devrait être impossible");
	}

	@Test
	void testUpdateStellarSystem() throws ServiceException {
	    configMock();
	    when(this.repo.save(this.sys)).thenReturn(this.sys);
	    
	    // cas où le système n'a pas d'id
	    assertThrows(ServiceException.class, () -> {
	        this.service.updateStellarSystem(this.sys);
	    }, "Update un système sans id devrait être impossible");
	    
	    // cas normal
	    this.sys.setId(1); 
	    when(this.repo.findById(1)).thenReturn(Optional.of(this.sys));
	    StellarSystem result = this.service.updateStellarSystem(this.sys);
	    assertNotNull(result);
	    verify(this.repo).save(this.sys);
	    
	    // Tentative de modification vers un nom déjà existant
	    StellarSystem sys1 = new StellarSystem(SYS_NAME, 3, 4);
	    sys1.setId(2);
	    assertThrows(ServiceException.class, () -> {
	        this.service.updateStellarSystem(sys1);
	    }, "Modifier un système vers un nom préexistant devrait être impossible");
	    
	    // Tentative de création à une position déjà existante
	    StellarSystem sys2 = new StellarSystem("Toto", SYS_POS_X, SYS_POS_Y);
	    sys2.setId(3);
	    assertThrows(ServiceException.class, () -> {
	        this.service.updateStellarSystem(sys2);
	    }, "Modifier un système vers une position préexistante devrait être impossible");
	}
	
	@Test
	void testDeleteStellarSystem() {
	    this.service.deleteStellarSystem(1);
	    verify(this.repo).deleteById(1);
	}

	@Test
	void testGets() {
	    // 1. Test de la récupération par ID
	    when(this.repo.findById(1)).thenReturn(Optional.of(this.sys));
	    Optional<StellarSystem> sys1 = this.service.getStellarSystem(1);
	    verify(this.repo).findById(1);
	    assertTrue(sys1.isPresent(), "Le getById devrait ramener un système");
	}
}