package com.excilys.database.mapper;

import javax.servlet.http.HttpServletRequest;

import com.excilys.database.entities.ComputerDTO;

public class ComputerWrapper {

    // Create a page based on the web request
    public static ComputerDTO wrapWebRequest(HttpServletRequest request) {
        ComputerDTO comp = new ComputerDTO();
        comp.setId(request.getParameter("computerId"));
        comp.setName(request.getParameter("computerName"));
        comp.setIntroduced(request.getParameter("introduced"));
        comp.setDiscontinued(request.getParameter("discontinued"));
        comp.setCompanyId(request.getParameter("companyId"));

        /*Computer comp = new Computer.Builder(nameInput).build();
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
        }*/

        return comp;
    }

}
