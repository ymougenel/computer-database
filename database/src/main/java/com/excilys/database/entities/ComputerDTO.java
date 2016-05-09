package com.excilys.database.entities;

public class ComputerDTO {
    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;
    private String companyId;

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
        this.companyId = (comp.getCompany() == null) ? null : comp.getCompany().getId().toString();
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyID) {
        this.companyId = companyID;
    }

}
