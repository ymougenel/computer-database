package com.excilys.database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.services.ComputerService;

/**
 * Servlet implementation class MyServlet
 */
// @WebServlet("/MyServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int pageSize;
    private int pageIndex;
    private int beginIndex;
    private int endIndex;
    /**
     * Default constructor.
     */
    public DashboardServlet() {
        super();
        pageIndex = 1;
        pageSize = 10;
        beginIndex =1;
        endIndex =7;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long count = ComputerService.getInstance().countComputers();
        processParameters(request);

        System.out.println("index: " + this.pageIndex + "\t begin : "
                + (1 + (this.pageIndex - 1) * this.pageSize) + "\n size:" + pageSize);

        Page<Computer> page = ComputerService.getInstance()
                .listComputers(1 + (this.pageIndex - 1) * this.pageSize, this.pageSize);
        //TODO calculate the begin-end pagination
        page.setMaxSize(this.pageSize);
        page.setIndex(this.pageIndex);
        request.setAttribute("count", count);
        request.setAttribute("page", page);
        request.setAttribute("beginIndex",this.beginIndex);
        request.setAttribute("endIndex",this.endIndex);
        request.setAttribute("notBeginIndex", this.pageIndex != 1);
        request.setAttribute("notEndIndex", (this.pageIndex-1) * page.getMaxSize() < count);
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void processParameters(HttpServletRequest request) {
        String requestSize = request.getParameter("pageSize");
        if (requestSize != null) {
            if (requestSize.equals("10") || requestSize.equals("50") || requestSize.equals("100")) {
                this.pageSize = Integer.parseInt(requestSize);
                this.pageIndex = 1;
            }
        }

        String pageIndex = request.getParameter("pageIndex");
        if (pageIndex != null) {
            if (pageIndex.equals("Next")) {
                this.pageIndex++;
            } else if (pageIndex.equals("Previous")) {
                this.pageIndex--;
            } else {
                this.pageIndex = Integer.parseInt(pageIndex);
            }
        }
    }

}
