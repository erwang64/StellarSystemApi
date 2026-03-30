package edu.esiea.stellarsystemapi.controllers.dto;

import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlanetRequest extends AbstractRequest{
	
	@NotBlank
	@Size(min = 2)
	private String name;
	@NotNull
	private Float mass;
	@NotNull
	private Boolean telluric;
	@NotNull
	private Boolean habitable;
	private String atmosphereComposition;
	@NotNull
	private Float orbitingRadius;
	@NotNull
	private Float revolutionTime;
	
	public PlanetRequest() {
		super (ResourceType.PLANET);
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
	public Boolean getTelluric() {
		return telluric;
	}
	public void setTelluric(Boolean telluric) {
		this.telluric = telluric;
	}
	public Boolean getHabitable() {
		return habitable;
	}
	public void setHabitable(Boolean habitable) {
		this.habitable = habitable;
	}
	public String getAtmosphereComposition() {
		return atmosphereComposition;
	}
	public void setAtmosphereComposition(String atmosphereComposition) {
		this.atmosphereComposition = atmosphereComposition;
	}
	public Float getOrbitingRadius() {
		return orbitingRadius;
	}
	public void setOrbitingRadius(Float orbitingRadius) {
		this.orbitingRadius = orbitingRadius;
	}
	public Float getRevolutionTime() {
		return revolutionTime;
	}
	public void setRevolutionTime(Float revolutionTime) {
		this.revolutionTime = revolutionTime;
	}
	
	
}
