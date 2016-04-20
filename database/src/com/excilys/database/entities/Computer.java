package com.excilys.database.entities;

import java.time.LocalDate;

public class Computer extends Entity{
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long company_id;
	
	
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
	
	public Long getCompany_id() {
		return company_id;
	}
	
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}

	@Override
	public String toString() {
		String split = "\t\t";
		String splitName = (name.length()>16 ? "\t" : (name.length()<10 ? "\t\t\t" : "\t\t"));
		return 	"id: " + this.id + split+ "name: " + name + splitName + "introduced: " + ((introduced!=null) ? introduced.toString() : "NULL") + 
				split+ "discontinued: " + ((discontinued!=null) ? discontinued.toString() : "NULL") + split+ "company_id: "+(company_id !=null ? company_id : "null");
				
	}
	
}
