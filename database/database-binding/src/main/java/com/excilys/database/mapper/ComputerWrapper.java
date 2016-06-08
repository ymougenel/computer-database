package com.excilys.database.mapper;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

public class ComputerWrapper {

    // Create a page based on the web request
    public static ComputerDTO wrapWebRequest(HttpServletRequest request) {
        ComputerDTO comp = new ComputerDTO();
        comp.setId(request.getParameter("computerId"));
        comp.setName(request.getParameter("computerName"));
        comp.setIntroduced(request.getParameter("introduced"));
        comp.setDiscontinued(request.getParameter("discontinued"));
        comp.setCompany(request.getParameter("companyId"));

        return comp;
    }

    // Create a page based on the web request
    public static ComputerDTO wrapWebRequest(Map<String, String> params) {
        ComputerDTO comp = new ComputerDTO();
        comp.setId(params.get("computerId"));
        comp.setName(params.get("computerName"));
        comp.setIntroduced(params.get("introduced"));
        comp.setDiscontinued(params.get("discontinued"));
        comp.setCompany(params.get("companyId"));

        return comp;
    }


    public static Computer wrapToComputer(ComputerDTO dto) {
        String name = dto.getName();
        String id = dto.getId();
        String introduced = dto.getIntroduced();
        String discontinued = dto.getDiscontinued();
        String companyId = dto.getCompany();
        String companyName = dto.getCompanyName();

        Computer comp = new Computer.Builder(name).build();


        if (dto.getId() != null && !id.isEmpty()) {
            comp.setId(Long.parseLong(id));
        }

        if (!introduced.isEmpty()) {
            comp.setIntroduced(LocalDate.parse(introduced));
        }
        else {
            comp.setIntroduced(null);
        }

        if (!discontinued.isEmpty()) {
            comp.setDiscontinued(LocalDate.parse(discontinued));
        }
        else {
            comp.setDiscontinued(null);
        }

        if (!companyId.isEmpty() && !companyId.equals("0")) {
            Company company = new Company(companyName);
            company.setId(Long.parseLong(companyId));
            comp.setCompany(company);
        }
        else {
            comp.setCompany(null);
        }

        return comp;
    }

}
