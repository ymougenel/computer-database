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
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.services.CompanyService;
import com.excilys.database.services.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
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
        String id = request.getParameter("computerId");
        String name = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyID = request.getParameter("companyId");
        try {
            Computer comp = new Computer(name);
            Long computerId = Long.parseLong(id);
            comp.setId(computerId);
            if (companyID != null && !companyID.equals("0")) {
                Company company = new Company("TempName");
                company.setId(Long.parseLong(companyID));
                comp.setCompany(company);
            }

            if (introduced != null && !introduced.equals("")) {
                comp.setIntroduced(LocalDate.parse(introduced));
            }
            if (discontinued != null && !discontinued.equals("")) {
                comp.setDiscontinued(LocalDate.parse(discontinued));
            }
            System.out.println("Comp:" + comp.toString());
            ComputerService.getInstance().updateComputer(comp);

            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", "success");
            request.setAttribute("messageHeader", "Computer updated");
            request.setAttribute("messageBody",
                    "The computer \"" + name + "\" has been successfully updated.");
            request.getRequestDispatcher("/dashboard").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            e.printStackTrace();
        }

    }

}
