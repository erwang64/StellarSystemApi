package edu.esiea.stellarsystemapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.esiea.stellarsystemapi.model.Moon;

@Repository
public interface MoonRepository extends JpaRepository<Moon, Integer>{
	List<Moon> findByPlanetId(Integer planetId);
	
	Optional<Moon> findByName(String name);
}
