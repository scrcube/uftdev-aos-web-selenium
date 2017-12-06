package net.mf;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import com.hpe.leanft.selenium.By;
import com.hpe.leanft.selenium.Utils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

// added to access the LeanFT reporting capabilities
import com.hp.lft.sdk.*;
import com.hp.lft.report.*;
import java.net.URI;

import java.util.regex.Pattern;

public class SeleniumTest  {
    private static final String ADV_WEBSITE  = "insert website here";
    private static final String ADV_LOGIN    = "insert login name here";
    private static final String ADV_PASSWORD = "insert password here";

    private static WebDriver driver;
    public SeleniumTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/opt/selenium/2.27/chromedriver");
        driver = new ChromeDriver();

        // The following is what is needed to add the LeanFT reporting to your custom test framework.
        // In this case a basic Selenium enabled test using the Junit framework
        // This is base on the LeanFT 14.02 release https://admhelp.microfocus.com/leanft/en/latest/HelpCenter/Content/HowTo/CustomFrameworks.htm
        try{
            ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
            config.setServerAddress(new URI("ws://localhost:5095"));
            SDK.init(config);
            Reporter.init();
        }
        catch(Exception e){
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {
        //Clean up and dispose of the driver
        //Good explanation of close, quit, dispose here http://stackoverflow.com/questions/15067107/difference-between-webdriver-dispose-close-and-quit
        driver.quit();
        try {
            Reporter.generateReport();
            SDK.cleanup();
        }
        catch (Exception e){
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void corndog() throws Exception {
        driver.get(ADV_WEBSITE);

        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Login to Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/nav/ul/li[2]/a")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/header/nav/ul/li[2]/a")), 1000);
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[2]/a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")), 1000);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")).sendKeys(ADV_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")), 1000);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")).sendKeys(ADV_PASSWORD);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("SIGN IN")));
        Utils.highlight(driver.findElement(By.visibleText("SIGN IN")), 1000);
        driver.findElement(By.visibleText("SIGN IN")).click();

        //Click on Tablets
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("TABLETS")));
        Utils.highlight(driver.findElement(By.visibleText("TABLETS")), 1000);
        driver.findElement(By.visibleText("TABLETS")).click();

        //Click on specific tablet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("HP Pro Tablet 608 G1")));
        Utils.highlight(driver.findElement(By.visibleText("HP Pro Tablet 608 G1")), 1000);
        driver.findElement(By.visibleText("HP Pro Tablet 608 G1")).click();

        //Add Tablet to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("ADD TO CART")));
        Utils.highlight(driver.findElement(By.visibleText(("ADD TO CART"))), 1000);
        driver.findElement(By.visibleText("ADD TO CART")).click();

        //Go to Checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))));
        Utils.highlight(driver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))), 1000);
        driver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))).click();

        //Checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("NEXT")));
        Utils.highlight(driver.findElement(By.visibleText("NEXT")), 1000);
        driver.findElement(By.visibleText("NEXT")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("PAY NOW")));
        Utils.highlight(driver.findElement(By.visibleText("PAY NOW")), 1000);
        driver.findElement(By.visibleText("PAY NOW")).click();

        //Logout of Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/nav/ul/li[2]/a")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/header/nav/ul/li[2]/a")), 1000);
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[2]/a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("Sign out")));
        Utils.highlight(driver.findElement(By.visibleText("Sign out")), 1000);
        driver.findElement(By.visibleText("Sign out")).click();

        //Added sleep here to give time to see the selection
        Thread.sleep(3000);
    }
}