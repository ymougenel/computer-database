package com.excilys.database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
@Configurable
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String computerId = request.getParameter("computerId");
        Long id = null;
        Computer comp = null;
        try {
            id = Long.parseLong(computerId);
            comp = computerService.findComputer(id);
            if (comp == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
        }
        ComputerDTO compDTO = new ComputerDTO(comp);
        List<Company> companies = companyService.listCompanies();
        request.setAttribute("companies", companies);
        request.setAttribute("computer", compDTO);
        request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerDTO comp = ComputerWrapper.wrapWebRequest(request);
        List<String> errors =ComputerValidador.computerValidation(comp, true);
        if (!errors.isEmpty()) {
            request.setAttribute("postMessage", "true");
            request.setAttribute("errors", errors);
            request.setAttribute("computer", comp);
            request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
            return;
        }

        computerService.updateComputer(ComputerWrapper.wrapToComputer(comp));
        NavbarFlaghandler.setFlag(request, "success", "Computer updated", "The computer \"" + comp.getName() + "\" has been successfully updated.");
        request.getRequestDispatcher("/dashboard").forward(request, response);

    }

}
