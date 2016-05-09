package com.excilys.database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.implementation.CompanyService;
import com.excilys.database.services.implementation.ComputerService;
import com.excilys.database.servlets.utils.NavbarFlaghandler;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class addComputerServlet
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Company> companies = CompanyService.getInstance().listCompanies();
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

        ComputerService.getInstance().insertComputer(ComputerWrapper.wrapToComputer(comp));
        // TODO Add insertion logging

        // Setting a success feedback navbar
        NavbarFlaghandler.setFlag(request, "success", "Computer added", "The computer \"" + comp.getName() + "\" has been successfully added.");
        request.getRequestDispatcher("/dashboard").forward(request, response);

    }

}
