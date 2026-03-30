package edu.esiea.stellarsystemapi.services;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;

@Service
@Transactional
public class StellarSystemService {
	
	private final StellarSystemRepository repo;
	
	public StellarSystemService(StellarSystemRepository repo) {
		this.repo = repo;
	}
	
	public Optional<StellarSystem> getStellarSystem(final int id){
		return this.repo.findById(id);
	}
	
	public Iterable<StellarSystem> getStellarSystems(){
		return this.repo.findAll();
	}
	
	public void deleteStellarSystem(int id) {
		repo.deleteById(id);
	}
	
	public StellarSystem createStellarSystem(final StellarSystem sysToCreate) throws ServiceException{
		if (sysToCreate.getId() > 0) {
			throw new ServiceException("Impossible de créer un système dont l'ID est défini.");
		}
		if (sysToCreate.getName() == null || sysToCreate.getName().isBlank()) {
			throw new ServiceException("Impossible de créer un système sans nom.");
		}
		Optional<StellarSystem> test = repo.findByName(sysToCreate.getName());
		if (test.isPresent()) {
			throw new ServiceException("Impossible de créer un système dont le nom existe déjà");
		}
		test = repo.findByPosXAndPosY(sysToCreate.getPosX(), sysToCreate.getPosY());
		if (test.isPresent()) {
			throw new ServiceException("Impossible de créer un système dont la position est déjà occupée.");
		}
		return this.repo.save(sysToCreate);
	}
	
	public StellarSystem updateStellarSystem(final StellarSystem sysToUpdate) throws ServiceException {
		int id = sysToUpdate.getId();
		if (id <= 0) {
			throw new ServiceException("Impossible de modifier un système dont l'ID n'est pas défini.");
		}
		Optional<StellarSystem> test = repo.findById(id);
		if (test.isEmpty()) {
			throw new ServiceException("Impossible de modifier un système inexistant.");
		}
		return this.repo.save(sysToUpdate);
	}
}
