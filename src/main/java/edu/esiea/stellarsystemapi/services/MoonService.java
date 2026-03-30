package edu.esiea.stellarsystemapi.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.model.Moon;
import edu.esiea.stellarsystemapi.repositories.MoonRepository;

@Service
@Transactional
public class MoonService {

    private final MoonRepository repo;

    public MoonService(MoonRepository repo) {
        this.repo = repo;
    }

    public Optional<Moon> getMoon(final int id) {
        return this.repo.findById(id);
    }

    public Iterable<Moon> getMoons() {
        return this.repo.findAll();
    }

    public List<Moon> getMoonsByPlanetId(int planetId) {
        return this.repo.findByPlanetId(planetId);
    }

    public void deleteMoon(Integer id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("ID invalide.");
        }
        Optional<Moon> moon = repo.findById(id);
        if (moon.isEmpty()) {
            throw new ServiceException("Lune introuvable.");
        }
        repo.deleteById(id);
    }

    public Moon createMoon(final Moon moonToCreate) throws ServiceException {
        if (moonToCreate.getId() > 0) {
            throw new ServiceException("Impossible de créer une lune dont l'ID est défini.");
        }
        if (moonToCreate.getName() == null || moonToCreate.getName().isBlank()) {
            throw new ServiceException("Impossible de créer une lune sans nom.");
        }
        Optional<Moon> test = repo.findByName(moonToCreate.getName());
        if (test.isPresent()) {
            throw new ServiceException("Impossible de créer une lune qui existe déjà.");
        }
        return this.repo.save(moonToCreate);
    }
}