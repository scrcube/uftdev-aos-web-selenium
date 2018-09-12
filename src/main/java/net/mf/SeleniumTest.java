package net.mf;

import java.io.File;
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

import java.awt.image.RenderedImage;
import java.net.URI;

import java.util.regex.Pattern;

public class SeleniumTest  {
    // This script was created against AOS 1.1.3.  Since it uses Xpath, you may need to update the script
    // if using against a different version.
    private static final String ADV_WEBSITE  = "http://nimbusserver.aos.com:8000/#/";
    //private static final String ADV_WEBSITE  = "http://www.advantageonlineshopping.com";

    //You will need to have an account created in AOS and will need to supply the credentials
    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";
    private static final String DRIVER_VER = "2.41";
    private static final String DRIVER_PATH="/"+DRIVER_VER+"/chromedriver";

    private static WebDriver driver;
    public SeleniumTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        File f = new File(System.getProperty("user.dir")+DRIVER_PATH);
        if(f.exists() && !f.isDirectory()) {
            // If you don't want to specify the path to the chrome driver, you will need to make sure it is part of the system path
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+DRIVER_PATH);
            System.out.println("Using driver: "+System.getProperty("user.dir")+DRIVER_PATH);
        } else {
            System.out.println("Chrome driver not found in: "+System.getProperty("user.dir")+DRIVER_PATH);
            System.out.println("Will attempt use the chrome driver that is in the system path.  If one doesn't exist, you will get an error on execution");
        }

        driver = new ChromeDriver();

        // The following is what is needed to add the LeanFT reporting to your custom test framework.
        // In this case a basic Selenium enabled test using the Junit framework
        // This is base on the LeanFT 14.03 release https://admhelp.microfocus.com/leanft/en/latest/HelpCenter/Content/HowTo/CustomFrameworks.htm
        try{
            ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
            config.setServerAddress(new URI("ws://localhost:5095"));
            SDK.init(config);

            ModifiableReportConfiguration rptConfig = new ModifiableReportConfiguration();
            rptConfig.setSnapshotsLevel(CaptureLevel.All);
            rptConfig.setTitle("Selenium UFT Pro (LeanFT) Run Results");
            rptConfig.setDescription("Example of incorporating UFT Pro (LeanFT) reporting with tests driven using Selenium automation");
            Reporter.init(rptConfig);
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

        Reporter.reportEvent("Open Website", "Opening website: "+ADV_WEBSITE);

        //driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Login to Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/nav/ul/li[3]/a/a")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a/a")), 3000);
        driver.findElement(By.xpath("/html/body/header/nav/ul/li[3]/a/a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")), 1000);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")).sendKeys(ADV_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")), 1000);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")).sendKeys(ADV_PASSWORD);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("SIGN IN")));
        Utils.highlight(driver.findElement(By.visibleText("SIGN IN")), 3000);
        driver.findElement(By.visibleText("SIGN IN")).click();
        Thread.sleep(2000);

        //Click on Tablets
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("TABLETS")));
        Utils.highlight(driver.findElement(By.visibleText("TABLETS")), 1000);
        RenderedImage img = Utils.getSnapshot(driver.findElement(By.visibleText("TABLETS")));
        Reporter.reportEvent("TABLETS","Found", Status.Passed, img);
        driver.findElement(By.visibleText("TABLETS")).click();

        //Click on specific tablet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("HP Pro Tablet 608 G1")));
        Utils.highlight(driver.findElement(By.visibleText("HP Pro Tablet 608 G1")), 1000);
        driver.findElement(By.visibleText("HP Pro Tablet 608 G1")).click();
        Thread.sleep(3000);

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

        String path ="//*[@id=\"paymentMethod\"]/div/div[2]/sec-form/sec-view[1]/div/input";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(driver.findElement(By.xpath(path)), 1000);
        driver.findElement(By.xpath(path)).clear();
        driver.findElement(By.xpath(path)).sendKeys(ADV_LOGIN);

        path = "//*[@id=\"paymentMethod\"]/div/div[2]/sec-form/sec-view[2]/div/input";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(driver.findElement(By.xpath(path)), 1000);
        driver.findElement(By.xpath(path)).clear();
        driver.findElement(By.xpath(path)).sendKeys(ADV_PASSWORD+"1");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("PAY NOW")));
        Utils.highlight(driver.findElement(By.visibleText("PAY NOW")), 1000);
        driver.findElement(By.visibleText("PAY NOW")).click();

        //Logout of Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menuUser")));
        Utils.highlight(driver.findElement(By.id("menuUser")), 1000);
        driver.findElement(By.id("menuUser")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("Sign out")));
        Utils.highlight(driver.findElement(By.visibleText("Sign out")), 1000);
        driver.findElement(By.visibleText("Sign out")).click();

        //Added sleep here to give time to see the selection
        Thread.sleep(3000);
    }
}