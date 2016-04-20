package com.excilys.database.entities;

public class Company extends Entity{
	
	@Override
	public String toString() {
		String split = "\t\t";
		return 	"id: " + this.id + split+ "name: " + name;
	}

}
