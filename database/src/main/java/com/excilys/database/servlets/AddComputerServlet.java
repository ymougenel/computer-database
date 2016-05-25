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
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class addComputerServlet
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerServiceInterface computerService;

    @Autowired
    private CompanyServiceInterface companyService;

    public AddComputerServlet() {
        super();
    }

    //    @Override
    //    public void init(ServletConfig config) throws ServletException {
    //        super.init(config);
    //        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    //    }


    @RequestMapping(method = RequestMethod.GET)
    public String doGet(final ModelMap pModel) {
        List<Company> companies = companyService.listCompanies();
        //pModel.addAllAttributes(companies);
        pModel.addAttribute("companies", companies);
        System.out.println("------ Hello world from Add computer get ("+companies.size()+")");
        return "addComputer";
        //request.setAttribute("companies", companies);
        //request.getRequestDispatcher("WEB-INF/views/addComputer.jsp").forward(request, response);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(final ModelMap pModel, @RequestParam Map<String, String> params) {
        System.out.println("***** Hello world from Add computer post ()");
        String name = params.get("computerName");
        System.out.println("--->"+name);

        ComputerDTO comp = ComputerWrapper.wrapWebRequest(params);
        //TODO validation dto

        List<String> errors =ComputerValidador.computerValidation(comp, false);
        if (!errors.isEmpty()) {
            System.err.println("Error arguments for add computer:"+errors.toString());;
            //request.setAttribute("postMessage", "true");
            //request.setAttribute("errors", errors);
            //request.setAttribute("computer", comp);
            //request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
            pModel.addAttribute("postMessage", "true");
            pModel.addAttribute("errors", errors);
            pModel.addAttribute("computer", comp);
            return "addComputer";
        }

        computerService.insertComputer(ComputerWrapper.wrapToComputer(comp));
        // TODO Add insertion logging

        // Setting a success feedback navbar
        NavbarFlaghandler.setFlag(pModel, "success", "Computer added", "The computer \"" + comp.getName() + "\" has been successfully added.");
        //request.getRequestDispatcher("/dashboard").forward(request, response);*/
        return "dashboard";

    }

}
