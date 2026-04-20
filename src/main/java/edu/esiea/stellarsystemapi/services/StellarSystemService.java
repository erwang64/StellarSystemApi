package edu.esiea.stellarsystemapi.services;

import java.util.Optional;

import edu.esiea.stellarsystemapi.exceptions.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.repositories.StellarSystemRepository;


@Service
@Transactional
public class StellarSystemService {

    private final StellarSystemRepository repository;

    public StellarSystemService(final StellarSystemRepository repository) {
        this.repository = repository;
    }

    public Optional<StellarSystem> getStellarSystem(final int id) {
        return repository.findById(id);
    }

    public Iterable<StellarSystem> getStellarSystems() {
        return repository.findAll();
    }

    public void deleteStellarSystem(final int id) {
        repository.deleteById(id);
    }

    public StellarSystem createStellarSystem(final StellarSystem system) throws ServiceException {
        if (system.getId() > 0) {
            throw new ServiceException("Impossible de créer un système dont l'ID est défini.");
        }

        validateUnicity(system);
        return repository.save(system);
    }

    public StellarSystem updateStellarSystem(final StellarSystem system) throws ServiceException {
        final int id = system.getId();

        if (id <= 0) {
            throw new ServiceException("Impossible de modifier un système dont l'ID n'est pas défini.");
        }

        if (repository.findById(id).isEmpty()) {
            throw new ServiceException("Impossible de modifier un système inexistant.");
        }

        validateUnicity(system);
        return repository.save(system);
    }

    private void validateUnicity(final StellarSystem system) throws ServiceException {
        checkNameUnicity(system);
        checkPositionUnicity(system);
    }

    private void checkNameUnicity(final StellarSystem system) throws ServiceException {
        Optional<StellarSystem> existing = repository.findByName(system.getName());

        if (existing.isPresent() && existing.get().getId() != system.getId()) {
            throw new ServiceException("Un système existe déjà avec ce nom.");
        }
    }

    private void checkPositionUnicity(final StellarSystem system) throws ServiceException {
        Optional<StellarSystem> existing = repository.findByPosXAndPosY(system.getPosX(), system.getPosY());

        if (existing.isPresent() && existing.get().getId() != system.getId()) {
            throw new ServiceException("Un système existe déjà à cette position.");
        }
    }
}
