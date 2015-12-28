package com.swayam.demo.json.polymorphic.noannotation;

public class Lizard implements Animal {

    private final String species;
    private final String commonName;
    private final int legs;

    public Lizard(String species, String commonName, int legs) {
	this.species = species;
	this.commonName = commonName;
	this.legs = legs;
    }

    public Lizard() {
	this(null, null, 0);
    }

    @Override
    public int getLegs() {
	return legs;
    }

    @Override
    public String getSpecies() {
	return species;
    }

    @Override
    public String getCommonName() {
	return commonName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((commonName == null) ? 0 : commonName.hashCode());
	result = prime * result + legs;
	result = prime * result + ((species == null) ? 0 : species.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Lizard other = (Lizard) obj;
	if (commonName == null) {
	    if (other.commonName != null)
		return false;
	} else if (!commonName.equals(other.commonName))
	    return false;
	if (legs != other.legs)
	    return false;
	if (species == null) {
	    if (other.species != null)
		return false;
	} else if (!species.equals(other.species))
	    return false;
	return true;
    }

}
