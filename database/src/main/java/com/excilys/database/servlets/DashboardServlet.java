package com.excilys.database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.entities.Page;
import com.excilys.database.mapper.PageWrapper;
import com.excilys.database.services.implementation.ComputerService;

/**
 * Servlet implementation class MyServlet
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int beginIndex;
    private int endIndex;

    /**
     * Default constructor.
     */
    public DashboardServlet() {
        super();
    }

    /**
     * @throws IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processNavbarRequestMessage(request);

        Long count = null;
        Page<Computer> page = PageWrapper.wrapWebRequest(request);
        String search = page.getSearch();
        if (search != null && search != "") {
            count = ComputerService.getInstance().countComputers(search);
            request.setAttribute("search", search);
        } else {
            count = ComputerService.getInstance().countComputers();
        }
        page = ComputerService.getInstance().listComputers(search,
                (page.getIndex() - 1) * page.getMaxSize(), page.getMaxSize(), page.getField(), page.getOrder());

        Page<ComputerDTO> pageDTO = PageWrapper.wrapPage(page);

        setIndexBorders(pageDTO.getMaxSize(), count, page.getIndex());
        request.setAttribute("count", count);
        request.setAttribute("page", pageDTO);
        request.setAttribute("beginIndex", this.beginIndex);
        request.setAttribute("endIndex", this.endIndex);
        request.setAttribute("notBeginIndex", page.getIndex() != 1);
        request.setAttribute("notEndIndex", (page.getIndex() - 1) * pageDTO.getMaxSize() < count);
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

    // Set the pagination index borders (min and max displayed)
    private void setIndexBorders(Long pageSize, Long nbElements,int pageIndex) {
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
