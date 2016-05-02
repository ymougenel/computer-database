package com.excilys.database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDAO;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class DeleteComputerServlet
 */
public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComputerDAO computerDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServlet() {
        super();
        computerDAO = ComputerDAO.getInstance();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String selection = request.getParameter("selection");
        System.out.println(selection);
        String[] ids =selection.split(",");
        Computer comp;
        for (String idInput : ids ) {
            ComputerValidador.computerIdValidation(idInput);
            Long id = Long.parseLong(idInput);
            comp = new Computer.Builder("DefaultName").id(id).build();
            computerDAO.delete(comp);
        }

        request.getRequestDispatcher("/dashboard").forward(request, response);
    }

}
