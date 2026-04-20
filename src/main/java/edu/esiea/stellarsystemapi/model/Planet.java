package edu.esiea.stellarsystemapi.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Planet")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Planet extends AbstractCelestialBody {

	@Column(name = "isTelluric")
	private boolean telluric;
	
	@Column(name = "isHabitable")
	private boolean habitable;
	
	@Column(name = "atmosphereComposition", length = 500)
	private String atmosphereComposition;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "planetId", referencedColumnName = "id")
	private List<Moon> moons;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "StarId")
	protected Star star;
	
	@Column(name = "orbitingRadius")
	protected float orbitingRadius;
	
	@Column(name = "revolutionTime")
	protected float revolutionTime;

	public Planet(final String name, final float mass, final float orbitingRadius,
			final float revolutionTime, final boolean habitable, final boolean telluric,
			final String atmosphereComposition) {
		super(name, mass);
		this.habitable = habitable;
		this.telluric = telluric;
		this.atmosphereComposition = atmosphereComposition;
	}
	
	//final Star centralBody
	
	public Planet() {
	    super();
	}
	
	public List<Moon> getMoons() {
		return moons;
	}

	public void setMoons(List<Moon> moons) {
		this.moons = moons;
	}

	public Star getStar() {
		return star;
	}

	public void setStar(Star star) {
		this.star = star;
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

	public boolean isHabitable() {
		return habitable;
	}

	public void setHabitable(boolean habitable) {
		this.habitable = habitable;
	}

	public boolean isTelluric() {
		return telluric;
	}

	public void setTelluric(boolean telluric) {
		this.telluric = telluric;
	}

	public String getAtmosphereComposition() {
		return atmosphereComposition;
	}

	public void setAtmosphereComposition(String atmosphereComposition) {
		this.atmosphereComposition = atmosphereComposition;
	}

	@Override
	public String toString() {
		String ret = "Planète : ".concat(this.name);
		if (this.telluric) {
			ret = ret.concat(" Tellurique");
		}
		if (this.habitable) {
			ret = ret.concat(" Habitable");
		}
		return ret;
	}
	
	/**
	 * Adds a moon that orbits this planet
	 * @param moon the moon to add
	 * @param setMoonPlanet this boolean is used to avoid never ending cycle when adding a moon
	 * <ul><li>if set to <code>true</code> this method will call {@link Moon#setPlanet(this)}</li>
	 * <li>if set to <code>false</code> this method will NOT call {@link Moon#setPlanet(this)}</li></ul>
	 */
	public void addMoon(Moon moon, boolean setMoonPlanet) {
		this.moons.add(moon);
		if(setMoonPlanet) {
			moon.setPlanet(this);
		}
	}
	
	/**
	 * Remove a moon that orbits this planet
	 * @param moon the moon to remove
	 * @param setMoonPlanet this boolean is used to avoid never ending cycle when removing a moon
	 * <ul><li>if set to <code>true</code> this method will call {@link Moon#setPlanet(this)}</li>
	 * <li>if set to <code>false</code> this method will NOT call {@link Moon#setPlanet(this)}</li></ul>
	 */
	public void removeMoon(Moon moon, boolean setMoonPlanet) {
		this.moons.remove(moon);
		if(setMoonPlanet) {
			moon.setPlanet(null);
		}
	}

}
