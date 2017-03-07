package net.demo;

import com.hp.lft.sdk.web.*;
import com.hp.lft.sdk.web.WebElement;
import com.hp.lft.report.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import com.hpe.leanft.selenium.By;

import unittesting.*;

public class SeleniumTest extends UnitTestClassBase{

    public SeleniumTest() {
    //Change this constructor to private if you supply your own public constructor
    }
/*
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
*/
@BeforeClass
public static void setUpBeforeClass() throws Exception {
    SeleniumTest instance = new SeleniumTest();
    UnitTestClassBase.globalSetup(SeleniumTest.class);
}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        UnitTestClassBase.globalTearDown();
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        WebDriver driver = new InternetExplorerDriver();
        driver.get("http://www.advantageonlineshopping.com:8080/#/");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver,10);

        //Click on Tablets
        wait.until(ExpectedConditions.elementToBeClickable(By.visibleText("TABLETS")));
        driver.findElement(By.id("TabletsImg")).click();

        Reporter.reportEvent("Clicked on TABLETS","");
        //Click on specific tablet
        wait.until(ExpectedConditions.elementToBeClickable(By.visibleText("HP Pro Tablet 608 G1")));
        driver.findElement(By.visibleText("HP Pro Tablet 608 G1")).click();

        //Clean up and dispose of the driver
        //Good explanation of close, quit, dispose here http://stackoverflow.com/questions/15067107/difference-between-webdriver-dispose-close-and-quit
        driver.quit();
    }

    @Test
    public void lft_test () throws Exception {
        Browser browser = BrowserFactory.launch(BrowserType.CHROME);
        browser.navigate("http://www.advantageonlineshopping.com:8080/#/");
        browser.describe(com.hp.lft.sdk.web.WebElement.class, new WebElementDescription.Builder()
                .className("categoryCell").tagName("DIV").innerText("TABLETS Shop Now ").build());

        browser.describe(WebElement.class, new WebElementDescription.Builder()
                .tagName("A").innerText("HP Pro Tablet 608 G1").build());

        browser.close();

    }
}