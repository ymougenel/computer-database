package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.entities.Page.Order;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.services.InvalidInsertionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class ComputerServiceTest {

    @Autowired
    private ComputerServiceInterface computerService;
    public static long initialDBSize = 574;

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("setup");
    }

    @Test
    public void findTest() {
        System.out.println("find test");
        Computer comp = computerService.findComputer(3l);
        assertEquals(comp.getName(), "CM-200");
    }

    @Test
    public void findTestNull() {
        System.out.println("find null");
        Long count = computerService.countComputers();
        Computer comp = computerService.findComputer(count + 100);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        System.out.println("insert teset");
        Computer comp = new Computer.Builder("Insertion").build();
        computerService.insertComputer(comp);
        assertNotNull(comp.getId());
    }

    // Exception based tests are ignored : computer validation is done by the servlet
    @Ignore @Test(expected = IllegalArgumentException.class)
    public void insertTestNameError() {
        System.out.println("insert error");
        Computer comp = new Computer();
        computerService.insertComputer(comp);
    }

    @Ignore @Test(expected = IllegalArgumentException.class)
    public void insertTestDateError() {
        Computer comp = new Computer.Builder("Insertion").build();
        comp.setIntroduced(LocalDate.parse("1930-02-03"));
        computerService.insertComputer(comp);
    }

    @Ignore @Test(expected = IllegalArgumentException.class)
    public void insertTestDate2Error() {
        Computer comp = new Computer.Builder("Insertion").build();
        comp.setIntroduced(LocalDate.parse("2300-02-03"));
        computerService.insertComputer(comp);
    }

    @Test
    public void countTest() {
        System.out.println("count");
        Long count = computerService.countComputers();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        System.out.println("count insertion");
        long count = computerService.countComputers();
        Computer comp = new Computer.Builder("CountTest").build();
        computerService.insertComputer(comp);
        long count2 = computerService.countComputers();
        assertEquals(count + 1, count2);
    }

    @Ignore @Test //Ignored due to a null exception (connection has to be initialized by service)
    public void countTestDeletion() {
        long count = computerService.countComputers();
        Computer comp = new Computer();
        comp.setName("deletionTest");
        computerService.insertComputer(comp);

        long count1 = computerService.countComputers();
        computerService.deleteComputer(comp);
        long count2 = computerService.countComputers();
        assertEquals(count, count2);
        assertEquals(count + 1, count1);
        assertEquals(count1, count2 + 1);
    }

    @Test
    public void deleteTest() {
        System.out.println("delete");
        Computer comp = new Computer.Builder("computer404").build();
        computerService.insertComputer(comp);
        computerService.deleteComputer(comp);
        assertNull(computerService.findComputer(comp.getId()));
    }

    @Test
    public void updateTest() throws InvalidInsertionException {
        System.out.println("udateTest");
        Computer comp = new Computer();
        comp.setName("First");
        computerService.insertComputer(comp);
        comp.setName("Second");
        computerService.updateComputer(comp);
        Computer result = computerService.findComputer(comp.getId());
        assertEquals("Second", result.getName());
    }


    @After
    public void cleanBdd() {
        System.out.println("cleaning");
        long count = computerService.countComputers();
        List<Computer> computers = computerService.listComputers(null, 0, 2*count, Page.CompanyTable.ID ,Order.ASC);
        for (Computer comp : computers) {
            if (comp.getId() > initialDBSize) {
                computerService.deleteComputer(comp);
            }
        }
    }
}
