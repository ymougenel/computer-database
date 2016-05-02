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
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.services.CompanyService;
import com.excilys.database.services.ComputerService;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class EditComputer
 */
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
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
            comp = ComputerService.getInstance().findComputer(id);
            if (comp == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
        }
        ComputerDTO compDTO = new ComputerDTO(comp);
        List<Company> companies = CompanyService.getInstance().listCompanies();
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
        String idInput = request.getParameter("computerId");
        String nameInput = request.getParameter("computerName");
        String introducedInput = request.getParameter("introduced");
        String discontinuedInput = request.getParameter("discontinued");
        String companyIDInput = request.getParameter("companyId");

        // Computer update based on the input parameters (exception if invalid params)
        try {
            ComputerValidador.computerIdValidation(idInput);
            Long computerId = Long.parseLong(idInput);
            Computer comp = new Computer.Builder(nameInput).id(computerId).build();

            if (companyIDInput != null && !companyIDInput.equals("0")) {
                Company company = new Company("DefaultName");
                ComputerValidador.computerIdValidation(companyIDInput);
                company.setId(Long.parseLong(companyIDInput));
                comp.setCompany(company);
            }

            if (introducedInput != null && !introducedInput.equals("")) {
                ComputerValidador.computerDateValidation(introducedInput);
                comp.setIntroduced(LocalDate.parse(introducedInput));
            }

            if (discontinuedInput != null && !discontinuedInput.equals("")) {
                ComputerValidador.computerDateValidation(discontinuedInput);
                comp.setDiscontinued(LocalDate.parse(discontinuedInput));
            }

            System.out.println("Comp:" + comp.toString()); //TODO edit logging
            ComputerService.getInstance().updateComputer(comp);

            // Setting a success feedback navbar
            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", "success");
            request.setAttribute("messageHeader", "Computer updated");
            request.setAttribute("messageBody",
                    "The computer \"" + nameInput + "\" has been successfully updated.");
            request.getRequestDispatcher("/dashboard").forward(request, response);

        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            e.printStackTrace();
        }

    }

}
