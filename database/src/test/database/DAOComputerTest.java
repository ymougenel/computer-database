package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.implementation.ComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class DAOComputerTest {

    @Autowired
    private ComputerDAO computerDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        //computerDAO = ComputerDAO.getInstance();
    }

    @Test
    public void findTest() {
        Computer comp = computerDAO.find(2);
        assertEquals(comp.getName(), "CM-2a");
    }

    @Test
    public void findTestNull() {
        Long count = computerDAO.count();
        Computer comp = computerDAO.find(count + 10);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        Computer comp = new Computer();
        comp.setName("Yoloooo");
        computerDAO.create(comp);
        assertNotNull(comp.getId());
    }

    @Test
    public void countTest() {
        Long count = computerDAO.count();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        long count = computerDAO.count();
        Computer comp = new Computer();
        comp.setName("CountTest");
        computerDAO.create(comp);
        long count2 = computerDAO.count();
        assertEquals(count + 1, count2);
    }

    @Test
    public void countTestDeletion() {
        long count = computerDAO.count();
        Computer comp = new Computer();
        comp.setName("deletionTest");
        computerDAO.create(comp);

        long count1 = computerDAO.count();
        computerDAO.delete(comp);
        long count2 = computerDAO.count();
        assertEquals(count, count2);
        assertEquals(count + 1, count1);
        assertEquals(count1, count2 + 1);
    }

    public void delete() {
        Computer comp = new Computer.Builder("computer404").build();
        computerDAO.create(comp);
        computerDAO.delete(comp);
        assertNull(computerDAO.find(comp.getId()));
    }

    @Test
    public void updateTest() {
        Computer comp = new Computer();
        comp.setName("First");
        computerDAO.create(comp);
        comp.setName("Second");
        computerDAO.update(comp);
        Computer result = computerDAO.find(comp.getId());
        assertEquals("Second", result.getName());
    }

    // NOTE run under eclipse junit test, yet doesnt work for maven...
    //    @Test
    //    public void ListAllTest() {
    //        long size = 10;
    //        List<Computer> computers = new ArrayList<Computer>();
    //        for (long i = 0; i < size; i++) {
    //            Computer comp = new Computer();
    //            comp.setName(i + "");
    //            computers.add(comp);
    //            computerDAO.create(comp);
    //        }
    //
    //        List<Computer> results = computerDAO.listAll();
    //        for (int i = 0; i < size; i++) {
    //            assert(results.contains(computers.get(i)));
    //        }
    //    }

    @After
    public void cleanBdd() {
        List<Computer> computers = new LinkedList<Computer>(computerDAO.listAll());
        for (Computer comp : computers) {
            if (comp.getId() > 574) {
                computerDAO.delete(comp);
            }
        }
    }
}
