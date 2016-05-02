package com.excilys.database.validadors;

import java.time.LocalDate;

import com.excilys.database.entities.Computer;

public class ComputerValidador {

    public static void computerIdValidation(String id) throws IllegalArgumentException {
        boolean valid = id.matches("\\d+") && !id.equals("0");
        if (!valid) {
            throw new IllegalArgumentException("Id " + id + "not valid");
        }
    }

    public static void computerDateValidation(String date) throws IllegalArgumentException {
        boolean valid = (date != null)
                && (date.matches("^((?:19|20)\\d{2})-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$"));
        if (!valid) {
            throw new IllegalArgumentException("Date " + date + "not valid");
        }
    }

    public static void computerValidation(Computer comp) throws IllegalArgumentException {
        LocalDate minLimit = LocalDate.parse("1970-01-01");
        LocalDate maxLimit = LocalDate.parse("2037-12-31");

        Long id = comp.getId();
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("Id " + id + "not valid");
        }

        String name = comp.getName();
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name " + name + "not valid");
        }

        LocalDate introduced = comp.getIntroduced();
        if (introduced != null && (introduced.isBefore(minLimit) || introduced.isAfter(maxLimit))) {
            throw new IllegalArgumentException("Introduced date " + introduced + "not valid");
        }

        LocalDate discontinued = comp.getDiscontinued();
        if (discontinued != null
                && (discontinued.isBefore(minLimit) || discontinued.isAfter(maxLimit))) {
            throw new IllegalArgumentException("Discontinued date " + discontinued + "not valid");
        }

    }

    public static void main(String... strings) {
        // String s1 = "20";
        // String s2 = "-20";
        // LocalDate d1 = LocalDate.parse("2012-12-13");
        // System.out.println("s1:"+s1+"->"+computerIdValidation(s1));
        // System.out.println("s2:"+s2+"->"+computerIdValidation(s2));
        // System.out.println("d1 ->"+computerDateValidation("2012-13-13"));
    }
}
