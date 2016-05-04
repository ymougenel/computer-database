package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.implementation.CompanyDAO;
import com.excilys.database.persistence.implementation.ComputerDAO;

public class DAOCompanyTest {
    private static CompanyDAO companyDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        companyDAO = CompanyDAO.getInstance();
    }

    @Test
    public void findTest() {
        Company comp = companyDAO.find(3);
        assertEquals(comp.getName(), "RCA");
    }

    @Test
    public void findTestNull() {
        Long count = companyDAO.count();
        Company comp = companyDAO.find(count + 100);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        Company comp = new Company();
        comp.setName("Yoloooo");
        companyDAO.create(comp);
        assertNotNull(comp.getId());
    }

    @Test
    public void countTest() {
        Long count = companyDAO.count();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        long count = companyDAO.count();
        Company comp = new Company();
        comp.setName("CountTest");
        companyDAO.create(comp);
        long count2 = companyDAO.count();
        assertEquals(count + 1, count2);
    }

    @Test
    public void countTestDeletion() {
        long count = companyDAO.count();
        Company comp = new Company();
        comp.setName("deletionTest");
        companyDAO.create(comp);

        long count1 = companyDAO.count();
        companyDAO.delete(comp);
        long count2 = companyDAO.count();
        assertEquals(count, count2);
        assertEquals(count + 1, count1);
        assertEquals(count1, count2 + 1);
    }

    public void delete() {
        Company comp = new Company("company404");
        companyDAO.create(comp);
        companyDAO.delete(comp);
        assertNull(companyDAO.find(comp.getId()));
    }

    @Test
    public void updateTest() {
        Company comp = new Company();
        comp.setName("First");
        companyDAO.create(comp);
        comp.setName("Second");
        companyDAO.update(comp);
        Company result = companyDAO.find(comp.getId());
        assertEquals("Second", result.getName());
    }

    @Test
    public void ListAllTest() {
        long count = companyDAO.count();
        List<Company> companies = new ArrayList<Company>(companyDAO.listAll());
        assertEquals(count, companies.size());
    }

    @AfterClass
    public static void cleanBdd() {
        List<Company> companys = new LinkedList<Company>(companyDAO.listAll());
        for (Company comp : companys) {
            if (comp.getId() > 574) {
                companyDAO.delete(comp);
            }
        }
    }

}
