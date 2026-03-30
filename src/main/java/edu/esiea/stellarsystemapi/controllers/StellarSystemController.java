package edu.esiea.stellarsystemapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.esiea.stellarsystemapi.controllers.dto.StellarSystemRequest;
import edu.esiea.stellarsystemapi.controllers.dto.StellarSystemResponse;
import edu.esiea.stellarsystemapi.controllers.dto.error.EndPointException;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.controllers.dto.mappers.StellarSystemMapper;
import edu.esiea.stellarsystemapi.model.Planet;
import edu.esiea.stellarsystemapi.model.Star;
import edu.esiea.stellarsystemapi.model.StellarSystem;
import edu.esiea.stellarsystemapi.services.PlanetService;
import edu.esiea.stellarsystemapi.services.StarService;
import edu.esiea.stellarsystemapi.services.StellarSystemService;

@RestController
@RequestMapping(path = "/api/StellarSystem")
public class StellarSystemController {

	private final StellarSystemService stellarSystemService;
	private final StarService starService;
	private final PlanetService planetService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StellarSystemController.class);

	public StellarSystemController(StellarSystemService stellarSystemService, StarService starService, PlanetService planetService) {
		this.stellarSystemService = stellarSystemService;
		this.starService = starService;
		this.planetService = planetService;
	}

	@PostMapping
	public ResponseEntity<StellarSystemResponse> addStellarSystem(@Valid @RequestBody StellarSystemRequest stellarSystemDTO) throws EndPointException {
		LOGGER.debug("POST  /api/StellarSystem");
		StellarSystem s = StellarSystemMapper.toEntity(stellarSystemDTO);
		try {
			s = this.stellarSystemService.createStellarSystem(s);
			LOGGER.info("Système créé");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getId()).toUri();
		return ResponseEntity.created(uri).body(StellarSystemMapper.toResponse(s));
	}

	@GetMapping("/all")
	public ResponseEntity<List<StellarSystemResponse>> getAllStellarSystems() {
		return ResponseEntity.ok(StellarSystemMapper.toResponseList(this.stellarSystemService.getStellarSystems()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<StellarSystemResponse> getStellarSystemById(@PathVariable int id) throws EndPointException {
		LOGGER.debug("GET  /api/StellarSystem/all");
		Optional<StellarSystem> system = this.stellarSystemService.getStellarSystem(id);
		if (system.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, id);
		}
		LOGGER.info("Système trouvé");
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system.get()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<StellarSystemResponse> replace(@PathVariable int id, @Valid @RequestBody StellarSystemRequest stellarSystemDTO) throws EndPointException {
		LOGGER.debug("PUT  /api/StellarSystem/{id}");
		Optional<StellarSystem> optSystem = this.stellarSystemService.getStellarSystem(id);
		if (optSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, id);
		}
		StellarSystem system = StellarSystemMapper.toEntity(stellarSystemDTO);
		system.setId(id);
		try {
			system = this.stellarSystemService.updateStellarSystem(system);
			LOGGER.info("Modification PUT du système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<StellarSystemResponse> update(@PathVariable("id") int stellarSystemId, @Valid @RequestBody StellarSystemRequest stellarSystemDTO) throws EndPointException {
		LOGGER.debug("Patch  /api/StellarSystem/{id}");
		Optional<StellarSystem> optStellarSystem = this.stellarSystemService.getStellarSystem(stellarSystemId);
		if (optStellarSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, stellarSystemId);
		}
		StellarSystem modStellarSystem = optStellarSystem.get();
		modStellarSystem = StellarSystemMapper.patchWithRequest(stellarSystemDTO, modStellarSystem);
		try {
			modStellarSystem = this.stellarSystemService.updateStellarSystem(modStellarSystem);
			LOGGER.info("Modification PATCH du système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(modStellarSystem));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) throws EndPointException {
		LOGGER.debug("DELETE  /api/StellarSystem/{id}");
		Optional<StellarSystem> system = this.stellarSystemService.getStellarSystem(id);
		if (system.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, id);
		}
		try {
			this.stellarSystemService.deleteStellarSystem(id);
			LOGGER.info("Delete du système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{sysId}/star/{starId}")
	public ResponseEntity<StellarSystemResponse> associateStar(@PathVariable int sysId, @PathVariable int starId) throws EndPointException {
		LOGGER.debug("PATCH  /api/StellarSystem/{id}/star/{id}");
		Optional<StellarSystem> optSystem = this.stellarSystemService.getStellarSystem(sysId);
		if (optSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, sysId);
		}
		Optional<Star> optStar = this.starService.getStar(starId);
		if (optStar.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STAR, null, starId);
		}
		StellarSystem system = optSystem.get();
		system.setStar(optStar.get());
		try {
			system = this.stellarSystemService.updateStellarSystem(system);
			LOGGER.info("Association d'une Star a un système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system));
	}

	@DeleteMapping("/{id}/star")
	public ResponseEntity<StellarSystemResponse> dissociateStar(@PathVariable int id) throws EndPointException {
		LOGGER.debug("DELETE  /api/StellarSystem/{id}/star");
		Optional<StellarSystem> optSystem = this.stellarSystemService.getStellarSystem(id);
		if (optSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, id);
		}
		StellarSystem system = optSystem.get();
		system.setStar(null);
		try {
			system = this.stellarSystemService.updateStellarSystem(system);
			LOGGER.info("Delete de l'association d'une star a un système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system));
	}

	@PutMapping("/{sysId}/planets/{planetId}")
	public ResponseEntity<StellarSystemResponse> associatePlanet(@PathVariable int sysId, @PathVariable int planetId) throws EndPointException {
		LOGGER.debug("PUT  /api/StellarSystem/{sysId}/planets/{planetsId}");
		Optional<StellarSystem> optSystem = this.stellarSystemService.getStellarSystem(sysId);
		if (optSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, sysId);
		}
		Optional<Planet> optPlanet = this.planetService.getPlanetById(planetId);
		if (optPlanet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, planetId);
		}
		StellarSystem system = optSystem.get();
		system.addPlanet(optPlanet.get());
		try {
			system = this.stellarSystemService.updateStellarSystem(system);
			LOGGER.info("Association d'une planete a un système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system));
	}

	@DeleteMapping("/{id}/planets/{planetId}")
	public ResponseEntity<StellarSystemResponse> dissociatePlanet(@PathVariable int id, @PathVariable int planetId) throws EndPointException {
		LOGGER.debug("DELETE  /api/StellarSystem/{sysId}/planets/{planetsId}");
		Optional<StellarSystem> optSystem = this.stellarSystemService.getStellarSystem(id);
		if (optSystem.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STELLAR_SYSTEM, null, id);
		}
		Optional<Planet> optPlanet = this.planetService.getPlanetById(planetId);
		if (optPlanet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, planetId);
		}
		StellarSystem system = optSystem.get();
		system.removePlanet(optPlanet.get());
		try {
			system = this.stellarSystemService.updateStellarSystem(system);
			LOGGER.info("Delete de l'association d'une planete a un système réussi");
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STELLAR_SYSTEM, e);
		}
		return ResponseEntity.ok(StellarSystemMapper.toResponse(system));
	}
}