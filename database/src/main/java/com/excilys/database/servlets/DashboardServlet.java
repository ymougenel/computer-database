package com.excilys.database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.entities.Page;
import com.excilys.database.services.ComputerService;

/**
 * Servlet implementation class MyServlet
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private long pageSize;
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
    }

    /**
     * @throws IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long count = ComputerService.getInstance().countComputers();
        processParameters(request, response);

        Page<Computer> page = ComputerService.getInstance()
                .listComputers((this.pageIndex - 1) * this.pageSize, this.pageSize);

        Page<ComputerDTO> pageDTO = new Page<ComputerDTO>();
        for (Computer comp : page.getEntities()) {
            pageDTO.addEntity(new ComputerDTO(comp));
        }

        pageDTO.setMaxSize(this.pageSize);
        pageDTO.setIndex(this.pageIndex);

        setIndexBorders(pageDTO.getMaxSize(), count);
        request.setAttribute("count", count);
        request.setAttribute("page", pageDTO);
        request.setAttribute("beginIndex", this.beginIndex);
        request.setAttribute("currentPage", this.pageIndex);
        request.setAttribute("endIndex", this.endIndex);
        request.setAttribute("notBeginIndex", this.pageIndex != 1);
        request.setAttribute("notEndIndex", (this.pageIndex - 1) * pageDTO.getMaxSize() < count);
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Process the web inputs : page size and page index
    private void processParameters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processNavbarRequestMessage(request);
        String pageSizeInput = request.getParameter("pageSize");

        if (pageSizeInput != null) {
            if (pageSizeInput.equals("10") || pageSizeInput.equals("50") || pageSizeInput.equals("100")) {
                this.pageSize = Integer.parseInt(pageSizeInput);
                this.pageIndex = 1;
            }
        }

        String pageIndexInput = request.getParameter("pageIndex");
        if (pageIndexInput != null) {
            try {
                this.pageIndex = Integer.parseInt(pageIndexInput);
                if (this.pageIndex < 1) {
                    throw new NumberFormatException("Wrong index");
                }
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
            }
        }
    }

    // Set the pagination index borders (min and max displayed)
    private void setIndexBorders(Long pageSize, Long nbElements) {
        int range = 3;
        beginIndex = pageIndex - range;
        endIndex = pageIndex + range;

        int limit = (int) Math.ceil((double) nbElements / (double) pageSize);
        if (endIndex >= limit) {
            beginIndex = beginIndex - (endIndex - limit);
            endIndex = limit;
        }

        if (beginIndex < 1) {
            beginIndex = 1;
        } else if (beginIndex == 1 && limit > 7) {
            endIndex = 6;
        }
    }

    // Forward the attributes for a navbar information (actions feedback)
    private void processNavbarRequestMessage(HttpServletRequest request) {
        if (request.getAttribute("postMessage") != null) {
            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", request.getAttribute("messageLevel"));
            request.setAttribute("messageHeader", request.getAttribute("messageHeader"));
            request.setAttribute("messageBody", request.getAttribute("messageBody"));
        } else {
            request.setAttribute("postMessage", "false");
        }
    }
}
