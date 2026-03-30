package edu.esiea.stellarsystemapi.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.model.StarType;

public class StarRequest extends AbstractRequest{

	@NotBlank
	@Size(min = 2)
	private String name;

	@NotNull
	private Float mass;

	@NotNull
	private StarType starType;

	@NotNull
	private Float temperature;
	
	public StarRequest() {
		super(ResourceType.STAR);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getMass() {
		return mass;
	}
	public void setMass(Float mass) {
		this.mass = mass;
	}
	public StarType getStarType() {
		return starType;
	}
	public void setStarType(StarType starType) {
		this.starType = starType;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
}