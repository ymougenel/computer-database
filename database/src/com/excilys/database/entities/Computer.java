package com.excilys.database.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Computer extends Entity{
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long company_id;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
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
		return 	"id: " + this.id + "\t name: " + name + "\t introduced: " + ((introduced!=null) ? introduced.toString() : "NULL") + "\t discontinued: " + 
				((discontinued!=null) ? discontinued.toString() : "NULL") + "\t company_id: "+(company_id !=null ? company_id : "null");
	}
	
}
