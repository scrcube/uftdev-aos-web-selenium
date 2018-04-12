# Lft4Se
This project assumes you have followed the LeanFT set up instructions found on https://leanft-help.saas.hpe.com/en/14.01/HelpCenter/Content/HowTo/Sel_LeanFT4SelT.htm


For adding to a local .m2 cache when these jars are not on your internal maven repo, I use the following lines either from a command line or from with in the terminal window (in my case the IntelliJ Terminal window)

### Windows
mvn install:install-file -Dfile="C:\Program Files (x86)\HP\Unified Functional Testing\Selenium SDK\Java\com.hpe.lft.selenium.jar" -DgroupId=com.hpe.lft -DartifactId=selenium-sdk -Dversion=14.3.0 -Dpackaging=jar

mvn install:install-file -Dfile="C:\Program Files (x86)\HP\Unified Functional Testing\Selenium SDK\Java\com.hpe.lft.selenium-javadoc.jar" -DgroupId=com.hpe.lft -DartifactId=selenium-sdk -Dversion=14.3.0 -Dpackaging=jar -Dclassifier=Javadoc

### Unix
mvn install:install-file -Dfile="/opt/leanft/selenium-sdk/Java/com.hpe.lft.selenium.jar" -DgroupId=com.hpe.lft -DartifactId=selenium-sdk -Dversion=14.3.0 -Dpackaging=jar

mvn install:install-file -Dfile="/opt/leanft/selenium-sdk/Java/com.hpe.lft.selenium-javadoc.jar" -DgroupId=com.hpe.lft -DartifactId=selenium-sdk -Dversion=14.3.0 -Dpackaging=jar -Dclassifier=Javadoc

This sample script uses the LeanFT for Selenium extentions that ship with LeanFT.  I have placed the open source version of the extentions in the pom file and commented them out for people to see.

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
        <se.lft.ver>14.3.0</se.lft.ver>
        <lft.ver>14.3.0</lft.ver>
```

### getDrivers.sh
The getDrivers.sh is a small utility to aid in getting the selenium driver required for this script.  This script was created to use the Selenium Chrome driver and as of the time of this writing, the driver 2.37 was the last driver version tested to work.

The script assumes your chromedriver is in the Lft4Se/<VER> folder.  If you will be placing it in a folder other than that, then you will need to edit the test.

To use the getDriver.sh, cd to the Lft4Se folder and then run (enter a different version for the chrome driver if you do not want to use 2.37):

```
./getDrivers.sh 2.37
```
You can also add the chromedriver to your system path and the script will work.

This script was last updated and verified using the chromedriver 2.37

If you are using the IntelliJ container to run this test, you will need to perform the above steps inside the IntelliJ container.

#### Note for execution from command line
java -cp .;junit-4.12.jar;presales-1.0-SNAPSHOT.jar;hamcrest-core-1.3.jar;selenium-server-standalone-2.53.1.jar;com.hpe.lft.selenium.jar org.junit.runner.JUnitCore SeleniumTest

The above assumes:
- you have done a maven deploy goal
- all the relavant *.jar files are in the same directory
- to get junit.jar and hamcrest-core.jar if not on your system already https://github.com/junit-team/junit4/wiki/Download-and-Install

