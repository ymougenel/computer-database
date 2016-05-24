package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDaoInterface;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class DAOComputerTest {

    @Autowired
    private ComputerDaoInterface computerDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("set uop");
        //computerDAO = ComputerDAO.getInstance();
    }

    @Test
    public void findTest() {
        System.out.println("find");
        Computer comp = computerDAO.find(2);
        assertEquals(comp.getName(), "CM-2a");
    }

    @Test
    public void findTestNull() {
        System.out.println("find null");
        Long count = computerDAO.count();
        Computer comp = computerDAO.find(count + 10);
        assertNull(comp);
    }

    @Test
    public void insertTest() {
        System.out.println("insert");
        Computer comp = new Computer();
        comp.setName("Yoloooo");
        computerDAO.create(comp);
        assertNotNull(comp.getId());
    }

    @Test
    public void countTest() {
        System.out.println("count test");
        Long count = computerDAO.count();
        assertNotNull(count);
        assertNotSame(count, 0);
    }

    @Test
    public void countTestInsertion() {
        System.out.println("count insertion");
        long count = computerDAO.count();
        Computer comp = new Computer();
        comp.setName("CountTest");
        computerDAO.create(comp);
        long count2 = computerDAO.count();
        assertEquals(count + 1, count2);
    }

    @Test
    public void countTestDeletion() {
        System.out.println("count deletion");
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

    @Test
    public void deleteTest() {
        System.out.println("Delete");
        Computer comp = new Computer.Builder("computer404").build();
        computerDAO.create(comp);
        computerDAO.delete(comp);
        assertNull(computerDAO.find(comp.getId()));
    }

    @Test
    public void updateTest() {
        System.out.println("update");
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

    /* @After
    public void cleanBdd() {
        System.out.println("cleaning");
        List<Computer> computers = computerDAO.listAll();
        for (Computer comp : computers) {
            if (comp.getId() > 574) {
                computerDAO.delete(comp);
            }
        }
        System.out.println("done cleaning");
    }*/
}
