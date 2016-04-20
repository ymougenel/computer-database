package com.excilys.database.entities;

import java.time.LocalDate;

public class Computer extends Entity{
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	
	public LocalDate getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	
	public Company getCompany_id() {
		return company;
	}
	
	public void setCompany_id(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String split = "\t\t";
		String splitName = (name.length()>16 ? "\t" : (name.length()<10 ? "\t\t\t" : "\t\t"));
		return 	"id: " + this.id + split+ "name: " + name + splitName + "introduced: " + ((introduced!=null) ? introduced.toString() : "NULL") + 
				split+ "discontinued: " + ((discontinued!=null) ? discontinued.toString() : "NULL") + split+ "company_id: "+(company !=null ? company.getName() : "null");
				
	}
	
}
