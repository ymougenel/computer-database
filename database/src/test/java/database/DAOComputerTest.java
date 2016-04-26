package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDAO;

public class DAOComputerTest {
    private static ComputerDAO computerDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        computerDAO = ComputerDAO.getInstance();
    }

    @Test
    public void findTest() {
        Computer comp = computerDAO.find(2);
        //        assertEquals(comp.getName(), "CM-2A");
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
        Computer comp = new Computer("computer404");
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

    @AfterClass
    public static void cleanBdd() {
        List<Computer> computers = new LinkedList<Computer>(computerDAO.listAll());
        for (Computer comp : computers) {
            if (comp.getId() > 574) {
                computerDAO.delete(comp);
            }
        }
    }
}
