package edu.esiea.stellarsystemapi.controllers.dto;

import java.util.List;

public class StellarSystemResponse {
	
	private int id;
	private String name;
	private int posX;
	private int posY;
	private List<Integer> planetsIds;
	
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
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public List<Integer> getPlanetsIds() {
		return planetsIds;
	}
	public void setPlanetsIds(List<Integer> planetsIds) {
		this.planetsIds = planetsIds;
	}	
}
