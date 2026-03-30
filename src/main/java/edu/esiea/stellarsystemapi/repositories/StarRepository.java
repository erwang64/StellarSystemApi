package edu.esiea.stellarsystemapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.esiea.stellarsystemapi.model.Star;

@Repository
public interface StarRepository extends JpaRepository<Star, Integer>{
	Optional<Star> findByName(String name);
}
