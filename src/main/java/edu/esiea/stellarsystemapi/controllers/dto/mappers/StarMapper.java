package edu.esiea.stellarsystemapi.controllers.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.stellarsystemapi.controllers.dto.StarRequest;
import edu.esiea.stellarsystemapi.controllers.dto.StarResponse;
import edu.esiea.stellarsystemapi.model.Star;

public class StarMapper {

	public static Star toEntity(StarRequest dto) {
		Star s = new Star();
		if (dto.getName() != null)        { s.setName(dto.getName()); }
		if (dto.getMass() != null)        { s.setMass(dto.getMass()); }
		if (dto.getStarType() != null)    { s.setType(dto.getStarType()); }
		if (dto.getTemperature() != null) { s.setTemperature(dto.getTemperature()); }
		return s;
	}

	public static Star patchWithRequest(StarRequest dto, Star origin) {
		if (dto.getName() != null)        { origin.setName(dto.getName()); }
		if (dto.getMass() != null)        { origin.setMass(dto.getMass()); }
		if (dto.getStarType() != null)    { origin.setType(dto.getStarType()); }
		if (dto.getTemperature() != null) { origin.setTemperature(dto.getTemperature()); }
		return origin;
	}

	public static StarResponse toResponse(Star s) {
		StarResponse ret = new StarResponse();
		ret.setId(s.getId());
		ret.setName(s.getName());
		ret.setMass(s.getMass());
		ret.setStarType(s.getType());
		ret.setTemperature(s.getTemperature());
		return ret;
	}

	public static List<StarResponse> toResponseList(Iterable<Star> stars) {
		List<StarResponse> result = new ArrayList<>();
		for (Star s : stars) {
			result.add(toResponse(s));
		}
		return result;
	}
}