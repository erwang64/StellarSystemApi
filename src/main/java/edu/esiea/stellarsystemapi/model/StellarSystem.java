package edu.esiea.stellarsystemapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "StellarSystem")
public class StellarSystem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "posX", nullable = false)
	private int posX;
	
	@Column(name = "posY", nullable = false)
	private int posY;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Star star;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "systemId", referencedColumnName = "id")
	private List<Planet> planets;

	public StellarSystem(final String name, final int posX, final int posY) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.planets = new ArrayList<Planet>();
	}
	
	public StellarSystem() {
	    this.planets = new ArrayList<Planet>();
	}

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

	public Star getStar() {
		return star;
	}

	public void setStar(Star star) {
		this.star = star;
	}

	public List<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(List<Planet> planets) {
		this.planets = planets;
	}
	
	public void addPlanet(Planet planet) {
	    this.planets.add(planet);
	}

	public void removePlanet(Planet planet) {
	    this.planets.remove(planet);
	}

	@Override
	public String toString() {
		String ret = "Système : ".concat(this.name).concat("\n").concat(this.star.toString()).concat("\n");
		for (final Planet planet : this.planets) {
			ret = ret.concat("\t").concat(planet.toString()).concat("\n");
		}
		return ret;
	}
}
