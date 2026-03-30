package edu.esiea.stellarsystemapi.services;

import java.rmi.server.ServerCloneException;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.model.Planet;
import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.repositories.PlanetRepository;
import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;

@Service
@Transactional
public class PlanetService {

    private final PlanetRepository repo;
	private final StellarSystemRepository sysRepo;
	
	public PlanetService(PlanetRepository repo, StellarSystemRepository sysRepo) {
		this.repo = repo;
		this.sysRepo = sysRepo;
	}
	
	public Optional<Planet> getPlanetById(final int id){
		return this.repo.findById(id);
	}
	
	public Iterable<Planet> getAllPlanets(){
		return this.repo.findAll();
	}
	
	public void deletePlanet(int id) throws ServiceException{
		Optional<Planet> test = this.repo.findById(id);
		if(test.isEmpty()) {
			throw new ServiceException("Impossible de supprimer une planète inexistante");
		}
		Optional<StellarSystem> check = this.sysRepo.findByPlanetsId(id);
		if(check.isPresent()) {
			StellarSystem sys = check.get();
			sys.removePlanet(test.get());
			this.sysRepo.save(sys);
		}
		this.repo.deleteById(id);
	}
	
	public Planet createPlanet(final Planet planetToCreate) throws ServerCloneException {
		if (planetToCreate.getId() > 0 ) {
			throw new ServiceException("Impossible de créer une planète dont l'ID est défini.");
		}
		Optional<Planet> check = this.repo.findByName(planetToCreate.getName());
		if (check.isPresent()) {
			throw new ServiceException("Impossible de créer la planète, une autre existe déjà avec le nom \"".concat(planetToCreate.getName()).concat("\""));
		}
		return this.repo.save(planetToCreate);
	}
	
	public Planet updatePlanet(final Planet planet) throws ServiceException {
		int id = planet.getId();
		if (id <= 0) {
			throw new ServiceException("Impossible de modifer une planete dont l'ID n'est pas défini.");
		}
		Optional<Planet> test = this.repo.findById(id);
		if(test.isEmpty()) {
			throw new ServiceException("Impossible de modifier une planète inexistante.");
		}
		return this.repo.save(planet);
	}
	
	
}
