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
        String name = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyID = request.getParameter("companyId");
        try {
            Computer comp = new Computer.Builder(name).build();
            if (companyID != null && !companyID.equals("0")) {
                Company company = new Company("TempName");
                company.setId(Long.parseLong(companyID));
                comp.setCompany(company);
            }

            if (!introduced.equals("")) {
                comp.setIntroduced(LocalDate.parse(introduced));
            }
            if (!discontinued.equals("")) {
                comp.setDiscontinued(LocalDate.parse(discontinued));
            }
            ComputerService.getInstance().insertComputer(comp);

            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", "success");
            request.setAttribute("messageHeader", "Computer added");
            request.setAttribute("messageBody",
                    "The computer \"" + name + "\" has been successfully added.");
            request.getRequestDispatcher("/dashboard").forward(request, response);
        } catch (InvalidInsertionException e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            e.printStackTrace();
        }


    }

}
