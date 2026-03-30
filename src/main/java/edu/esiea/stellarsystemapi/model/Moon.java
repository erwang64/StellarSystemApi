package edu.esiea.stellarsystemapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Moon")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Moon extends AbstractCelestialBody {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "planetId")
	private Planet planet;
	
	@Column(name = "orbitingRadius")
	private float orbitingRadius;
	
	@Column(name = "revolutionTime")
	private float revolutionTime;

	public Moon(final String name, final float mass, final Planet centralBody, final float orbitingRadius,
			final float revolutionTime) {
		super(name, mass);
		this.planet = centralBody;
		this.orbitingRadius = orbitingRadius;
		this.revolutionTime = revolutionTime;
	}

	public Planet getPlanet() {
		return planet;
	}



	public void setPlanet(Planet newPlanet) {
		if(this.planet != null) {
			this.planet.removeMoon(this, false);
		}
		if(newPlanet != null) {
			newPlanet.addMoon(this, false);
		}
		this.planet = newPlanet;
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



	@Override
	public String toString() {
		return "Lune : ".concat(this.name);

	}
}
