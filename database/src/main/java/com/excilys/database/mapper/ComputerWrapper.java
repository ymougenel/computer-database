package com.excilys.database.mapper;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.validadors.ComputerValidador;

public class ComputerWrapper {

    public static Computer wrapWebRequest(HttpServletRequest request, boolean idValidationRequired) {
        String idInput = request.getParameter("computerId");
        String nameInput = request.getParameter("computerName");
        String introducedInput = request.getParameter("introduced");
        String discontinuedInput = request.getParameter("discontinued");
        String companyIDInput = request.getParameter("companyId");

        Computer comp = new Computer.Builder(nameInput).build();
        // Computer update based on the input parameters (exception if invalid params)
        if (idValidationRequired) {
            ComputerValidador.computerIdValidation(idInput);
            Long computerId = Long.parseLong(idInput);
            comp.setId(computerId);
        }




        if (companyIDInput != null && !companyIDInput.equals("0")) {
            Company company = new Company("DefaultName");
            ComputerValidador.computerIdValidation(companyIDInput);
            company.setId(Long.parseLong(companyIDInput));
            comp.setCompany(company);
        }

        if (introducedInput != null && !introducedInput.equals("")) {
            ComputerValidador.computerDateValidation(introducedInput);
            comp.setIntroduced(LocalDate.parse(introducedInput));
        }

        if (discontinuedInput != null && !discontinuedInput.equals("")) {
            ComputerValidador.computerDateValidation(discontinuedInput);
            comp.setDiscontinued(LocalDate.parse(discontinuedInput));
        }

        return comp;
    }
}
