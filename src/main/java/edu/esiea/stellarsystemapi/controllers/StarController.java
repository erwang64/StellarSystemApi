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

import edu.esiea.stellarsystemapi.controllers.dto.StarRequest;
import edu.esiea.stellarsystemapi.controllers.dto.StarResponse;
import edu.esiea.stellarsystemapi.controllers.dto.error.EndPointException;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.controllers.dto.mappers.StarMapper;
import edu.esiea.stellarsystemapi.model.Star;
import edu.esiea.stellarsystemapi.services.StarService;

@RestController
@RequestMapping(path = "/api/star")
public class StarController {

	private final StarService starService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StarController.class);
	
	public StarController(StarService starService) {
		this.starService = starService;
	}

	@PostMapping
	public ResponseEntity<StarResponse> addStar(@Valid @RequestBody StarRequest starDTO) throws EndPointException {
		Star s = StarMapper.toEntity(starDTO);
		try {
			s = this.starService.createStar(s);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STAR, e);
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getId()).toUri();
		return ResponseEntity.created(uri).body(StarMapper.toResponse(s));
	}

	@GetMapping("/all")
	public ResponseEntity<List<StarResponse>> getAllStars() {
		return ResponseEntity.ok(StarMapper.toResponseList(this.starService.getStars()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<StarResponse> getStarById(@PathVariable int id) throws EndPointException {
		Optional<Star> star = this.starService.getStar(id);
		if (star.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STAR, null, id);
		}
		return ResponseEntity.ok(StarMapper.toResponse(star.get()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<StarResponse> replace(@PathVariable int id, @Valid @RequestBody StarRequest starDTO) throws EndPointException {
		Optional<Star> optStar = this.starService.getStar(id);
		if (optStar.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STAR, null, id);
		}
		Star star = StarMapper.toEntity(starDTO);
		star.setId(id);
		try {
			star = this.starService.updateStar(star);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STAR, e);
		}
		return ResponseEntity.ok(StarMapper.toResponse(star));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<StarResponse> update(@PathVariable int id, @Valid @RequestBody StarRequest starDTO) throws EndPointException {
		Optional<Star> optStar = this.starService.getStar(id);
		if (optStar.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STAR, null, id);
		}
		Star modStar = StarMapper.patchWithRequest(starDTO, optStar.get());
		try {
			modStar = this.starService.updateStar(modStar);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.STAR, e);
		}
		return ResponseEntity.ok(StarMapper.toResponse(modStar));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) throws EndPointException {
		Optional<Star> star = this.starService.getStar(id);
		if (star.isEmpty()) {
			throw new EndPointException(HttpStatus.NOT_FOUND, "Ressource non trouvée", ResourceType.STAR, null, id);
		}
		try {
			this.starService.deleteStar(id);
		} catch (Exception e) {
			throw new EndPointException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), ResourceType.STAR, e);
		}
		return ResponseEntity.noContent().build();
	}
}