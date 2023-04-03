package io.cucumber.skeleton;


import base.AutomationMethods;

import org.testng.*;
import org.testng.annotations.*;
import supplementler.base.DriverManager;
import supplementler.utils.ConfigManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.testng.ITestResult;


import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static supplementler.utils.Helper.deleteOldTestReportFiles;


@Test
@Parameters({"tags"})
@CucumberOptions(
        publish = true,
        features = {"src/test/resources/io/cucumber/features/web_case.feature", "src/test/resources/io/cucumber/features/admin_case.feature", "src/test/resources/io/cucumber/features/mweb_case.feature", "src/test/resources/io/cucumber/features/mvitaminler.feature",},
        glue = "io.cucumber.skeleton",
        tags = "@REGRESSION",
        monochrome = true,
        plugin = {"summary", "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/failedrerun.txt"}
)

public class RunCucumberTest extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LogManager.getLogger(RunCucumberTest.class);


    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeMethod
    public void startBrowser(ITestResult result, Method method,ITestContext context) throws MalformedURLException {

        DriverManager.launchBrowser(ConfigManager.getBrowser());

    }


    @AfterMethod
    public void closeBrowser(ITestResult result) {
        /*
        if (!result.isSuccess()) {
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(AutomationMethods.getBase64Screenshot()).build());
        }

         */
        DriverManager.quitDriver();
    }


    @BeforeTest(alwaysRun = true)
    public void beforeTestReport() {

    }


    @AfterTest(alwaysRun = true)
    public void afterTestReport() {


    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite()  {
       deleteOldTestReportFiles();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        DriverManager.quitDriver();
    }
}
