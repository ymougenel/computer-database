package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PaginationExplorerTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Ignore @Test
    public void testPaginationExploration() throws Exception {
        driver.get(baseUrl + "/database/dashboard?pageIndex=5");
        driver.findElement(By.linkText("3")).click();
        driver.findElement(By.id("addComputer")).click();
        driver.findElement(By.linkText("Cancel")).click();
        driver.findElement(By.linkText("4")).click();
        driver.findElement(By.xpath("//li[9]/a/span")).click();
        driver.findElement(By.linkText("«")).click();
    }

    @Test
    public void testPreviousDisplay() throws Exception {
        driver.get(baseUrl + "/database/dashboard");
        driver.findElement(By.linkText("2")).click();
        try {
            WebElement previous = driver.findElement(By.id("previous"));
            assertNotNull(previous);
            previous = driver.findElement(By.id("previous"));
            assertNotNull(previous);
        } catch (Exception e){
            System.out.println("This should not be printed 2");
            assertTrue(false);
        }
    }

    @Test
    public void pageSizeTest(){
        driver.get(baseUrl + "/database/dashboard");
        driver.findElement(By.linkText("10")).click();
        List<WebElement> computers = driver.findElements(By.id("computer"));
        assertEquals(10,computers.size());

        driver.findElement(By.linkText("50")).click();
        computers = driver.findElements(By.id("computer"));
        assertEquals(50,computers.size());

        driver.findElement(By.linkText("100")).click();
        computers = driver.findElements(By.id("computer"));
        assertEquals(100,computers.size());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}