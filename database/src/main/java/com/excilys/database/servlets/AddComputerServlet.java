package com.excilys.database.servlets;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.servlets.utils.NavbarFlaghandler;
import com.excilys.database.validators.ComputerValidator;

/**
 * Servlet implementation class addComputerServlet
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerServiceInterface computerService;

    @Autowired
    private CompanyServiceInterface companyService;

    public AddComputerServlet() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(final ModelMap pModel) {
        List<Company> companies = companyService.listCompanies();
        pModel.addAttribute("companies", companies);
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(final ModelMap pModel, @RequestParam Map<String, String> params) {

        ComputerDTO comp = ComputerWrapper.wrapWebRequest(params);
        //ComputerValidador.

        List<String> errors = ComputerValidator.computerValidation(comp, false);
        if (!errors.isEmpty()) {
            System.err.println("Error arguments for add computer:" + errors.toString());;
            pModel.addAttribute("postMessage", "true");
            pModel.addAttribute("errors", errors);
            pModel.addAttribute("computer", comp);
            return "addComputer";
        }

        computerService.insertComputer(ComputerWrapper.wrapToComputer(comp));

        // Setting a success feedback navbar
        NavbarFlaghandler.setFlag(pModel, "success", "Computer added",
                "The computer \"" + comp.getName() + "\" has been successfully added.");
        return "redirect:dashboard";

    }

}
