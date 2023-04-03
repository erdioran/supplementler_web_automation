package io.cucumber.skeleton;


import base.AutomationMethods;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import supplementler.base.DriverManager;
import supplementler.utils.ConfigManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static supplementler.utils.Helper.deleteOldTestReportFiles;


@CucumberOptions(
        features = "@target/failedrerun.txt",
        glue = "io.cucumber.skeleton",
        monochrome = true,
        plugin = {"summary", "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/failedrerun.txt"}
)

public class RunnerFailed extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LogManager.getLogger(RunnerFailed.class);

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeMethod
    public void startBrowser(ITestResult result, Method method) throws MalformedURLException {

        DriverManager.launchBrowser(ConfigManager.getBrowser());
    }

    @AfterMethod
    public void closeBrowser(ITestResult result) throws IOException {
       /*
        if (!result.isSuccess() && !ConfigManager.isHeadless()) {
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, (Markup) MediaEntityBuilder.createScreenCaptureFromBase64String(AutomationMethods.getBase64Screenshot()).build());
        }

        */
        DriverManager.quitDriver();
    }


    @BeforeTest(alwaysRun = true)
    public void beforeTestReport() {
    }


    @AfterTest(alwaysRun = true)
    public void afterTestReport() {
/*
        try {
            sendEmail();
        } catch (Exception e) {
            LOGGER.info("The report could not be sent.");
        }


 */

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
