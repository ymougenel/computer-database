package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.database.entities.Company;
import com.excilys.database.services.CompanyService;

public class CompanyServiceTest {
    private static CompanyService companyService;

    @BeforeClass
    public static void setUp() throws Exception {
        companyService = CompanyService.getInstance();
    }

    @Test
    public void findTest() {
        Company comp = companyService.findCompany(3L);
        assertEquals(comp.getName(), "RCA");
    }

    //    @Test (expected = DAOException.class)
    //    public void findTestError() {
    //        Company comp = companyService.findCompany(-1L);
    //        assertEquals(comp.getName(), "RCA");
    //    }

    @Test
    public void findTestNull() {
        Long count = companyService.countCompanies();
        Company comp = companyService.findCompany(count + 100);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        Company comp = new Company();
        comp.setName("Yoloooo");
        companyService.insertCompany(comp);
        assertNotNull(comp.getId());
    }

    @Test
    public void insertTestError() {
        Company comp = new Company();
        companyService.insertCompany(comp);
        assertNotNull(comp.getId());
    }


    @Test
    public void countTest() {
        Long count = companyService.countCompanies();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        long count = companyService.countCompanies();
        Company comp = new Company();
        comp.setName("CountTest");
        companyService.insertCompany(comp);
        long count2 = companyService.countCompanies();
        assertEquals(count + 1, count2);
    }

    @Test
    public void countTestDeletion() {
        long count = companyService.countCompanies();
        Company comp = new Company();
        comp.setName("deletionTest");
        companyService.insertCompany(comp);

        long count1 = companyService.countCompanies();
        companyService.deleteCompany(comp);
        long count2 = companyService.countCompanies();
        assertEquals(count, count2);
        assertEquals(count + 1, count1);
        assertEquals(count1, count2 + 1);
    }

    public void delete() {
        Company comp = new Company("company404");
        companyService.insertCompany(comp);
        companyService.deleteCompany(comp);
        assertNull(companyService.findCompany(comp.getId()));
    }

    @Test
    public void updateTest() {
        Company comp = new Company();
        comp.setName("First");
        companyService.insertCompany(comp);
        comp.setName("Second");
        companyService.updateCompany(comp);
        Company result = companyService.findCompany(comp.getId());
        assertEquals("Second", result.getName());
    }

    @Test
    public void ListAllTest() {
        long count = companyService.countCompanies();
        List<Company> companies = new ArrayList<Company>(companyService.listCompanies());
        assertEquals(count, companies.size());
    }

    @AfterClass
    public static void cleanBdd() {
        for (Company comp : companyService.listCompanies()) {
            if (comp.getId() > 43) {
                companyService.deleteCompany(comp);
            }
        }
    }
}
