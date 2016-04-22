package com.excilys.database.entities;

import java.time.LocalDate;

public class Computer extends Entity {
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		return true;
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

	public Company getCompany_id() {
		return company;
	}

	public void setCompany_id(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String split = "\t\t";
		String splitName = (name.length() > 17 ? "\t" : (name.length() < 10 ? "\t\t\t" : "\t\t"));
		return "id: " + this.id + split + "name: " + name + splitName + "introduced: "
				+ ((introduced != null) ? introduced.toString() : "NULL") + split + "discontinued: "
				+ ((discontinued != null) ? discontinued.toString() : "NULL") + split + "company_id: "
				+ (company != null ? company.getName() : "null");

	}

}
