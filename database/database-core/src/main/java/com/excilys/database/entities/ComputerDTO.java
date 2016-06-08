package com.excilys.database.entities;

public class ComputerDTO {
    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;
    private String company;

    public ComputerDTO() {
    }

    public ComputerDTO(Computer comp) {
        this.id = comp.getId().toString();
        this.name = comp.getName();
        this.introduced = (comp.getIntroduced() == null ? "" : comp.getIntroduced().toString());
        this.discontinued = (comp.getDiscontinued() == null
                ? ""
                        : comp.getDiscontinued().toString());
        this.companyName = (comp.getCompany() == null) ? "" : comp.getCompany().getName();
        this.company = (comp.getCompany() == null) ? null : comp.getCompany().getId().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String companyID) {
        this.company = companyID;
    }

    @Override
    public String toString() {
        String split = "\t\t";
        String splitName = "\t\t";
        if (name != null) {
            splitName = (name.length() > 17 ? "\t" : (name.length() < 10 ? "\t\t\t" : "\t\t"));
        }

        return "id: " + this.id + "\t" + "name: " + name + splitName
                + "introduced: " + introduced + split
                + "discontinued: " + discontinued
                + split + "company_id: "
                + company;

    }

}
