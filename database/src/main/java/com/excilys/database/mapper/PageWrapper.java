package com.excilys.database.mapper;

import javax.servlet.http.HttpServletRequest;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.ComputerDTO;
import com.excilys.database.entities.Page;
import com.excilys.database.entities.Page.Order;

public class PageWrapper {
    public static Page<Computer> wrapWebRequest(HttpServletRequest request) {
        Page<Computer> page = new Page<Computer>();

        String orderInput = request.getParameter("order");
        if (orderInput != null && orderInput.equals("DESC")) {
            page.setOrder(Order.DESC);
        } else {
            page.setOrder(Order.ASC);
        }

        String fieldInput = request.getParameter("field");
        if (fieldInput != null) {
            page.setField(Page.CompanyTable.getField(fieldInput));
        }
        else {
            page.setField(Page.CompanyTable.NAME);
        }

        String search = request.getParameter("search");
        if (search != null ) {
            page.setSearch(search);
        }

        String pageSizeInput = request.getParameter("pageSize");
        if (pageSizeInput != null) {
            if (pageSizeInput.equals("10") || pageSizeInput.equals("50")
                    || pageSizeInput.equals("100")) {
                page.setMaxSize(Long.parseLong(pageSizeInput));
                page.setIndex(1);
            }
        }

        String pageIndexInput = request.getParameter("pageIndex");
        if (pageIndexInput != null) {
            page.setIndex(Integer.parseInt(pageIndexInput));
        }

        return page;
    }

    public static Page<ComputerDTO> wrapPage(Page<Computer> page) {
        Page<ComputerDTO> pageDTO = new Page<ComputerDTO>();
        pageDTO.setSearch(page.getSearch());
        pageDTO.setField(page.getField());
        pageDTO.setMaxSize(page.getMaxSize());
        pageDTO.setIndex(page.getIndex());
        pageDTO.setOrder(page.getOrder());

        for (Computer comp : page.getEntities()) {
            pageDTO.addEntity(new ComputerDTO(comp));
        }
        return pageDTO;
    }
}
