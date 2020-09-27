package net.mf;

import java.io.File;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import com.hpe.leanft.selenium.By;
import com.hpe.leanft.selenium.Utils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    //These are known defaults as of 2018/sep/12
    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";

    private static WebDriver driver;
    public SeleniumTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        WebDriverManager.chromedriver().forceCache();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);

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
    public void purchaseTablet() throws Exception {
        driver.get(ADV_WEBSITE);

        Reporter.reportEvent("Open Website", "Opening website: "+ADV_WEBSITE);

        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Login to Advantage Online Shopping
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id = 'hrefUserIcon']/*[@id = 'menuUser']")));
        Utils.highlight(driver.findElement(By.xpath("//*[@id = 'hrefUserIcon']/*[@id = 'menuUser']")), 3000);
        RenderedImage img = Utils.getSnapshot(driver.findElement(By.xpath("//*[@id = 'menuUserLink']")));
        Reporter.reportEvent("Click Menu Icon","Found", Status.Passed, img);
        driver.findElement(By.xpath("//*[@id = 'hrefUserIcon']/*[@id = 'menuUser']")).click();

        // Type Username
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]")));
        Reporter.reportEvent("Type Username","Found", Status.Passed, img);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[1]/div/input")).sendKeys(ADV_LOGIN);

        // Type Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")));
        Utils.highlight(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]")));
        Reporter.reportEvent("Type Password","Found", Status.Passed, img);
        driver.findElement(By.xpath("/html/body/login-modal/div/div/div[3]/sec-form/sec-view[2]/div/input")).sendKeys(ADV_PASSWORD);

        // Sign in
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("SIGN IN")));
        Utils.highlight(driver.findElement(By.visibleText("SIGN IN")), 3000);
        img = Utils.getSnapshot(driver.findElement(By.visibleText("SIGN IN")));
        Reporter.reportEvent("SING IN","Found", Status.Passed, img);
        driver.findElement(By.visibleText("SIGN IN")).click();
        Thread.sleep(2000);

        //Click on Tablets
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("TABLETS")));
        Utils.highlight(driver.findElement(By.visibleText("TABLETS")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath("//*[@id = 'tabletsImg']")));
        Reporter.reportEvent("Tablets","Found", Status.Passed, img);
        driver.findElement(By.visibleText("TABLETS")).click();

        //Click on specific tablet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("HP Pro Tablet 608 G1")));
        Utils.highlight(driver.findElement(By.visibleText("HP Pro Tablet 608 G1")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath("//img[@id='18']")));
        Reporter.reportEvent("Click on Tablet","Found", Status.Passed, img);
        driver.findElement(By.visibleText("HP Pro Tablet 608 G1")).click();
        Thread.sleep(3000);

        //Add Tablet to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("ADD TO CART")));
        Utils.highlight(driver.findElement(By.visibleText(("ADD TO CART"))), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath("//button[@name='save_to_cart']")));
        Reporter.reportEvent("Add To Cart","Found", Status.Passed, img);
        driver.findElement(By.xpath("//button[@name='save_to_cart']")).click();

        //Go to Checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))));
        Utils.highlight(driver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))), 1000);
        img = Utils.getSnapshot(driver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))));
        Reporter.reportEvent("Go To Checkout","Found", Status.Passed, img);
        driver.findElement(By.visibleText(Pattern.compile("CHECKOUT \\(\\$*"))).click();
        Thread.sleep(3000);

        //Checkout Next
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("NEXT")));
        Utils.highlight(driver.findElement(By.visibleText("NEXT")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.visibleText("NEXT")));
        Reporter.reportEvent("Checkout Next","Found", Status.Passed, img);
        driver.findElement(By.visibleText("NEXT")).click();

        String path ="//*[@name='safepay_username']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(driver.findElement(By.xpath(path)), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath(path)));
        Reporter.reportEvent("Type SafePay username","Found", Status.Passed, img);
        driver.findElement(By.xpath(path)).clear();
        driver.findElement(By.xpath(path)).sendKeys(ADV_LOGIN);

        path = "//*[@id=\"paymentMethod\"]/div/div[2]/sec-form/sec-view[2]/div/input";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Utils.highlight(driver.findElement(By.xpath(path)), 1000);
        img = Utils.getSnapshot(driver.findElement(By.xpath(path)));
        Reporter.reportEvent("Type SafePay password","Found", Status.Passed, img);
        driver.findElement(By.xpath(path)).clear();
        driver.findElement(By.xpath(path)).sendKeys(ADV_PASSWORD+"1");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("PAY NOW")));
        Utils.highlight(driver.findElement(By.visibleText("PAY NOW")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.visibleText("PAY NOW")));
        Reporter.reportEvent("Click PAY NOW","Found", Status.Passed, img);
        driver.findElement(By.visibleText("PAY NOW")).click();

        //Logout of Advantage
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menuUser")));
        Utils.highlight(driver.findElement(By.id("menuUser")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.id("menuUser")));
        Reporter.reportEvent("Click Menu Icon","Found", Status.Passed, img);
        // Reporting Order Info
        img = Utils.getSnapshot(driver.findElement(By.xpath("//*[@ng-show='paymentEnd']")));
        Reporter.reportEvent("Order Info","Found", Status.Passed, img);
        driver.findElement(By.id("menuUser")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.visibleText("Sign out")));
        Utils.highlight(driver.findElement(By.visibleText("Sign out")), 1000);
        img = Utils.getSnapshot(driver.findElement(By.visibleText("Sign out")));
        Reporter.reportEvent("Sign Out","Found", Status.Passed, img);
        driver.findElement(By.visibleText("Sign out")).click();

        //Added sleep here to give time to see the selection
        Thread.sleep(3000);
    }
}