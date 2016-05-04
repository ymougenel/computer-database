package com.excilys.database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.mapper.ComputerWrapper;
import com.excilys.database.services.CompanyService;
import com.excilys.database.services.ComputerService;

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

        Computer comp = ComputerWrapper.wrapWebRequest(request, false);
        ComputerService.getInstance().insertComputer(comp);
        // TODO Add insertion logging

        // Setting a success feedback navbar
        request.setAttribute("postMessage", "true");
        request.setAttribute("messageLevel", "success");
        request.setAttribute("messageHeader", "Computer added");
        request.setAttribute("messageBody",
                "The computer \"" + comp.getName() + "\" has been successfully added.");
        request.getRequestDispatcher("/dashboard").forward(request, response);

    }

}
