package com.excilys.database.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.services.CompanyService;
import com.excilys.database.services.ComputerService;
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
        String nameInput = request.getParameter("computerName");
        String introducedInput = request.getParameter("introduced");
        String discontinuedInput = request.getParameter("discontinued");
        String companyIDInput = request.getParameter("companyId");

        // Computer creation based on the input parameters (exception if invalid params)
        try {
            Computer comp = new Computer.Builder(nameInput).build();

            if (companyIDInput != null && !companyIDInput.equals("0")) {
                Company company = new Company("TempName");
                ComputerValidador.computerIdValidation(companyIDInput);
                company.setId(Long.parseLong(companyIDInput));
                comp.setCompany(company);
            }

            if (!introducedInput.equals("")) {
                ComputerValidador.computerDateValidation(introducedInput);
                comp.setIntroduced(LocalDate.parse(introducedInput));
            }

            if (!discontinuedInput.equals("")) {
                ComputerValidador.computerDateValidation(discontinuedInput);
                comp.setDiscontinued(LocalDate.parse(discontinuedInput));
            }

            ComputerService.getInstance().insertComputer(comp);
            // TODO Add insertion logging

            // Setting a success feedback navbar
            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", "success");
            request.setAttribute("messageHeader", "Computer added");
            request.setAttribute("messageBody",
                    "The computer \"" + nameInput + "\" has been successfully added.");
            request.getRequestDispatcher("/dashboard").forward(request, response);

        } catch (Exception e) {
            // TODO add error logging
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            e.printStackTrace();
        }

    }

}
