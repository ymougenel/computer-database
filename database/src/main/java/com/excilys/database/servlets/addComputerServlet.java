package com.excilys.database.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.services.CompanyService;
import com.excilys.database.services.ComputerService;
import com.excilys.database.services.InvalidInsertionException;

/**
 * Servlet implementation class addComputerServlet
 */
@WebServlet("/addComputerServlet")
public class addComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public addComputerServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String demande = request.getServletPath();
        List<Company> companies = CompanyService.getInstance().listCompanies();
        request.setAttribute("companies", companies);
        request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyID = request.getParameter("companyId");
        try {
            Computer comp = new Computer(name);
            Company company = new Company("TempName");
            if (companyID != null) {
                company.setId(Long.parseLong(companyID));
            }
            comp.setCompany(company);
            if (!introduced.equals("")) {
                comp.setIntroduced(LocalDate.parse(introduced));
            }
            if (!discontinued.equals("")) {
                comp.setDiscontinued(LocalDate.parse(discontinued));
            }
            ComputerService.getInstance().insertComputer(comp);

            request.getRequestDispatcher("/dashboard").forward(request, response);
        } catch (InvalidInsertionException e) {
            request.getRequestDispatcher("/addComputer").forward(request, response);
            e.printStackTrace();
        }
        request.getRequestDispatcher("/dashboard").forward(request, response);
    }

}
