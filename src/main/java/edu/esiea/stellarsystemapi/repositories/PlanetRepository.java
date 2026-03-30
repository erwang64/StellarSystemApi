package edu.esiea.stellarsystemapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.esiea.stellarsystemapi.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer>{
	Optional<Planet> findByMoonsId(Integer moonId);
	
	Optional<Planet> findByName(String name);
}
