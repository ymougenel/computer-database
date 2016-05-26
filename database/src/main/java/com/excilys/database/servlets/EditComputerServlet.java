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
import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.servlets.utils.NavbarFlaghandler;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class EditComputer
 */
@Controller
@RequestMapping("/editComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    CompanyServiceInterface companyService;

    @Autowired
    ComputerServiceInterface computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
    }

    // @Override
    // public void init(ServletConfig config) throws ServletException {
    // super.init(config);
    // SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
    // config.getServletContext());
    // }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(final ModelMap pModel, @RequestParam Map<String, String> params) {

        String computerId = params.get("computerId");
        Long id = null;
        Computer comp = null;
        id = Long.parseLong(computerId);
        System.out.println(id);
        comp = computerService.findComputer(id);
        System.out.println(comp.toString());
        ComputerDTO compDTO = new ComputerDTO(comp);
        List<Company> companies = companyService.listCompanies();
        pModel.addAttribute("companies", companies);
        pModel.addAttribute("computer", compDTO);
        return "editComputer";
        // request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,
        // response);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(final ModelMap pModel, @RequestParam Map<String, String> params){
        ComputerDTO comp = ComputerWrapper.wrapWebRequest(params);
        List<String> errors = ComputerValidador.computerValidation(comp, true);
        if (!errors.isEmpty()) {
            pModel.addAttribute("postMessage", "true");
            pModel.addAttribute("errors", errors);
            pModel.addAttribute("computer", comp);
            return "editComputer";
        }

        computerService.updateComputer(ComputerWrapper.wrapToComputer(comp));

        NavbarFlaghandler.setFlag(pModel, "success", "Computer updated",
                "The computer \"" + comp.getName() + "\" has been successfully updated.");
        return "redirect:dashboard";
        //request.getRequestDispatcher("/dashboard").forward(request, response);

    }

}
