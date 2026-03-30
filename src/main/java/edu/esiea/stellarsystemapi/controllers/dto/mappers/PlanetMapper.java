package edu.esiea.stellarsystemapi.controllers.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.stellarsystemapi.controllers.dto.PlanetRequest;
import edu.esiea.stellarsystemapi.controllers.dto.PlanetResponse;
import edu.esiea.stellarsystemapi.model.Moon;
import edu.esiea.stellarsystemapi.model.Planet;

public class PlanetMapper {

	public static Planet patchPlanetWithRequest(PlanetRequest dto, Planet origin) {
		if (dto.getName() != null){ origin.setName(dto.getName()); }
		if (dto.getMass() != null){ origin.setMass(dto.getMass()); }
		if (dto.getHabitable() != null){ origin.setHabitable(dto.getHabitable()); }
		if (dto.getTelluric() != null){ origin.setTelluric(dto.getTelluric()); }
		if (dto.getOrbitingRadius() != null){ origin.setOrbitingRadius(dto.getOrbitingRadius()); }
		if (dto.getRevolutionTime() != null){ origin.setRevolutionTime(dto.getRevolutionTime()); }
		if (dto.getAtmosphereComposition() != null){ origin.setAtmosphereComposition(dto.getAtmosphereComposition()); }
		return origin;
	}

	public static Planet planetRequestToPlanet(PlanetRequest dto) {
		Planet planet = new Planet();
		planet.setName(dto.getName());
		planet.setMass(dto.getMass());
		planet.setHabitable(dto.getHabitable());
		planet.setTelluric(dto.getTelluric());
		planet.setOrbitingRadius(dto.getOrbitingRadius());
		planet.setRevolutionTime(dto.getRevolutionTime());
		planet.setAtmosphereComposition(dto.getAtmosphereComposition());
		return planet;
	}

	public static PlanetResponse toResponse(Planet p) {
		PlanetResponse ret = new PlanetResponse();
		ret.setId(p.getId());
		ret.setName(p.getName());
		ret.setAtmosphereCompostion(p.getAtmosphereComposition());
		ret.setMass(p.getMass());
		ret.setOrbitingRadius(p.getOrbitingRadius());
		ret.setRevolutionTime(p.getRevolutionTime());
		ret.setTelluric(p.isTelluric());
		ret.setHabitable(p.isHabitable());
		List<Integer> moonsIds = new ArrayList<>();
		if (p.getMoons() != null && !p.getMoons().isEmpty()) {
			for (Moon moon : p.getMoons()) {
				moonsIds.add(moon.getId());
			}
		}
		ret.setMoonsIds(moonsIds);
		return ret;
	}
	
	public static List<PlanetResponse> toResponseList(Iterable<Planet> planets) {
		List<PlanetResponse> result = new ArrayList<>();
		for (Planet planet : planets) {
			result.add(toResponse(planet));
		}
		return result;
	}
}