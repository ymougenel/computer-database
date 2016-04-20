package com.excilys.database.entities;

public abstract class Entity {
	protected Long id;
	protected String name;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String toString();
}
