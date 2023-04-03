package io.cucumber.skeleton;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import supplementler.base.DriverManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static base.ProjectMethods.*;
import static objectRepository.AdminOR.*;
import static objectRepository.AdressAddOR.*;
import static objectRepository.BasketOR.SEPET_TOPLAM_TUTAR_MWEB_V2;
import static objectRepository.GeneralOR.*;
import static objectRepository.HomePageOR.*;
import static objectRepository.LoginPageOR.*;
import static objectRepository.MailOR.*;
import static objectRepository.ProductDetailOR.*;
import static objectRepository.ProductFindOR.*;
import static objectRepository.SiparisAlindiOR.*;
import static objectRepository.UserInfoOR.*;
import static supplementler.utils.DataManager.getData;
import static supplementler.utils.Helper.sleepInSeconds;
import static supplementler.utils.Helper.sleepMs;
import static supplementler.utils.UrlManager.getTestUrl;

public class StepDefinitionsAdmin {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsAdmin.class);
    public String activeEnvironment;
    public String discountName;
    HashMap<String, String> myMap = new HashMap<>();

    @When("select {string} page in top menu")
    public void selectPageInTopMenu(String categoryName) {
        clickTopMenu(categoryName);
        LOGGER.info("user is redirect to " + categoryName + " page");
    }


    @Then("{string} text is on the product list title")
    public void textIsOnTheProductListTitle(String title) {
        Assert.assertTrue(elementVisibiltyWithSize(By.xpath("//h1[normalize-space()='" + title + "']")));
    }


    @And("search {string} user in list with email")
    public void searchUserInListWithEmail(String mail) {
        enterText(SEARCH_EMAIL, mail);
        click(SEARCH_BUTTON_CUSTOMER_PAGE);
        Assert.assertTrue(elementVisibiltyWithSize(By.xpath("(//a[contains(text(),'"+mail+"')])[2]")));
        LOGGER.info("The right user listened.");
    }

    @And("click {string} in customer page")
    public void clickInCustomerPage(String button) {
        click(By.xpath("//a[normalize-space()='" + button + "']"));
        Assert.assertTrue(elementVisibiltyWithSize(MUSTERI_BILGILERI_TAB));
    }

    @And("click {string} in customer detail")
    public void clickInCustomerDetail(String tab) {
        click(By.xpath("//a[contains(text(),'" + tab + "')]"));
    }

    @When("select {string} checkbox in list")
    public void selectCheckboxInList(String roleCode) {
        click(By.xpath("//input[@value='" + roleCode + "']"));
    }

    @And("click KAYDET BUTTON in customer detail")
    public void clickKAYDETBUTTONInCustomerDetail() {
        click(KAYDET_CUSTOMER_DETAIL);
    }

    @And("customer has the role {string}")
    public void customerHasTheRole(String role) {
        String roles = getText(CUSTOMER_ROLES_TAB);
        Assert.assertTrue(checkStringContains(roles, role));

    }

    @And("remove customer {string} role and check {string}")
    public void removeCustomerRoleAncCheck(String roleCode, String role) {
        //Role kaldırma işlemini tek methodda yapar.
        click(DUZENLE_BUTTON_CUSTOMER_LIST);
        click(MUSTERI_ROLLERI_BUTTON_CUSTOMER_DETAIL);
        click(By.xpath("//input[@value='" + roleCode + "']"));
        click(KAYDET_CUSTOMER_DETAIL);
        String roles = getText(CUSTOMER_ROLES_TAB);
        Assert.assertFalse(checkStringContains(roles, role));
        LOGGER.info("The test user's role has been cleared.");
    }


    @And("Enter {string} in the AYAR ADI textbox")
    public void enterInTheAYARADITextbox(String ayar) {
        enterText(AYAR_ADI_TEXTBOX, ayar);
    }

    @And("click search button")
    public void clickSearchButton() {
        click(SEARCH_BUTTON);
    }

    @Then("check that the value is {string}")
    public void checkThatTheValueIs(String limit) {
        int size = getListSize(By.xpath("(//tbody)[3]"));
        LOGGER.info("List size is :" + size);
        Assert.assertEquals(size, 1);
        Assert.assertTrue(elementVisibiltyWithSize(By.xpath("//td[normalize-space()='" + limit + "']")));
    }


    @And("click {string} in categories page")
    public void clickInCategoriesPage(String button) {
        click(By.xpath("(//a[@class='t-button t-grid-edit'][normalize-space()='" + button + "'])[1]"));
    }

    @Then("MARKA ADI field update status is {string}")
    public void fieldUpdateStatusIs(String status) {
        if (status.equals("true")) {
            Assert.assertTrue(elementVisibiltyWithSize(MARKA_FIELD_FIRST_ITEM));
            LOGGER.info("The brand name field can be updated.");
        } else if (status.equals("false")) {
            Assert.assertFalse(elementVisibiltyWithSize(MARKA_FIELD_FIRST_ITEM));
            LOGGER.info("The brand name field cant be updated.");
        }

    }


    @And("check discount supplementler-nestle in {string}")
    public void checkDiscountSupplementlerNestle(String base) {
        String[] role = {"sitesettings.monthlydiscountamount", "sitesettings.nestlemonthlydiscountamount", "sitesettings.yearlydiscountamount", "sitesettings.nestleyearlydiscountamount"};
        String[] discounts = {"2500", "2500", "25000", "25000"};

        for (int i = 0; i < discounts.length; i++) {
            pageLoad(getTestUrl(base) + getData("url.TümAyarlar"));
            enterText(AYAR_ADI_TEXTBOX, role[i]);
            click(SEARCH_BUTTON);
            checkThatTheValueIs(discounts[i]);
        }

    }

    @And("check discount count supplementler-nestle in {string}")
    public void checkDiscountCountSupplementlerNestle(String base) {
        String[] role = {"sitesettings.monthlydiscountcount", "sitesettings.nestlemonthlydiscountcount", "sitesettings.yearlydiscountcount", "sitesettings.nestleyearlydiscountcount"};
        String[] discounts = {"2", "2", "10", "10"};

        for (int i = 0; i < discounts.length; i++) {
            pageLoad(getTestUrl(base) + getData("url.TümAyarlar"));
            enterText(AYAR_ADI_TEXTBOX, role[i]);
            click(SEARCH_BUTTON);
            checkThatTheValueIs(discounts[i]);
        }

        //@Then("check tahat Admin Area manage only orders")
        //public void checkadminareamanageonlyorders() {
        //Assert.assertTrue(elementVisibiltyWithSize(Admin_Area_Manage_Only_Orders));
        //}

    }

    @And("click YENİ EKLE button")
    public void clickYENİEKLEButton() {
        click(ADMIN_INDIRIMLER_YENI_EKLE_BUTTON);
    }

    @And("create discount {string}, {string}, with coupon code {string}, {string}")
    public void createDiscountWithCouponCode(String name, String discountType, String status, String customerID) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String formattedDateTime = now.format(formatter);
        discountName=name+formattedDateTime;


        enterText(ADMIN_INDIRIMLER_NAME_TEXTBOX, discountName);

        //Yüzdelik veya fiyat indirimi seçer, indirim miktarlını girer
        if (discountType.equals("yüzdelik")) {
            click(ADMIN_INDIRIMLER_TUR_YUZDELIK_CHECKBOX);
            enterText(ADMIN_INDIRIMLER_TUR_YUZDELIK_TEXTBOX, "10");
        } else if (discountType.equals("fiyat")) {
            click(ADMIN_INDIRIMLER_TUR_FIYAT_CHECKBOX);
            enterText(ADMIN_INDIRIMLER_TUR_FIYAT_TEXTBOX, "100");
        }

        //Kupon kodu true ise kod belirler
        if (status.equals("true")) {
            click(ADMIN_INDIRIMLER_KUPON_KODU_GEREKTIRIR_CHECKBOX);
            enterText(ADMIN_INDIRIMLER_KUPON_KODU_TEXTBOX, "TEST AUTOMATION");
        }

        enterText(ADMIN_INDIRIMLER_MUSTERI_ID_TEXTBOX, getData("customers_id." + customerID + ""));

        click(ADMIN_KAYDET_VE_DEVAM_ET_BUTTON);
        myMap.put(name + "Url", getTabUrl());

    }

    @And("add Belirtilen üründen sepette {string} bulunması koşulu - {string}")
    public void discountBelirtilenÜründenSepetteBulunmasıKoşuluAdd(String count, String prdocutID) {
        String urlCheck = checkStore(getTabUrl());
        // String indirimName = getText(ADMIN_INDIRIMLER_INDIRIM_NAME_TEXT);
        // String indirimType = indirimName.substring(indirimName.indexOf("TEST AUTOMATION ") + "TEST AUTOMATION ".length(), indirimName.indexOf("(") - 1);
        click(ADMIN_INDIRIMLER_INDIRIM_KISITLAMALARI_TAB);
        click(ADMIN_INDIRIMLER_REQUIREMENT_TYPE);
        click(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU);

        sleepMs(3);
        Assert.assertTrue(elementVisibiltyWithSize(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_ID_TEXTBOX), "Requirement type product id text box'ı görülmedi.");
        Assert.assertTrue(elementVisibiltyWithSize(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_QUANTITY_TEXTBOX), "Requirement type product id text box'ı görülmedi.");


        if (urlCheck.equals("supplementler")) {
            enterText(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_ID_TEXTBOX, getData("test_product.indirim_" + prdocutID + ""));
        } else if (urlCheck.equals("vitaminler")) {
            enterText(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_ID_TEXTBOX, getData("test_product.indirim_" + prdocutID + ""));
        } else if (urlCheck.equals("fitmoda")) {
            enterText(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_ID_TEXTBOX, getData("test_product.indirim_" + prdocutID + ""));
        }

        enterText(ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_QUANTITY_TEXTBOX, count);
        click(ADMIN_INDIRIMLER_REQUIREMENT_KAYDET_BUTTON);

    }

    @And("logout admin panel")
    public void logoutAdminPanel() {
        click(ADMIN_CIKIS_BUTTON);
    }

    @And("delete discount {string}")
    public void deleteDiscount(String name) {


        pageLoad(myMap.get(name + "Url"));
        LOGGER.info("İndirim Adı: " + getText(ADMIN_INDIRIMLER_INDIRIM_NAME_TEXT));

        Assert.assertEquals(getText(ADMIN_INDIRIMLER_INDIRIM_NAME_TEXT), "Indirim Detayları Düzenle - " + discountName + " (Geri Dön)");
        click(ADMIN_INDIRIMLER_SIL_BUTTON);
        click(ADMIN_INDIRIMLER_SIL_POPUP_BUTTON);
        Assert.assertFalse(elementVisibiltyWithSize(By.xpath("//td[normalize-space()='" + discountName + "']")), "İndirim silinemedi.");


    }


    @And("landing page status change {string}")
    public void landingPageStatusChange(String type) {

        getChecked(ADMIN_LANDING_PAGE_AKTIF_CHECKBOX,type);
    }

    @And("click KAYDET VE DEVAM ET button")
    public void clickKAYDETVEDEVAMETButton() {
        click(ADMIN_KAYDET_VE_DEVAM_ET_BUTTON);
    }

    @And("click landing page link")
    public void clickLandingPageLink() {

        sleepInSeconds(5);
        click(ADMIN_LANDING_PAGE_LINK);
    }


    @And("landing page Filtreleme-Sıralama type change {string}")
    public void landingPageFiltrelemeSıralamaTypeChange(String type) {

        getChecked(ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX,type);
       /*
        if (type.equals("inaktif")) {
            if (getValue(ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX_INPUT).equals("false")) {
                click(ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX_INPUT);
            }

        } else if (type.equals("aktif")) {
            if (getValue(ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX_INPUT).equals("true")) {
                click(ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX_INPUT);
            }
        }

        */
    }

    @And("Hemen Bitir Click and expiry date check")
    public void hemenBitirClickAndExpiryDateCheck() {

        click(ADMIN_INDIRIMLER_HEMEN_BITIR_BUTTON);
        sleepInSeconds(2);
        DriverManager.getDriver().switchTo().alert().accept();
        sleepInSeconds(2);
        DriverManager.getDriver().switchTo().alert().accept();

        int i=getValue(ADMIN_INDIRIMLER_AND_DATE).length();
        Assert.assertEquals(i,9,"Bitiş Tarihi alanına değer atanmadığı görüldü.");
        sleepInSeconds(2);
        click(ADMIN_INDIRIMLER_DELETE);
        sleepInSeconds(2);
        click(ADMIN_INDIRIMLER_DELETE_ALLERT_SIL);
        sleepInSeconds(2);
        elementVisibilty(ADMIN_INDIRIMLER_PAGE_TITLE);
    }

    @And("click META ALANI")
    public void clickMETAALANI() {
        click(By.id("MetaDescription"));
    }
}
