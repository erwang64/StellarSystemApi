package edu.esiea.stellarsystemapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "Star")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Star extends AbstractCelestialBody {

	@Enumerated(EnumType.STRING)
	@Column(length = 17, name = "starType", unique = true)
	private StarType starType;
	
	@Column(name = "temperature")
	private float temperature;
	
	
	public Star(final String name, final float mass, final StarType type, final float temperature) {
		super(name, mass);
		this.starType = type;
		this.temperature = temperature;
	}
	
	public Star() {
		super();
	}

	public StarType getType() {
		return starType;
	}

	public void setType(StarType type) {
		this.starType = type;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		final String ret = "Étoile : ".concat(this.name).concat("\n").concat(this.starType.name());
		return ret;
	}

}
