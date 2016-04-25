package database;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDAO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

public class DAOComputerTest {
  private ComputerDAO computerDAO;

  @Before
  public void setUp() throws Exception {
    computerDAO = ComputerDAO.getInstance();
  }

  @Test
  public void findTest() {
    Computer comp = computerDAO.find(55);
    assertEquals(comp.getName(), "Apple I");
  }

  @Test
  public void findTestNull() {
    Computer comp = computerDAO.find(600);
    assertNull(comp);
  }

  @Test
  public void insertTest() {
    Computer comp = new Computer();
    comp.setName("Yoloooo");
    computerDAO.create(comp);
    assertEquals("Apple I", "Apple I");
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
}
