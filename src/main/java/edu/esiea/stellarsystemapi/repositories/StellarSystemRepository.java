package edu.esiea.stellarsystemapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.esiea.stellarsystemapi.model.StellarSystem;

@Repository
public interface StellarSystemRepository extends JpaRepository<StellarSystem, Integer>{
	
	Optional<StellarSystem> findByName(String name);
	
	Optional<StellarSystem> findByPosXAndPosY(Integer posX, Integer posY);
	
	Optional<StellarSystem> findByStarId(int starId);
	
	Optional<StellarSystem> findByPlanetsId(int planetId);
}