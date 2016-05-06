package com.excilys.database.validadors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.database.entities.ComputerDTO;

public class ComputerValidador {
    public static LocalDate minLimit = LocalDate.parse("1970-01-01");
    public static LocalDate maxLimit = LocalDate.parse("2037-12-31");
    public static String DATE_REGEX = "^((?:19|20)\\d{2})-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$";

    public static void idValidation(String id) throws IllegalArgumentException {
        boolean valid = (id != null) && id.matches("\\d+") && !id.equals("0");
        if (!valid) {
            throw new IllegalArgumentException("Id " + id + "not valid");
        }
    }

    public static void nameValidation(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name null");
        }
    }

    public static void computerIdValidation(Long id) throws IllegalArgumentException {
        boolean valid = (id != null) && id > 0;
        if (!valid) {
            throw new IllegalArgumentException("Id " + id + "not valid");
        }
    }

    public static boolean computerDateValidation(String date) throws IllegalArgumentException {
        if (date.isEmpty()) {
            return true;
        }

        if (date.matches(DATE_REGEX)) {
            LocalDate currentDate = LocalDate.parse(date);
            return ((currentDate.isAfter(minLimit) && currentDate.isBefore(maxLimit)));
        }
        return false;
    }

    public List<String> computerValidation(ComputerDTO comp) throws IllegalArgumentException {
        List<String> errors = new ArrayList<String>();

        idValidation(comp.getId());

        String name = comp.getName();
        String introduced = comp.getIntroduced();
        String discontinued = comp.getDiscontinued();
        String company_id = comp.getCompanyId();

        // Check for illegal parameters
        if (name == null || introduced == null || discontinued == null || company_id == null) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (name.equals("")) {
            errors.add("Name must not be empty");
        }

        if (!computerDateValidation(introduced)) {
            errors.add("Introduced date invalid");
        }

        if (!computerDateValidation(discontinued)) {
            errors.add("Discontinued date invalid");
        }

        //TODO company validation
        return errors;
    }
}
