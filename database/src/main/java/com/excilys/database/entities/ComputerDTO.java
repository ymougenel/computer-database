package com.excilys.database.entities;

import java.time.LocalDate;

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

    public static Computer wrapToComputer(ComputerDTO dto) {
        Computer comp = new Computer.Builder(dto.name).build();

        if (dto.id != null && !dto.id.isEmpty()) {
            comp.setId(Long.parseLong(dto.id));
        }

        if (!dto.introduced.isEmpty()) {
            comp.setIntroduced(LocalDate.parse(dto.introduced));
        }

        if (!dto.discontinued.isEmpty()) {
            comp.setDiscontinued(LocalDate.parse(dto.discontinued));
        }

        if (!dto.companyId.isEmpty()) {
            Company company = new Company(dto.companyName);
            company.setId(Long.parseLong(dto.companyId));
            comp.setCompany(company);
        }

        return comp;
    }
}
