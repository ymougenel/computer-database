package com.excilys.database.validadors;

import com.excilys.database.entities.Company;

public class CompanyValidation {

    public static void companyValidation(Company comp) throws IllegalArgumentException {
        Long id = comp.getId();
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id " + id + " not valid");
        }

        String name = comp.getName();
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name " + name + "not valid");
        }
    }
}
