package com.excilys.database.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.database.entities.Company;
import com.excilys.database.services.CompanyServiceInterface;

@Component
public class CachePersistenceHandler {

    public static AtomicBoolean companiesLoaded = new AtomicBoolean(false);

    @Autowired
    private CompanyServiceInterface companyService;

    public static HashMap<Long, String> companies;
    public static AtomicLong count = null;

    public String getCompanyName(Long id) {
        if (!companiesLoaded.get()) {
            companies = new HashMap<>();
            System.out.println("gheyy---------"+companyService==null);
            List<Company> l = companyService.listCompanies();
            for (Company c : l) {
                companies.put(c.getId(), c.getName());
            }
            companiesLoaded.set(true);
        }

        return companies.get(id);
    }



}
