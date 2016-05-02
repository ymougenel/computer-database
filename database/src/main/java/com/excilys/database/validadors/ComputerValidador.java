package com.excilys.database.validadors;

import java.time.LocalDate;

public class ComputerValidador {

    public static boolean computerIdValidation(String id) {
        return id.matches("\\d+") && !id.equals("0");
    }

    public static boolean computerDateValidation(String id) {
        return (id != null) && (id.matches("^((?:19|20)\\d{2})-(0?\\d|1[012])-(0?\\d|[12]\\d|3[01])$"));
    }

    public static void main(String...strings) {
        String s1 = "20";
        String s2 = "-20";
        LocalDate d1 = LocalDate.parse("2012-12-13");
        System.out.println("s1:"+s1+"->"+computerIdValidation(s1));
        System.out.println("s2:"+s2+"->"+computerIdValidation(s2));
        System.out.println("d1 ->"+computerDateValidation("2012-13-13"));
    }
}
