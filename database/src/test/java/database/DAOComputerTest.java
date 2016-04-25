package database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDAO;

public class DAOComputerTest {
	private ComputerDAO computerDAO;

	@Before
	public void setUp() throws Exception {
		computerDAO = ComputerDAO.getInstance();
	}

	@Test
	public void findTest() {
		Computer c = computerDAO.find(55);
		assertEquals(c.getName(), "Apple I");
	}

	@Test
	public void findTestNull() {
		Computer c = computerDAO.find(600);
		assertNull(c);
	}

	@Test
	public void insertTest() {
		Computer c = new Computer();
		c.setName("Yoloooo");
		computerDAO.create(c);
		assertEquals("Apple I", "Apple I");
		assertNotNull(c.getId());
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
		Computer c = new Computer();
		c.setName("CountTest");
		computerDAO.create(c);
		long count2 = computerDAO.count();
		assertEquals(count + 1, count2);
	}

	@Test
	public void countTestDeletion() {
		long count = computerDAO.count();
		Computer c = new Computer();
		c.setName("deletionTest");
		computerDAO.create(c);

		long count1 = computerDAO.count();
		computerDAO.delete(c);
		long count2 = computerDAO.count();
		assertEquals(count, count2);
		assertEquals(count + 1, count1);
		assertEquals(count1, count2 + 1);
	}
}
