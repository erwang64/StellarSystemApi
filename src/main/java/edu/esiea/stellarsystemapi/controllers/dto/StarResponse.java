package edu.esiea.stellarsystemapi.controllers.dto;

import edu.esiea.stellarsystemapi.model.StarType;

public class StarResponse {
	private int id;
	private String name;
	private float mass;
	private StarType starType;
	private float temperature;

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
	public StarType getStarType() {
		return starType;
	}
	public void setStarType(StarType starType) {
		this.starType = starType;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
}