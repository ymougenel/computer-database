package com.excilys.database.entities;

public class CompanyDTO implements Entity{
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public static Company wrapToCompany(CompanyDTO dtoCompany) {
        Company comp = new Company(dtoCompany.name);
        comp.setId(dtoCompany.id);
        return comp;
    }
}
