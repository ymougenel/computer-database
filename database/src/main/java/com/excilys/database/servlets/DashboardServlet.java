package com.excilys.database.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.entities.Page;
import com.excilys.database.mapper.PageWrapper;
import com.excilys.database.services.ComputerServiceInterface;

/**
 * Servlet implementation class MyServlet
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int beginIndex;
    private int endIndex;
    @Autowired
    private ComputerServiceInterface computerService;
    /**
     * Default constructor.
     */
    public DashboardServlet() {
        super();
    }

    //    @Override
    //    public void init(ServletConfig config) throws ServletException {
    //        super.init(config);
    //        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    //    }
    /**
     * @throws IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(final ModelMap pModel, @RequestParam Map<String, String> params) {

        Long count = null;
        Page<Computer> page = PageWrapper.wrapWebRequest(params);
        String search = page.getSearch();
        if (search != null && search != "") {
            count = computerService.countComputers(search);
            pModel.addAttribute("search", search);
        } else {
            count = computerService.countComputers();
        }
        //        page = ComputerService.getInstance().listComputers(search,
        //                (page.getIndex() - 1) * page.getMaxSize(), page.getMaxSize(), page.getField(), page.getOrder());

        page.setEntities(computerService.listComputers(search,
                (page.getIndex() - 1) * page.getMaxSize(), page.getMaxSize(), page.getField(), page.getOrder()));
        Page<ComputerDTO> pageDTO = PageWrapper.wrapPage(page);

        setIndexBorders(pageDTO.getMaxSize(), count, page.getIndex());
        pModel.addAttribute("count", count);
        pModel.addAttribute("page", pageDTO);
        pModel.addAttribute("beginIndex", this.beginIndex);
        pModel.addAttribute("endIndex", this.endIndex);
        pModel.addAttribute("notBeginIndex", pageDTO.getIndex() != 1);
        pModel.addAttribute("notEndIndex", (pageDTO.getIndex() - 1) * pageDTO.getMaxSize() < count);
        return "dashboard";
        //pModel.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(final ModelMap pModel, @RequestParam Map<String, String> params) {
        return doGet(pModel, params);
    }

    // Set the pagination index borders (min and max displayed)
    private void setIndexBorders(Long pageSize, Long nbElements,int pageIndex) {
        int range = 3;

        if (nbElements <= pageSize) {
            beginIndex = 1;
            endIndex =1;
            return;
        }

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
}
