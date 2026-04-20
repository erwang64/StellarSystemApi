package edu.esiea.stellarsystemapi.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import edu.esiea.stellarsystemapi.model.Planet;
import edu.esiea.stellarsystemapi.model.Star;
import edu.esiea.stellarsystemapi.model.StarType;
import edu.esiea.stellarsystemapi.model.StellarSystem;

@DataJpaTest
class StellarSystemRepositoryTest {
	
	@Autowired
	StellarSystemRepository sysRepo;
	@Autowired
	StarRepository starRepo;
	@Autowired
	PlanetRepository planetRepo;
	
	private static final String SYS_NAME = "Système solaire";
	private static final int SYS_POS_X = 13;
	private static final int SYS_POS_Y = 12;
	private static int starId;
	private static int planetId;

	@BeforeEach
	void setUp() throws Exception {
		Star star = new Star("Soleil", 1988f, StarType.NAINE_JAUNE, 5900.0f);
		star = this.starRepo.save(star);
		starId = star.getId();
		Planet planet = new Planet("Terre", 1.0f , 158.9f, 365.25f, true , true , "70% aeote , 18 blablabla");
		
		planet = this.planetRepo.save(planet);
		planetId = planet.getId();
		
		StellarSystem sys = new StellarSystem(SYS_NAME , SYS_POS_X, SYS_POS_Y);
		sys.setStar(star);
		sys.addPlanet(planet);
		this.sysRepo.save(sys);
	}

	@Test
	void testFindByName() {
		Optional<StellarSystem> ret = this.sysRepo.findByName(SYS_NAME);
		assertTrue(ret.isPresent(), "On devrait retrouver le ssyteme");
		StellarSystem sys = ret.get();
		assertEquals(SYS_NAME, sys.getName(), "Le nom est pas correcte");
		
		ret = this.sysRepo.findByName("Existe pas");
		assertTrue(ret.isEmpty(),"On devrait pas trouver de system");
		
		ret = this.sysRepo.findByName(null);
		assertTrue(ret.isEmpty(), " On devrait pas detrouver de system avec null");
	}
	
	@Test
	void testFindByPosXAndPosY() {
		
		Optional<StellarSystem> ret = this.sysRepo.findByPosXAndPosY(SYS_POS_X,SYS_POS_Y);
		assertTrue(ret.isPresent(), "On devrait retrouver le system par sa posiiton");
		
		StellarSystem sys = ret.get();
		assertEquals(SYS_NAME, sys.getName(),"Le nom n'est pas coreect");
		ret = this.sysRepo.findByPosXAndPosY(666, 666);
		assertTrue(ret.isEmpty(), "On devait pas de trouver de system ici");
	}
	
	@Test
	void testFindByStarId() {
		Optional<StellarSystem> ret = this.sysRepo.findByStarId(starId);
		assertTrue(ret.isPresent(),"On devrait retrouver le systeme par l'id de son etoile");
		StellarSystem sys = ret.get();
		assertEquals(SYS_NAME, sys.getName(),"le nom est pas correcte");
		
		ret = this.sysRepo.findByStarId(666);
		assertTrue(ret.isEmpty(),"On ne devrait pas de trouver de system");
		
		sys.setStar(null);
		this.sysRepo.save(sys);
		ret = this.sysRepo.findByStarId(starId);
		this.sysRepo.save(sys);
	}
	
	@Test
	void testFindByPlanetsId() {
		Optional<StellarSystem> ret = this.sysRepo.findByPlanetsId(planetId);
		assertTrue(ret.isPresent(),"On devrait retrouver le systeme par l'id de son etoile");
		StellarSystem sys = ret.get();
		assertEquals(SYS_NAME, sys.getName(),"le nom est pas correcte");
		
		ret = this.sysRepo.findByPlanetsId(666);
		assertTrue(ret.isEmpty(),"On ne devrait pas de trouver de system");
		
		Planet planet = this.planetRepo.findById(planetId).get();
		sys.removePlanet(planet);
		this.sysRepo.save(sys);
		ret = this.sysRepo.findByPlanetsId(planetId);
		assertTrue(ret.isEmpty(),"CE system n'a plus de planete");
		
	}
	
	@Test
	void testEntityRestrictions() {
		
		StellarSystem sys = new StellarSystem(null, 22 ,35);
		assertThrows(DataIntegrityViolationException.class, () -> {this.sysRepo.save(sys);} ,"le nom peut pas etre nul");
		
		StellarSystem sys2 = new StellarSystem(SYS_NAME, 22 , 35);
		assertThrows(DataIntegrityViolationException.class, () -> {this.sysRepo.saveAndFlush(sys2); } ,"Deux syteme peuvent pas voir le meme nom");
		
		StellarSystem sys3 = new StellarSystem("Nom1", 22, 35);
		sys3 = this.sysRepo.save(sys3);
		
		StellarSystem sys4 = this.sysRepo.findById(sys3.getId()).get();
		
		assertThrows(AssertionFailure.class, () -> {this.sysRepo.saveAndFlush(sys4);} ,"Deux system modifier peuvent pas avoir le mme nom");	
	}

}
