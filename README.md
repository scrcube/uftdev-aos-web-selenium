# uftdev-aos-web-selenium
An example of running a Selenium Test through UFT Developer (Formerly LeanFT)

This project assumes you have followed the LeanFT set up instructions found on https://admhelp.microfocus.com/leanft/en/15.0/HelpCenter/Content/HowTo/Sel_LeanFT4SelT.htm

**NOTE:** You need to be connected to the internet the first time this is executed so the chromewebdriver gets cached to your .m2 cache

This sample script uses the LeanFT for Selenium extensions available https://search.maven.org/search?q=a:leanft-selenium-java-sdk
```
        <dependency>
            <groupId>com.microfocus.adm.leanft</groupId>
            <artifactId>leanft-selenium-java-sdk</artifactId>
            <version>1.0</version>
        </dependency>
```

Check which versions of libraries you have available and make the appropriate changes in the pom.xml

```
        <junit.ver>4.12</junit.ver>
        <se.ver>2.53.1</se.ver>
        <webdrivermanager.ver>3.0.0</webdrivermanager.ver>
        <slf4j.ver>1.7.25</slf4j.ver>
        <leanft.version>RELEASE</leanft.version>
```

### Selenium Web Driver Manager
This script makes use of a utility that simplifies getting and setting the webdriver (Chrome in this case).  Using this approach makes the assumption that you have access to the internet when this script is run the first time so the drivers can automatically be downloaded and added to your local .m2 cache.

More details about the utility can be found:

* https://github.com/bonigarcia/webdrivermanager#webdrivermanager-as-java-dependency
* https://stackoverflow.com/questions/7450416/selenium-2-chrome-driver

#### Note for execution from command line (the below is for reference and i have not validated on this release since we are using Maven all the time now)
java -cp .;junit-4.12.jar;presales-1.0-SNAPSHOT.jar;hamcrest-core-1.3.jar;selenium-server-standalone-2.53.1.jar;com.hpe.lft.selenium.jar org.junit.runner.JUnitCore SeleniumTest

The above assumes:
- you have done a maven deploy goal
- all the relavant *.jar files are in the same directory
- to get junit.jar and hamcrest-core.jar if not on your system already https://github.com/junit-team/junit4/wiki/Download-and-Install

