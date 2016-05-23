package com.excilys.database.persistence;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Company;
import com.excilys.database.services.CompanyServiceInterface;

public enum CachePersistenceHandler {
    INSTANCE;

    @Autowired
    private CompanyServiceInterface companyService;

    public static AtomicBoolean companiesLoaded;
    public static List<Company> companies;
    public static AtomicLong count;
    public static CachePersistenceHandler getInstance(){
        return INSTANCE;
    }

    public List<Company> getCompanies() {
        if (!companiesLoaded.get()) {
            companies = companyService.listCompanies();
            companiesLoaded.set(true);
        }
        return companies;
    }



}
