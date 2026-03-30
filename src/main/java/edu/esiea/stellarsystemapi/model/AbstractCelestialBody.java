package edu.esiea.stellarsystemapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCelestialBody {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	protected int id;
	
	@Column(name = "name", nullable = false)
	protected String name;
	
	@Column(name = "mass", nullable = false)
	protected float mass;

	public AbstractCelestialBody(final String name, final float mass) {
		this.name = name;
		this.mass = mass;
	}
	
	public AbstractCelestialBody() {}

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
}
