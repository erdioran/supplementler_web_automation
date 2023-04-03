package io.cucumber.skeleton;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import static base.AutomationMethods.*;
import static objectRepository.AdminOR.*;
import static objectRepository.GeneralOR.*;
import static objectRepository.HomePageOR.*;
import static objectRepository.LoginPageOR.*;
import static objectRepository.AdressAddOR.*;
import static objectRepository.MailOR.*;
import static objectRepository.ProductDetailOR.*;
import static objectRepository.ProductFindOR.*;
import static objectRepository.SiparisAlindiOR.*;
import static objectRepository.UserInfoOR.*;
import static supplementler.utils.DataManager.getData;
import static supplementler.utils.Helper.*;

public class StepDefinitionsHomePage {
    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsHomePage.class);

    @When("click TEST")
    public void clickFITTEST() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler")) {
            click(TOP_BAR_FIT_TEST_SUPPLEMENTLER);
        } else if (urlCheck.equals("vitaminler")) {
            click(TOP_BAR_KISISEL_TAVSIYE_VITAMINLER);
        }

    }

    @And("verify the descriptions")
    public void verifyTheDescriptions() {
        scrollToElementCenter(BOTTOM_DEVAMINI_OKU_MWEB);
        Assert.assertEquals(getText(BOTTOM_DESCRIPTION_TITLE_MWEB), "Sağlıklı ve Zinde Bir Vücut İçin En İyi Vitaminler");
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_1_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_2_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_3_MWEB));
        click(BOTTOM_DEVAMINI_OKU_MWEB);
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_4_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_5_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_6_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_7_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_8_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_9_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_10_MWEB));
        Assert.assertTrue(elementVisibilty(BOTTOM_DESCRIPTION_PARAGRAPH_11_MWEB));
        scrollToElementCenter(BOTTOM_DESCRIPTION_KAPAT_MWEB);
        click(BOTTOM_DESCRIPTION_KAPAT_MWEB);

    }


    @And("go to KVKK page and verfy the text mWeb")
    public void goToKVKKPageAndVerfyTheTextMWeb() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            scrollToElementCenter(BOTTOM_KISISEL_VERILERIN_KORUNMASI_WEB);
            click(BOTTOM_KISISEL_VERILERIN_KORUNMASI_WEB);
            Assert.assertTrue(elementVisibilty(AYDINLATMA_METNI_WRAPPER));
            Assert.assertEquals(getText(AYDINLATMA_METNI_UYELIK_SURECLERI_TITLE_WEB), "ÜYELİK SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_SIPARIS_IADE_SURECLERI_TITLE_WEB), "SİPARİŞ SÜREÇLERİ VE İADE SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_MUHASEBE_FINANS_SURECLERI_TITLE_WEB), "MUHASEBE VE FİNANS SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_ILETISIM_SURECLERI_TITLE_WEB), "İLETİŞİM SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_MUSTERI_MEMNUNIYETI_SURECLERI_TITLE_WEB), "MÜŞTERİ MEMNUNİYETİ SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_HUKUKI_SURECLER_TITLE_WEB), "HUKUKİ SÜREÇLER");
        } else if (urlCheck.equals("fitmoda")) {
            scrollToElementCenter(BOTTOM_KISISEL_VERILERIN_KORUNMASI_WEB_FITMODA);
            click(BOTTOM_KISISEL_VERILERIN_KORUNMASI_WEB_FITMODA);
            Assert.assertTrue(elementVisibilty(AYDINLATMA_METNI_WRAPPER_FITMODA));
            Assert.assertEquals(getText(AYDINLATMA_METNI_UYELIK_SURECLERI_TITLE_WEB_FITMODA), "ÜYELİK SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_SIPARIS_IADE_SURECLERI_TITLE_WEB_FITMODA), "SİPARİŞ SÜREÇLERİ VE İADE SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_MUHASEBE_FINANS_SURECLERI_TITLE_WEB_FITMODA), "MUHASEBE VE FİNANS SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_ILETISIM_SURECLERI_TITLE_WEB_FITMODA), "İLETİŞİM SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_MUSTERI_MEMNUNIYETI_SURECLERI_TITLE_WEB_FITMODA), "MÜŞTERİ MEMNUNİYETİ SÜREÇLERİ");
            Assert.assertEquals(getText(AYDINLATMA_METNI_HUKUKI_SURECLER_TITLE_WEB_FITMODA), "HUKUKİ SÜREÇLER");
        }

        Assert.assertEquals(getText(AYDINLATMA_METNI_TITLE_WEB), "Aydınlatma Metni");
        Assert.assertEquals(getText(AYDINLATMA_METNI_DIKEY_VITAMIN_TEXT_WEB), "Dikey Vitamin Kozmetik ve Gıda Takviyeleri Pazarlama Ticaret Anonim Şirketi");
        Assert.assertEquals(getText(AYDINLATMA_METNI_KVKK_EMAIL_TITLE_WEB), "kvkk@supplementler.com");


    }

    @And("click İLETİŞİM in bottom")
    public void clickİLETİŞİMInBottom() {

        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("fitmoda")) {
            scrollToElementCenter(BOTTOM_ILETISIM_MWEB_FITMODA);
            click(BOTTOM_ILETISIM_MWEB_FITMODA); // Fitmoda İletişim formu şu an yok
        } else {
            scrollToElementCenter(BOTTOM_ILETISIM_MWEB);
            click(BOTTOM_ILETISIM_MWEB);

        }
        Assert.assertTrue(elementVisibiltyWithSize(ILETISIM_PAGE_ILETISIM_FORM_MWEB));
    }

    @And("fill out the contact form")
    public void fillOutTheContactForm() {
        enterText(ILETISIM_PAGE_ILETISIM_FORM_ADINIZ_SOYADINIZ_MWEB, "Test Üye");
        enterText(ILETISIM_PAGE_ILETISIM_FORM_EMAIL_MWEB, getData("userData.emailUser1"));
        click(ILETISIM_PAGE_ILETISIM_FORM_KONU_SIPARISIM_HAKKINDA_MWEB);
        enterText(ILETISIM_PAGE_ILETISIM_FORM_TELEFON_MWEB, "5375778414");
        enterText(ILETISIM_PAGE_ILETISIM_FORM_SIPARIS_NUMARANIZ_MWEB,"123456789");
        enterText(ILETISIM_PAGE_ILETISIM_FORM_KONU_BASLIGI_MWEB,"TEST FORM");
        enterText(ILETISIM_PAGE_ILETISIM_FORM_MESAJINIZ_MWEB,"TEST MESAJIDIR DIKKATE ALMAYIN");
    }

    @And("click KVKK checkbox in ILETISIM page")
    public void clickKVKKCheckboxInIletisimPage() {
        scrollToElementCenter(ILETISIM_PAGE_KVKK_CHEKBOX_MWEB);
        click(ILETISIM_PAGE_KVKK_CHEKBOX_MWEB);
    }

    @And("click GONDER button in ILETISIM page")
    public void clickGONDERButtonInILETISIMPage() {
        scrollToElementCenter(ILETISIM_PAGE_GONDER_BUTTON_MWEB);
        click(ILETISIM_PAGE_GONDER_BUTTON_MWEB);
    }

    @And("form submitted page check")
    public void formSubmittedPageCheck() {
        Assert.assertTrue(elementVisibiltyWithSize(ILETISIM_PAGE_FORM_GONDERILDI_ANASAYFA_BUTTON));
        Assert.assertEquals(getText(ILETISIM_PAGE_FORM_GONDERILDI_TEXT),getData("textControl.iletisimFormuBasarili"));
    }

    @And("close COOKIE BAR")
    public void closeCOOKIEBAR() {
        if (elementVisibiltyWithSize(COOKIE_BAR_CLOSE)){
            click(COOKIE_BAR_CLOSE);
        }
    }

    @And("click top menu mWeb")
    public void clickTopMenuMWeb() {
        click(TOP_MENU_BUTTON);
    }

    @And("click on Kisisel Tavsiye")
    public void clickOnKisiselTavsiye() {
        click(TOP_MENU_KISISEL_TAVSIYE);
    }

    @And("click TESTE BASLA button")
    public void clickTESTEBASLAButton() {
        click(TOP_MENU_KISISEL_TAVSIYE_TESTE_BASLA_BUTTON);
    }

    @And("select {string}")
    public void selectSex(String sex) {

        if(sex.equals("ERKEK")){
            click(TOP_MENU_KISISEL_TAVSIYE_CINSIYET_ERKEK);
        }else if (sex.equals("KADIN")){
            click(TOP_MENU_KISISEL_TAVSIYE_CINSIYET_KADIN);
        }

        click(TOP_MENU_KISISEL_TAVSIYE_SONRAKI_BUTTON);
    }


    @And("select eating and drinking habits")
    public void selectEatingAndDrinkingHabits() {
        click(TOP_MENU_KISISEL_TAVSIYE_MEYVE_AZHIC_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_ALKOL_AZHIC_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_SIGARA_AZHIC_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_SONRAKI_BUTTON);
    }


    @And("click URUNLERI GOSTER button and verify the result")
    public void clickURUNLERIGOSTERButtonAndVerifyTheResult() {
        click(TOP_MENU_KISISEL_TAVSIYE_URUNLER_GOSTER_BUTTON);
        sleepInSeconds(2);
        Assert.assertTrue(elementVisibiltyWithSize(GENERAL_FIRST_PRODUCT_IN_LIST));
    }


    @And("select age {string} for young")
    public void selectAgeRangeForYoung(String secenek) {

        click(By.xpath("(//a[@class='age-range quiz-btn flat'])["+secenek+"]"));
        click(TOP_MENU_KISISEL_TAVSIYE_SONRAKI_BUTTON);

    }

    @And("select eating and drinking habits for man")
    public void selectEatingAndDrinkingHabitsForMan() {
        click(TOP_MENU_KISISEL_TAVSIYE_MEYVE_COK_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_ALKOL_AZHIC_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_SIGARA_COK_BUTTON);
        click(TOP_MENU_KISISEL_TAVSIYE_SONRAKI_BUTTON);
    }

    @And("select your health {string}")
    public void selectYourHealthProblems(String problem) {

        click(By.xpath("//a[contains(text(),'" + problem + "')]"));
        click(TOP_MENU_KISISEL_TAVSIYE_SONRAKI_BUTTON);
    }

    @And("enter {string} into Search Bar mWeb")
    public void enterIntoSearchBarMWeb(String text) {
        enterText(HOME_PAGE_SEARCH_BAR,text);
        keyboardEnter();

    }


    @And("click Kategoriler")
    public void clickKategoriler() {
        click(TOP_MENU_KATEGORILER);
    }

    @And("click Kolajen and verify the sub titles")
    public void clickKolajenAndVerifyTheSubTitles() {
        click(TOP_MENU_KATEGORILER_KOLOJEN);
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_KOLOJEN_TIP1),"Tip I-III Kolajen");
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_KOLOJEN_TIP2),"Tip II Kolajen");
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_BALIK_KOLOJENI),"Balık (Marine) Kolajeni");
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_HYALURONIK_ASIT),"Hyalüronik Asit (Hyaluronic Acid)");
        LOGGER.info("Kategoriler altındaki alt başlıklar kontrol edildi.");
    }

    @And("click Tip I-III Kolajen and verify the page name and the products")
    public void clickTipIIIIKolajenAndVerifyThePageNameAndTheProducts() {
        click(TOP_MENU_KATEGORILER_KOLOJEN_TIP1);
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_ARA_BASLIK_3),"Tip I-III Kolajen");
        Assert.assertTrue(getText(GENERAL_FIRST_PRODUCT_IN_LIST).contains("Collagen"));
        Assert.assertTrue(getText(GENERAL_SECOND_PRODUCT_IN_LIST).contains("Collagen"));
        LOGGER.info("Tip I-III Kolajen başlığında ilk iki ürün başlığında Collagen arandı");
    }




    @And("click {string} and verify the page {string} and the products")
    public void clickAndVerifyThePageAndTheProducts(String title, String name) {
        click(By.xpath("//a[@title='"+title+"']"));
        Assert.assertEquals(getText(TOP_MENU_KATEGORILER_ARA_BASLIK_3),name);
        Assert.assertTrue(elementVisibiltyWithSize(GENERAL_FIRST_PRODUCT_IN_LIST));
        Assert.assertTrue(elementVisibiltyWithSize(GENERAL_SECOND_PRODUCT_IN_LIST));
        LOGGER.info("Başlıktaki ilk iki ürünün varlığı kontrol edildi");

    }

    @And("click {string} and verify the sub titles")
    public void clickAndVerifyTheSubTitles(String category) {
        click(By.xpath("//span[normalize-space()='"+category+"']"));


    }

    @And("click sepete ekle")
    public void clickSepeteEkle() {
        scrollToElementCenter(SEPETE_EKLE);
        click(SEPETE_EKLE);
        sleepInSeconds(10);
    }

    @And("search for hardline in the search bar")
    public void searchForHardlineInTheSearchBar() {
        enterText(HOME_PAGE_SEARCH_BAR,"hardline");
        click(HOME_PAGE_SEARCH_BAR_ARA_BUTTON);

    }


}
