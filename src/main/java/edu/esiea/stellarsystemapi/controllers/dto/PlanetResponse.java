package edu.esiea.stellarsystemapi.controllers.dto;

import java.util.List;

public class PlanetResponse {
	private int id;
	private String name;
	private float mass;
	private boolean telluric;
	private boolean habitable;
	private String atmosphereCompostion;
	private float orbitingRadius;
	private float revolutionTime;
	private List<Integer> moonsIds;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public boolean isTelluric() {
		return telluric;
	}
	public void setTelluric(boolean telluric) {
		this.telluric = telluric;
	}
	public boolean isHabitable() {
		return habitable;
	}
	public void setHabitable(boolean habitable) {
		this.habitable = habitable;
	}
	public String getAtmosphereCompostion() {
		return atmosphereCompostion;
	}
	public void setAtmosphereCompostion(String atmosphereCompostion) {
		this.atmosphereCompostion = atmosphereCompostion;
	}
	public float getOrbitingRadius() {
		return orbitingRadius;
	}
	public void setOrbitingRadius(float orbitingRadius) {
		this.orbitingRadius = orbitingRadius;
	}
	public float getRevolutionTime() {
		return revolutionTime;
	}
	public void setRevolutionTime(float revolutionTime) {
		this.revolutionTime = revolutionTime;
	}
	public List<Integer> getMoonsIds() {
		return moonsIds;
	}
	public void setMoonsIds(List<Integer> moonsIds) {
		this.moonsIds = moonsIds;
	}
	
}
