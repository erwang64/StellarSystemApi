package edu.esiea.stellarsystemapi.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
class StellarSystemServiceTest {
	
	@Mock
	private StellarSystemRepository repo;
	
	@Inject
	private  StellarSystemService service;
	

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetStellarSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStellarSystems() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteStellarSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateStellarSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateStellarSystem() {
		fail("Not yet implemented");
	}

}
