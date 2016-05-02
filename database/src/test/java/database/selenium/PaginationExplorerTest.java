package java.database.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PaginationExplorerTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}