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

import edu.esiea.stellarsystemapi.controllers.dto.PlanetRequest;
import edu.esiea.stellarsystemapi.controllers.dto.PlanetResponse;
import edu.esiea.stellarsystemapi.controllers.dto.error.EndPointException;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.controllers.dto.mappers.PlanetMapper;
import edu.esiea.stellarsystemapi.model.Planet;
import edu.esiea.stellarsystemapi.services.PlanetService;

@RestController
@RequestMapping(path = "/api/planet")
public class PlanetController {

	private final PlanetService planetService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanetController.class);

	public PlanetController(PlanetService planetService) {
		this.planetService = planetService;
	}

	@PostMapping
	public ResponseEntity<PlanetResponse> addPlanet(@Valid @RequestBody PlanetRequest planetDTO) throws EndPointException {
		Planet p = PlanetMapper.planetRequestToPlanet(planetDTO);
		try {
			p = this.planetService.createPlanet(p);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.PLANET, e);
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(uri).body(PlanetMapper.toResponse(p));
	}

	@GetMapping("/all")
	public ResponseEntity<List<PlanetResponse>> getAllPlanets() {
		return ResponseEntity.ok(PlanetMapper.toResponseList(this.planetService.getAllPlanets()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlanetResponse> getPlanetById(@PathVariable int id) throws EndPointException {
		Optional<Planet> planet = this.planetService.getPlanetById(id);
		if (planet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, id);
		}
		return ResponseEntity.ok(PlanetMapper.toResponse(planet.get()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PlanetResponse> replace(@PathVariable int id, @Valid @RequestBody PlanetRequest planetDTO) throws EndPointException {
		Optional<Planet> optPlanet = this.planetService.getPlanetById(id);
		if (optPlanet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, id);
		}
		Planet planet = PlanetMapper.planetRequestToPlanet(planetDTO);
		planet.setId(id);
		try {
			planet = this.planetService.updatePlanet(planet);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.PLANET, e);
		}
		return ResponseEntity.ok(PlanetMapper.toResponse(planet));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PlanetResponse> update(@PathVariable("id") int planetId, @Valid @RequestBody PlanetRequest planetDTO) throws EndPointException {
		Optional<Planet> optPlanet = this.planetService.getPlanetById(planetId);
		if (optPlanet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, planetId);
		}
		Planet modPlanet = PlanetMapper.patchPlanetWithRequest(planetDTO, optPlanet.get());
		try {
			modPlanet = this.planetService.updatePlanet(modPlanet);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.PLANET, e);
		}
		return ResponseEntity.ok(PlanetMapper.toResponse(modPlanet));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) throws EndPointException {
		Optional<Planet> planet = this.planetService.getPlanetById(id);
		if (planet.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.PLANET, null, id);
		}
		try {
			this.planetService.deletePlanet(id);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.PLANET, e);
		}
		return ResponseEntity.noContent().build();
	}
}