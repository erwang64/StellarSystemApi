package edu.esiea.stellarsystemapi.controllers.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.stellarsystemapi.controllers.dto.StellarSystemRequest;
import edu.esiea.stellarsystemapi.controllers.dto.StellarSystemResponse;
import edu.esiea.stellarsystemapi.model.Planet;
import edu.esiea.stellarsystemapi.model.StellarSystem;

public class StellarSystemMapper {

	public static StellarSystem toEntity(StellarSystemRequest dto) {
		StellarSystem s = new StellarSystem();
		if (dto.getName() != null)    { s.setName(dto.getName()); }
		if (dto.getPosX() != null)    { s.setPosX(dto.getPosX()); }
		if (dto.getPosY() != null)    { s.setPosY(dto.getPosY()); }
		if (dto.getPlanets() != null) { s.setPlanets(dto.getPlanets()); }
		return s;
	}

	public static StellarSystem patchWithRequest(StellarSystemRequest dto, StellarSystem origin) {
		if (dto.getName() != null)    { origin.setName(dto.getName()); }
		if (dto.getPosX() != null)    { origin.setPosX(dto.getPosX()); }
		if (dto.getPosY() != null)    { origin.setPosY(dto.getPosY()); }
		if (dto.getPlanets() != null) { origin.setPlanets(dto.getPlanets()); }
		return origin;
	}

	public static StellarSystemResponse toResponse(StellarSystem s) {
		StellarSystemResponse ret = new StellarSystemResponse();
		ret.setId(s.getId());
		ret.setName(s.getName());
		ret.setPosX(s.getPosX());
		ret.setPosY(s.getPosY());
		List<Integer> planetsIds = new ArrayList<>();
		if (s.getPlanets() != null && !s.getPlanets().isEmpty()) {
			for (Planet planet : s.getPlanets()) {
				planetsIds.add(planet.getId());
			}
		}
		ret.setPlanetsIds(planetsIds);
		return ret;
	}

	public static List<StellarSystemResponse> toResponseList(Iterable<StellarSystem> systems) {
		List<StellarSystemResponse> result = new ArrayList<>();
		for (StellarSystem s : systems) {
			result.add(toResponse(s));
		}
		return result;
	}
}