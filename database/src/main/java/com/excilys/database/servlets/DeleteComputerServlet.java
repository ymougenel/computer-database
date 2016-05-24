package com.excilys.database.servlets;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.validadors.ComputerValidador;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerDaoInterface computerDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServlet() {
        super();
        //computerDAO = ComputerDAO.getInstance();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(final ModelMap pModel, @RequestParam Map<String, String> params) {
        final String selection = params.get("selection");
        String[] ids =selection.split(",");
        Computer comp;
        for (String idInput : ids ) {
            ComputerValidador.idValidation(idInput);
            Long id = Long.parseLong(idInput);
            comp = new Computer.Builder("DefaultName").id(id).build();
            computerDAO.delete(comp);
        }
        return "dashboard";
        //request.getRequestDispatcher("/dashboard").forward(request, response);
    }

}
