package com.excilys.database.entities;

import java.time.LocalDate;

public class ComputerDTO implements Entity{
    private Long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;
    private Long companyId;

    public ComputerDTO() {
    }

    public ComputerDTO(Computer comp) {
        this.id = comp.getId();
        this.name = comp.getName();
        this.introduced = (comp.getIntroduced() == null ? "" : comp.getIntroduced().toString());
        this.discontinued = (comp.getDiscontinued() == null ? "" : comp.getDiscontinued().toString());
        this.companyName = (comp.getCompany() == null) ? "" : comp.getCompany().getName();
        this.companyId = (comp.getCompany() == null) ? null : comp.getCompany().getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyID) {
        this.companyId = companyID;
    }

    public static Computer wrapToComputer(ComputerDTO dtoComp) {
        Computer comp = new Computer(dtoComp.name);
        comp.setId(dtoComp.id);
        comp.setIntroduced(LocalDate.parse(dtoComp.introduced));
        comp.setDiscontinued(LocalDate.parse(dtoComp.discontinued));

        Company company = new Company(dtoComp.companyName);
        company.setId(dtoComp.companyId);
        comp.setCompany(company);
        return comp;
    }
}
