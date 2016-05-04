package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ComputerInsertionTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testComputerInsertion() throws Exception {
        driver.get(baseUrl + "/database/dashboard");
        String homeTitle = driver.findElement(By.id("homeTitle")).getText();
        long totalCount = Long.parseLong(homeTitle.split(" ")[0]);
        driver.findElement(By.id("addComputer")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("Test");
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2012-02-04");
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2013-02-05");
        new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Apple Inc.");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

        homeTitle = driver.findElement(By.id("homeTitle")).getText();
        long newCount = Long.parseLong(homeTitle.split(" ")[0]);
        assertEquals(totalCount+1, newCount);
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