package edu.esiea.stellarsystemapi.controllers.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import edu.esiea.stellarsystemapi.model.Planet;

public class StellarSystemRequest extends AbstractRequest{

	@NotBlank
	@Size(min = 2)
	private String name;

	@NotNull
	private Integer posX;

	@NotNull
	private Integer posY;

	private List<Planet> planets;
	
	public StellarSystemRequest() {
		super(ResourceType.STELLAR_SYSTEM);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPosX() {
		return posX;
	}
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	public Integer getPosY() {
		return posY;
	}
	public void setPosY(Integer posY) {
		this.posY = posY;
	}
	public List<Planet> getPlanets() {
		return planets;
	}
	public void setPlanets(List<Planet> planets) {
		this.planets = planets;
	}
}