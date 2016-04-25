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

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.services.ComputerService;
import com.excilys.database.services.InvalidInsertionException;

public class ComputerServiceTest {
    private static ComputerService computerService;

    @BeforeClass
    public static void setUp() throws Exception {
        computerService = ComputerService.getInstance();
    }

    @Test
    public void findTest() {
        Computer comp = computerService.findComputer(3l);
        assertEquals(comp.getName(), "CM-200");
    }

    @Test
    public void findTestNull() {
        Long count = computerService.countCompanies();
        Computer comp = computerService.findComputer(count + 100);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        Computer comp = new Computer();
        comp.setName("Yoloooo");
        try {
            computerService.insertComputer(comp);
        } catch (InvalidInsertionException e) {
            e.printStackTrace();
        }
        assertNotNull(comp.getId());
    }

    public void insertTestException() {
        Computer comp = new Computer();
        try {
            computerService.insertComputer(comp);
            assert(false);
        } catch (InvalidInsertionException e) {
            assert(true);
        }
    }

    @Test
    public void countTest() {
        Long count = computerService.countCompanies();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        long count = computerService.countCompanies();
        Computer comp = new Computer();
        comp.setName("CountTest");
        try {
            computerService.insertComputer(comp);
        } catch (InvalidInsertionException e) {
            e.printStackTrace();
        }
        long count2 = computerService.countCompanies();
        assertEquals(count + 1, count2);
    }

    @Test
    public void countTestDeletion() {
        long count = computerService.countCompanies();
        Computer comp = new Computer();
        comp.setName("deletionTest");
        try {
            computerService.insertComputer(comp);
        } catch (InvalidInsertionException e) {
            e.printStackTrace();
        }

        long count1 = computerService.countCompanies();
        computerService.deleteComputer(comp);
        long count2 = computerService.countCompanies();
        assertEquals(count, count2);
        assertEquals(count + 1, count1);
        assertEquals(count1, count2 + 1);
    }

    public void delete() {
        Computer comp = new Computer("computer404");
        try {
            computerService.insertComputer(comp);
        } catch (InvalidInsertionException e) {
            e.printStackTrace();
        }
        computerService.deleteComputer(comp);
        assertNull(computerService.findComputer(comp.getId()));
    }

    @Test
    public void updateTest() throws InvalidInsertionException {
        Computer comp = new Computer();
        comp.setName("First");
        computerService.insertComputer(comp);
        comp.setName("Second");
        computerService.updateComputer(comp);
        Computer result = computerService.findComputer(comp.getId());
        assertEquals("Second", result.getName());
    }

    @Test
    public void ListAllTest() {
        long count = computerService.countCompanies();
        Page<Computer> page = computerService.ListCompanies();
        List<Computer> companies = new ArrayList<Computer>(page.getEntities());
        assertEquals(count, companies.size());
    }

    @AfterClass
    public static void cleanBdd() {
        Page<Computer> page = computerService.ListCompanies();
        for (Computer comp : page.getEntities()) {
            if (comp.getId() > 43) {
                computerService.deleteComputer(comp);
            }
        }
    }
}
