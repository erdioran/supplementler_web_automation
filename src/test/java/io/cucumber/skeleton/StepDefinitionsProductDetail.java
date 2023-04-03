package io.cucumber.skeleton;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import supplementler.base.DriverManager;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static base.AutomationMethods.*;
import static objectRepository.BasketOR.*;
import static objectRepository.GeneralOR.*;
import static objectRepository.ProductDetailOR.*;
import static supplementler.utils.DataManager.getData;
import static supplementler.utils.Helper.sleepInSeconds;

public class StepDefinitionsProductDetail {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsProductDetail.class);
    SoftAssert softAssert = new SoftAssert();
    static String productTitle;
    static String productPrice;
    static String popupUrunAdeti;
    static String popupAromaText;


    @Then("check {string} visibility false")
    public void checkVisibility(String element) {
        By element1 = By.id(element);
        Assert.assertFalse(elementVisibiltyWithSize(element1));
        LOGGER.info(element + " element could not be displayed on the page.");

    }

    @And("scroll down until the for sticky bar")
    public void scrollDownDetayliAciklamaOrFooterCard() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            pageScroollDownToTargetElement(URUN_DETAY_DETAYLI_ACIKLAMA_FIELD);
        } else if (urlCheck.equals("fitmoda")) {
            pageScroollDownToTargetElement(URUN_DETAY_FOOTER_ORJUNAL_URUN_FIELD_FITMODA);
            sleepInSeconds(2);
        }

    }

    @And("scroll down until the FOOTER CARD field")
    public void scrollDownUntilTheFOOTERCARDField() throws InterruptedException {
        pageScroollDownToTargetElement(URUN_DETAY_FOOTER_ORJUNAL_URUN_FIELD_FITMODA);
        Thread.sleep(5000);
    }

    @And("scroll down until the MASAUSTU GORONUM field")
    public void scrollDownUsteCik() {
        pageScroollDownToTargetElement(URUN_DETAY_MASAUSTU_GORUNUM_LINK);
    }


    @Then("check sticky bar visibility {string}")
    public void checkVisibilityStickyBar(String check) {

        if (Objects.equals(check, "true")) {
            Assert.assertTrue(elementVisibiltyWithSize(STICKY_BAR_URUN_DETAY), "Sticky bar görülmedi");
            LOGGER.info("Sticky bar element could not displayed on the page.");
        } else if (Objects.equals(check, "false")) {
            Assert.assertFalse(elementVisibiltyWithSize(STICKY_BAR_URUN_DETAY), "Sticky bar görülmedi");
            LOGGER.info("Sticky bar element could displayed on the page.");
        }
    }

    @And("scroll up until the SEPETE EKLE field")
    public void scrollUpSepeteEkle() {
        pageScroollUpToTargetElement(URUN_DETAY_SEPETE_EKLE_BUTTON_ID);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("check sticky background color is {string} and sticky discount background is {string}")
    public void checkStickyBarBackground(String colorCode, String discountColor) {
        Assert.assertEquals(getData("colorCode." + colorCode + ""), getElementBackgroundColor(STICKY_BAR_URUN_DETAY));
        Assert.assertEquals(getData("colorCode." + discountColor + ""), getElementBackgroundColor(STICKY_BAR_SEPETE_EKLE_BUTTON));
        if (elementVisibilty(STICKY_BAR_INDIRIM_URUN_DETAY)) {
            Assert.assertEquals(getData("colorCode." + discountColor + ""), getElementBackgroundColor(STICKY_BAR_INDIRIM_URUN_DETAY));
        }
        LOGGER.info("Sticky Bar background color is: " + colorCode + " / Sticky Bar discount background color is: " + discountColor + " / Sticky Bar discount background color is: " + discountColor);
    }

    @And("check sticky font colors is {string}")
    public void checkStickyFontColorsIs(String fontColor) {
        Assert.assertEquals(getData("colorCode." + fontColor + ""), getElementFontColor(STICKY_BAR_SEPETE_EKLE_BUTTON));
        if (elementVisibilty(STICKY_BAR_INDIRIM_URUN_DETAY) == true) {
            Assert.assertEquals(getData("colorCode." + fontColor + ""), getElementFontColor(STICKY_BAR_INDIRIMLI_FIYAT));
            Assert.assertEquals(getData("colorCode." + fontColor + ""), getElementFontColor(STICKY_BAR_INDIRIMSIZ_FIYAT));
            Assert.assertEquals(getData("colorCode." + fontColor + ""), getElementFontColor(STICKY_BAR_INDIRIM_URUN_DETAY));
        }

        LOGGER.info("All font color is: " + fontColor);

    }


    @Then("check video location")
    public void checkVideoLocation() {

        String title = getText(URUN_DETAY_VIDEO_POSITION_FITMODA);
        LOGGER.info("video title: " + title);

        Assert.assertTrue(elementVisibiltyWithSize(URUN_DETAY_VIDEO_POSITION_FITMODA), "Ürün detay video doğru konumda değil.");
        Assert.assertTrue(title.length() > 10);

    }

    @And("add product to cart mweb")
    public void addProductToCartMweb() {
        String urlCheck = checkStore(getTabUrl());

        if (!elementVisibiltyWithSize(URUN_DETAY_KOMBINASYONDA_NELER_VAR_FIELD_MWEB)) {
            LOGGER.info("Ürün kombinasyon değil. Test devam ediliyor.");
            click(URUN_DETAY_SEPETE_EKLE_BUTTON);
            if (elementVisibiltyWithSize(URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_MWEB) || elementVisibiltyWithSize(URUN_DETAY_BEDEN_SECINIZ_POPUP_FITMODA)) { //buraya fitmoda beden seç popupı visibility

                sleepInSeconds(2);
                if (urlCheck.equals("supplementler") || (urlCheck.equals("vitaminler"))) {

                    for (int i = 1; i < 4; i++) {
                        String status = getStockStatusMweb(By.xpath("/html/body/div[8]/div/div/div/div/div/div[3]/a[" + i + "]"));
                        LOGGER.info(i + ". choise stock is " + status);
                        if (status.equals("False")) {
                            click(By.xpath("/html/body/div[8]/div/div/div/div/div/div[3]/a[" + i + "]"));
                            LOGGER.info("Aroma veya beden seçildi.");
                            break;
                        } else {
                            i++;
                        }
                    }
                } else if (urlCheck.equals("fitmoda")) {
                    click(URUN_DETAY_BEDEN_SECINIZ_POPUP_FITMODA);
                    for (int i = 1; i < 4; i++) {
                        String status = getStockStatusMweb(By.xpath("//div[@id='flavor-select']/a[" + i + "]"));
                        LOGGER.info(i + ". choise stock is " + status);
                        if (status.equals("False")) {
                            click(By.xpath("//div[@id='flavor-select']/a[" + i + "]"));
                            LOGGER.info("Aroma veya beden seçildi.");
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            if (elementVisibiltyWithSize(URUN_DETAY_HEDIYE_SECIMI_POPUP_MWEB)) {
                if (urlCheck.equals("supplementler") || (urlCheck.equals("vitaminler"))) {
                    if (elementVisibiltyWithSize(By.xpath("/html/body/div[8]/div/div/div/div/div/div[3]/div[1]/a"))) {
                        click(By.xpath("/html/body/div[8]/div/div/div/div/div/div[3]/div[1]/a"));
                    } else {
                        click(By.xpath("//body/div[8]/div/div/div/div/div[3]/div/a"));
                    }

                    LOGGER.info("Random gift seçildi.");

                } else if (urlCheck.equals("fitmoda")) {
                    /////
                }
            }

            sleepInSeconds(2);
            if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                click(UYARI_POPUP_TAMAM_BUTTON);
            } else {
                if (elementVisibiltyWithSize(URUN_DETAY_URUN_SEPETE_EKLENDI_POPUP_MWEB)) {
                    LOGGER.info("Alıverişe devam et popupı görüldü");
                    click(URUN_DETAY_SEPETE_GIT_BUTTON_MWEB);
                } else {
                    click(URUN_DETAY_SEPETE_EKLE_BUTTON);
                    click(URUN_DETAY_SEPETE_GIT_BUTTON_MWEB);
                }
            }
        } else {
            LOGGER.info("Ürün kombinasyon ürün, sepete eklenemiyor.");
        }
    }

    @And("add product to cart web")
    public void addProductToCartWeb() {
        String urlCheck = checkStore(getTabUrl());

        if (!elementVisibiltyWithSize(URUN_DETAY_KOMBINASYONDA_NELER_VAR_FIELD_WEB)) {
            LOGGER.info("Ürün kombinasyon değil. Test devam ediliyor.");
            click(URUN_DETAY_SEPETE_EKLE_BUTTON);
            sleepInSeconds(2);

            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {

                if (elementVisibiltyWithSize(URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE) || elementVisibiltyWithSize(URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_TEST)) {

                    if (elementVisibiltyWithSize(URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE)) {
                        click(By.xpath("//body[1]/div[22]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[1]/strong[1]"));
                    } else {
                        click(By.xpath("//body[1]/div[21]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[1]/strong[1]"));
                    }
                    LOGGER.info("Aroma veya beden seçildi. ");
                    sleepInSeconds(2);
                }
            } else if (urlCheck.equals("fitmoda")) {
                if (elementVisibiltyWithSize(URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_FITMODA)) {

                    click(By.xpath("//body[1]/div[16]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[1]/strong[1]"));
                    LOGGER.info("Aroma veya beden seçildi. ");
                    sleepInSeconds(2);
                }
            }


            if (elementVisibiltyWithSize(URUN_DETAY_HEDIYE_SECIMI_SIZE)) {

                click(By.xpath("(//li[@class='gift-select-option-v2'])[1]"));
                LOGGER.info("Hediye seçildi.");
                sleepInSeconds(2);
            }

            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {

                if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                    click(UYARI_POPUP_TAMAM_BUTTON);
                } else {
                    if (elementVisibiltyWithSize(URUN_DETAY_ALISVERISE_DEVAM_ET_BUTTON)) {
                        click(URUN_DETAY_SATIN_AL_BUTTON);
                    } else if (!elementVisibiltyWithSize(URUN_DETAY_ALISVERISE_DEVAM_ET_BUTTON)) {
                        click(URUN_DETAY_SEPETE_EKLE_BUTTON);
                        click(URUN_DETAY_SATIN_AL_BUTTON);
                    }
                }

            } else if (urlCheck.equals("fitmoda")) {

                if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                    click(UYARI_POPUP_TAMAM_BUTTON);
                } else {
                    if (elementVisibiltyWithSize(URUN_DETAY_ALISVERISE_DEVAM_ET_BUTTON)) {
                        click(URUN_DETAY_SATIN_AL_BUTTON_FITMODA);
                    } else {
                        click(URUN_DETAY_SEPETE_EKLE_BUTTON);
                        click(URUN_DETAY_SATIN_AL_BUTTON_FITMODA);
                    }
                }
            }

        } else {
            LOGGER.info("Ürün kombinasyon ürün, sepete eklenemiyor.");
        }
    }


    @And("click SEPETE EKLE button on the desired product")
    public void clickSEPETEEKLEButtonOnTheDesiredProduct() {
        scrollToElementCenter(By.xpath("//a[normalize-space()='Hardline Whey 3 Matrix 2300 Gr']"));
        WebElement element = DriverManager.getDriver().findElement(By.xpath("//img[@alt='Hardline Whey 3 Matrix 2300 Gr']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).build().perform();
        //sleepInSeconds(2);
        click(DESKTOP_HIZLI_SEPETE_EKLE);
        sleepInSeconds(2);
        Assert.assertTrue(elementVisibiltyWithSize(DESKTOP_HIZLI_SEPETE_EKLE_POPUP));
        LOGGER.info("Hızlı sepete ekle popup ın açıldığı gözlemlendi");
        Assert.assertTrue(elementVisibiltyWithSize(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_STARS));
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_AROMA_SECIMI_TITLE), "Aroma Seçimi");
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_HEDIYE_SECIMI_TITLE), "Hediye Seçimi");
        LOGGER.info(getText(DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_TITLE));
        productTitle = getText(DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_TITLE);
        LOGGER.info(getText(DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_PRICE));
        productPrice = getText(DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_PRICE);


    }

    @And("close the popup")
    public void closeThePopup() {
        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_KAPAT);
        Assert.assertFalse(elementVisibiltyWithSize(DESKTOP_HIZLI_SEPETE_EKLE_POPUP));
        LOGGER.info("Hızlı sepete ekle popup ın kapandığı gözlemlendi");
    }

    @And("click Urun Detayina Git")
    public void clickUrunDetayinaGit() {
        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_URUN_DETAYINA_GIT_BUTTON);
        LOGGER.info(getText(URUN_DETAY_TITLE));
        productTitle = getText(URUN_DETAY_TITLE);
        LOGGER.info(getText(URUN_DETAY_PRICE));
        productPrice = getText(URUN_DETAY_PRICE) + " TL";

    }


    @And("select an aroma")
    public void selectAnAroma() {
        List<WebElement> aromas = DriverManager.getDriver().findElements(By.xpath("//div[@class='Aroma']"));

        int sizeList = getListSize(By.xpath("//div[@class='Aroma']"));

        for (int i = 1; i <= sizeList; i++) {
            By test = By.xpath("(//div[@class='Aroma'])[" + i + "]");
            //String aroma = null;
            if (i <= 5) {
                click(test);
                popupAromaText = getText(test);
                // Assert.assertNotEquals(getText(),"");
                String skt = null;
                skt = getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SKT);
                LOGGER.info("Desktop hızlı sepet eklemede açılan ürünün: " + skt);
                checkSKT(skt);
            } else {
                click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_OK_BUTTON);
                click(By.xpath("(//div[@class='Aroma'])[5]"));
                popupAromaText = getText(By.xpath("(//div[@class='Aroma'])[5]"));
                String skt = null;
                skt = getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SKT);
                LOGGER.info("Desktop hızlı sepet eklemede açılan ürünün: " + skt);
                checkSKT(skt);
            }


        }
    }

    @And("select a gift")
    public void selectAGift() {

        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SELECT_GIFT);
        int lastGift = getListSize(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_GIFT_SIZE);
        LOGGER.info(lastGift);
        click(By.xpath("//div[@class='special-offer-select']//select/option[" + lastGift + "]"));


    }

    @And("select piece of product")
    public void selectPieceOfProduct() {
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_TITLE), "Adet");
        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON), "minus");
        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON), "ant-designplus-circle-outlined");
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS), "1");

        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON);

        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON), "minus-active");
        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON), "ant-designplus-circle-outlined");
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS), "2");

        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON);

        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON), "minus");
        Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON), "ant-designplus-circle-outlined");
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS), "1");

        for (int i = 2; i <= 5; i++) {

            if (i < 5) {
                click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON);
                Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON), "minus-active");
                Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON), "ant-designplus-circle-outlined");
                Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS), "" + i + "");
            } else {
                click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON);
                Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON), "minus-active");
                Assert.assertEquals(getClassValue(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON), "ant-designminus-circle-outlined");
                Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS), "" + i + "");
            }
        }


    }

    @And("click Sepete Ekle on the popup")
    public void clickSepeteEkleOnThePopup() {

        popupUrunAdeti = getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS);
        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SEPETE_EKLE_BUTTON);
        LOGGER.info("Ürün başarıyla sepete eklendi");
        sleepInSeconds(3);
        Assert.assertFalse(elementVisibiltyWithSize(DESKTOP_HIZLI_SEPETE_EKLE_POPUP));

    }

    @And("click Sepete Git on the small popup")
    public void clickSepeteGitOnTheSmallPopup() {
        if (elementVisibiltyWithSize(DESKTOP_HIZLI_SEPETE_EKLE_SEPETE_GIT_SMALL_POPUP_TEXT)) {
            Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_SEPETE_GIT_SMALL_POPUP_TEXT), "Ürününüz başarıyla sepete eklendi.");
            click(DESKTOP_HIZLI_SEPETE_EKLE_SEPETE_GIT_SMALL_POPUP_SEPETE_GIT);
            Assert.assertEquals(getValue(SEPET_URUN_ADETI_WEB), popupUrunAdeti);
            int result = Integer.parseInt(productPrice) * 5;
            String stringPrice = String.valueOf(result);
            Assert.assertEquals(SEPET_ARA_TOPLAM_TEXT, stringPrice);
            Assert.assertEquals(getText(By.xpath("//option[@selected='selected']")), popupAromaText.toUpperCase(Locale.ROOT));

        } else {
            LOGGER.info("Sepete Git popup ı yakalayamadım.");
            click(TOP_SEPET_BUTTON_WEB);
            Assert.assertEquals(getValue(SEPET_URUN_ADETI_WEB), popupUrunAdeti);
            int result = Integer.parseInt(productPrice.substring(0, productPrice.indexOf(" "))) * 5;
            String stringPrice = String.valueOf(result);
            Assert.assertEquals(getText(SEPET_ARA_TOPLAM_TUTAR_WEB), stringPrice + ",00 TL");
            Assert.assertEquals(getText(By.xpath("//option[@selected='selected']")), popupAromaText.toUpperCase(Locale.ROOT));
        }


    }


    @And("click Sepete Ekle on the popup to verify five+ message")
    public void clickSepeteEkleOnThePopupToVerifyFiveMessage() {
        selectAnAroma();
        selectAGift();
        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SEPETE_EKLE_BUTTON);
        Assert.assertEquals(getText(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_5_ADET_MESSAGE), getText(DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_TITLE) + " ürününü maksimum 5 adet satın alabilirsiniz.");
        click(DESKTOP_HIZLI_SEPETE_EKLE_POPUP_5_ADET_MESSAGE_KAPAT);
    }
}
