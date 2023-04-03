package io.cucumber.skeleton;

import com.github.javafaker.Faker;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import supplementler.base.DriverManager;
import supplementler.utils.DataManager;

import static base.AutomationMethods.checkStringContains;
import static base.ProjectMethods.*;
import static objectRepository.AdminOR.*;
import static objectRepository.BasketOR.*;
import static objectRepository.GeneralOR.*;
import static objectRepository.HomePageOR.*;
import static objectRepository.AdressAddOR.*;
import static objectRepository.LoginPageOR.*;
import static objectRepository.MailOR.*;
import static objectRepository.ProductDetailOR.*;
import static objectRepository.ProductFindOR.*;
import static objectRepository.SiparisAlindiOR.*;
import static objectRepository.UserInfoOR.*;
import static supplementler.utils.DataManager.getData;
import static supplementler.utils.Helper.sleepInSeconds;
import static supplementler.utils.UrlManager.getTestUrl;

public class StepDefinitionsGeneral {

    Faker faker = new Faker();

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsGeneral.class);

    @Given("open login page in {string}")
    public void loginPage(String base) {

        pageLoad(getTestUrl(base) + getData("url.login"));
        LOGGER.info("user is on login page " + base);
    }

    @Given("load home page in {string}")
    public void homePage(String base) {
        pageLoad(getTestUrl(base));
        LOGGER.info("user is on home page " + base);
    }

    @Given("login with {string} user")
    public void loginWithTestUser(String type) {
        login(getData("userData.email" + type + ""), getData("userData.passwordTestUser"));
        LOGGER.info("user is login with : " + type + " user");
    }

    @Given("login with {string} user mweb")
    public void loginWithTestUserMweb(String type) {
        loginMweb(getData("userData.email" + type + ""), getData("userData.passwordTestUser"));
        //loginMweb("sefkanguven@supplementler.com","123456QWE");
        String tabUrl=getTabUrl();
        Assert.assertTrue(checkStringContains(tabUrl,getData("url.mwebLogin")));
        LOGGER.info("user is login with : " + type + " user");
    }

    @Given("login with {string} user mweb for ad soyad")
    public void loginWithTestUserMwebForAdSoyad(String type) {
        loginMweb(getData("userData.emailAdSoyad"), getData("userData.adSoyadPassword"));
        String tabUrl=getTabUrl();
        Assert.assertTrue(checkStringContains(tabUrl,getData("url.mwebLogin")));
        LOGGER.info("user is login with : " + type + " user");
    }

    @And("load {string} page in {string}")
    public void loadPageWithData(String page, String base) {
        pageLoad(getTestUrl(base) + getData("url." + page + ""));
        sleepInSeconds(10);
    }


    @Given("load {string} url in {string}")
    public void loadPageUrl(String url, String site) {
        pageLoad(getTestUrl(site) + getData("testUrl." + url + ""));
    }


    @Given("open admin page in {string}")
    public void openAdminPage(String base) {
        pageLoad(getTestUrl(base) + getData("url.admin"));
        LOGGER.info("Environment is: " + base);
    }

    @And("wait {int} second")
    public void waitMinMin(int second) {
        sleepInSeconds(second);
    }


    @Then("check test popup close icon visibility")
    public void checkTestPopupCloseIconVisibility() {
        elementVisibiltyWithSize(FIT_TEST_POPUP_CLOSE_ICON);
    }

    @And("check tab url is {string} in {string}")
    public void checkTabUrlIs(String url, String base) {
        String currenturl = getTabUrl();
        System.out.println("current url:" + currenturl);
        String testurl = getTestUrl(base);
        Assert.assertEquals(currenturl, testurl + url);
    }

    @And("login with {string} user web")
    public void loginWithUserWeb(String type) {
        loginMweb(getData("userData.email" + type + ""), getData("userData.password" + type + ""));
        String tabUrl=getTabUrl();
        Assert.assertTrue(checkStringContains(tabUrl,getData("url.mwebLogin")));
        LOGGER.info("user is login with : " + type + " user");
    }

    @And("go to my account mWeb")
    public void goToMyAccountmWeb() {
        click(TOP_HESABIM_ICON_MWEB);
        click(TOP_KULLANICI_BILGILERIM_BUTTON_MWEB);
    }

    @And("update user info mWeb")
    public void updateUserInfoMweb() {

       userInfoUpdateMweb();
    }


    @And("logout site")
    public void logoutSite() {
        click(TOP_BAR_KULLANICI_ADI);
        click(TOP_BAR_LOGOUT_WEB);
    }

    @And("login with {string} user popup")
    public void loginWithUserPopup(String type) {
        loginPopup(getData("userData.email" + type + ""), getData("userData.passwordTestUser"));
        //loginPopup("sefkanguven@supplementler.com","123456QWE");
        LOGGER.info("user is login with : " + type + " user");
    }

    @And("HEADER CART click and verify the cart null")
    public void titleCartClickAndVerifyTheCartNull() {
        click(TOP_SEPET_ICON);
        LOGGER.info("header cart click");
        Assert.assertEquals(getText(BASKET_NULL_DESCRIPTION), getData("textControl.sepetBosBilgilendirmeText"));
        LOGGER.info("cart empty description check");
    }


    @And("go to previous page")
    public void goToPreviousPage() {
        DriverManager.getDriver().navigate().back();
    }  

    @And("check that the page is not displayed")
    public void checkThatThePageIsNotDisplayed() {
        boolean urlCheck = checkStringContains(getTabUrl(), "www");
        LOGGER.info("Boolen değer : "+urlCheck);
        if (urlCheck) {
            Assert.assertEquals(getText(PAGE_STATUS),"404","Sayfa görüntülendi. 404 alması bekleniyordu");
            LOGGER.info("Web error control");
        } else if(!urlCheck){

            Assert.assertEquals(getText(PAGE_STATUS),"404","Sayfa görüntülendi. 404 alması bekleniyordu");

            LOGGER.info("mweb error control");
        }
    }

    @And("switch tab")
    public void switchTabTest() {
        switchToTab();
    }

    @And("close tab")
    public void closeTabTest() {
        closeTab();
    }


    @And("wait {int}")
    public void wait(int second) {
        sleepInSeconds(second);
    }

    @And("close tabs other than the main tab")
    public void closeTabsOtherThanTheMainTab() {
        closeTabOtherThanTheMainTab();

    }

    @And("convert web to mweb")
    public void convertWebToMweb() {
        String urlCheck=getTabUrl();
        String updateUrl=urlCheck.replace("www","m");
        LOGGER.info("URL Check : "+updateUrl);
        pageLoad(updateUrl);
        sleepInSeconds(5);
    }
}
