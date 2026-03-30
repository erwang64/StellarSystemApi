package edu.esiea.stellarsystemapi.services;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.model.Star;
import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.repositories.StarRepository;
import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;

@Service
@Transactional
public class StarService {
	
	private final StarRepository repo;
	private final StellarSystemRepository sysRepo;
	
	public StarService(StarRepository repo, StellarSystemRepository sysRepo) {
		this.repo = repo;
		this.sysRepo = sysRepo;
	}
	
	public Optional<Star> getStar(final int id){
		return this.repo.findById(id);
	}
	
	public Iterable<Star> getStars(){
		return this.repo.findAll();
	}
	
	public void deleteStar(Integer id) throws ServiceException {
	    if (id == null || id <= 0) {
	        throw new ServiceException("ID invalide.");
	    }
	    Optional<Star> star = repo.findById(id);
	    if (star.isEmpty()) {
	        throw new ServiceException("Étoile introuvable.");
	    }
	    Optional<StellarSystem> system = sysRepo.findByStarId(id);
	    if (system.isPresent()) {
	        throw new ServiceException("Impossible de supprimer une étoile liée à un système.");
	    }
	    repo.deleteById(id);
	}
	
	public Star createStar(final Star starToCreate) throws ServiceException{
		if (starToCreate.getId() > 0) {
			throw new ServiceException("Impossible de créer une étoile dont l'ID est défini.");
		}
		if (starToCreate.getName() == null || starToCreate.getName().isBlank()) {
			throw new ServiceException("Impossible de créer une étoile sans nom.");
		}
		Optional<Star> test = repo.findByName(starToCreate.getName());
		if (test.isPresent()) {
			throw new ServiceException("Impossible de créer une étoile qui existe déjà.");
		}
		return this.repo.save(starToCreate);
	}
	
	public Star updateStar(final Star starToUpdate) throws ServiceException {
	    int id = starToUpdate.getId();
	    if (id <= 0) {
	        throw new ServiceException("Impossible de modifier une étoile dont l'ID n'est pas défini.");
	    }
	    Optional<Star> test = repo.findById(id);
	    if (test.isEmpty()) {
	        throw new ServiceException("Impossible de modifier une étoile inexistante.");
	    }
	    return this.repo.save(starToUpdate);
	}
	
	
}
