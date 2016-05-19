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
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.servlets.utils.NavbarFlaghandler;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class addComputerServlet
 */
@Configurable
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerServiceInterface computerService;

    @Autowired
    private CompanyServiceInterface companyService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
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
        List<Company> companies = companyService.listCompanies();
        request.setAttribute("companies", companies);
        request.getRequestDispatcher("WEB-INF/views/addComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ComputerDTO comp = ComputerWrapper.wrapWebRequest(request);
        //TODO validation dto

        List<String> errors =ComputerValidador.computerValidation(comp, false);
        if (!errors.isEmpty()) {
            request.setAttribute("postMessage", "true");
            request.setAttribute("errors", errors);
            request.setAttribute("computer", comp);
            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
            return;
        }

        computerService.insertComputer(ComputerWrapper.wrapToComputer(comp));
        // TODO Add insertion logging

        // Setting a success feedback navbar
        NavbarFlaghandler.setFlag(request, "success", "Computer added", "The computer \"" + comp.getName() + "\" has been successfully added.");
        request.getRequestDispatcher("/dashboard").forward(request, response);

    }

}
