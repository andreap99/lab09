package it.polito.tdp.borders.model;

public class Border {
	

	private int id;
	private Country s1;
	private Country s2;
	
	public Border(int id, Country s1, Country s2) {
		super();
		this.id = id;
		this.s1 = s1;
		this.s2 = s2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Country getS1() {
		return s1;
	}

	public void setS1(Country s1) {
		this.s1 = s1;
	}

	public Country getS2() {
		return s2;
	}

	public void setS2(Country s2) {
		this.s2 = s2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Border other = (Border) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "cod:"+id + "  " + s1 + "  " + s2;
	}

}
