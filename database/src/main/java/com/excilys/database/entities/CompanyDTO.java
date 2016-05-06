package com.excilys.database.entities;

public class CompanyDTO {
    private String id;
    private String name;

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

    public static Company wrapToCompany(CompanyDTO dtoCompany) {
        Company comp = new Company(dtoCompany.name);
        comp.setId(Long.parseLong(dtoCompany.id));
        return comp;
    }
}
