# Lft4Se
This project assumes you have followed the LeanFT set up instructions found on https://admhelp.microfocus.com/leanft/en/14.50/HelpCenter/Content/HowTo/Sel_LeanFT4SelT.htm

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
        <lft.ver>14.50.0</lft.ver>
```

### getDrivers.sh
The getDrivers.sh is a small utility to aid in getting the selenium driver required for this script.  This script was created to use the Selenium Chrome driver and as of the time of this writing, the driver 2.37 was the last driver version tested to work.

The script assumes your chromedriver is in the lft4se/<VER> folder.  If you will be placing it in a folder other than that, then you will need to edit the test.

To use the getDriver.sh, cd to the lft4se folder and then run (enter a different version for the chrome driver if you do not want to use 2.41):

```
./getDrivers.sh 2.41
```
You can also add the chromedriver to your system path and the script will work.

This script was last updated and verified using the chromedriver 2.41

If you are using the IntelliJ container to run this test, you will need to perform the above steps inside the IntelliJ container.

#### Note for execution from command line (the below is for reference and i have not validated on this release since we are using Maven all the time now)
java -cp .;junit-4.12.jar;presales-1.0-SNAPSHOT.jar;hamcrest-core-1.3.jar;selenium-server-standalone-2.53.1.jar;com.hpe.lft.selenium.jar org.junit.runner.JUnitCore SeleniumTest

The above assumes:
- you have done a maven deploy goal
- all the relavant *.jar files are in the same directory
- to get junit.jar and hamcrest-core.jar if not on your system already https://github.com/junit-team/junit4/wiki/Download-and-Install

