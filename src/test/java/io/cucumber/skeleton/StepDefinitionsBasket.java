package io.cucumber.skeleton;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import supplementler.base.DriverManager;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;

import static base.AutomationMethods.*;
import static base.ProjectMethods.*;
import static objectRepository.AdminOR.*;
import static objectRepository.AdressAddOR.*;
import static objectRepository.BasketOR.*;
import static objectRepository.BasketOR.ODEME_PAGE_KAYITLI_KARTLARIM_BUTTON;
import static objectRepository.GeneralOR.*;
import static objectRepository.HomePageOR.*;
import static objectRepository.LoginPageOR.*;
import static objectRepository.MailOR.*;
import static objectRepository.ProductDetailOR.*;
import static objectRepository.ProductFindOR.*;
import static objectRepository.SiparisAlindiOR.*;
import static objectRepository.UserInfoOR.*;
import static supplementler.utils.ConfigManager.getTestUrlConfig;
import static supplementler.utils.DataManager.*;
import static supplementler.utils.Helper.*;

public class StepDefinitionsBasket {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsBasket.class);
    SoftAssert softAssert = new SoftAssert();
    HashMap<String, String> myMap = new HashMap<>();

    String totalPriceOld;
    String totalPriceNew;

    String secilenTeslimatSaati;


    @And("get total cart amount mweb")
    public void getTotalCartAmountMweb() {
        String totalPriceFull = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        totalPriceOld = totalPriceFull.split(" ")[0];
        LOGGER.info("İndirim öncesi toplam tutar: " + totalPriceOld);
    }

    @When("click KUPON KODU checkbox mweb")
    public void clickKUPONKODUCheckboxMweb() {
        click(SEPET_KUPON_KODU_CHECKBOX_MWEB);
    }

    @Then("check that the {string} alert message appears mweb")
    public void checkThatTheAlertMessageAppearsMweb(String message) {
        String errorMessage = getText(SEPET_HATA_MESAJI_MWEB);
        Assert.assertEquals(errorMessage, getData("errorMessage." + message + ""));
    }


    @And("check total cart amount is same as previous step mweb")
    public void checkTotalCartAmountIsSameAsPreviousStepMweb() {
        String totalPriceFull = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        totalPriceNew = totalPriceFull.split(" ")[0];
        Assert.assertEquals(totalPriceNew, totalPriceOld);
    }

    @And("pass adress page mweb")
    public void passAdressPageMweb() {
        click(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON);
        enterText(ADRES_EKLE_PAGE_CEP_TELEFONU_TEXTBOX, getData("userData.phoneCustomer"));
        click(ADRES_EKLE_PAGE_KAYDET_BUTTON);
        click(ADRES_PAGE_DEVAM_ET_BUTTON);
    }

    @And("add guest {string} address and proceed to checkout mweb")
    public void addGuestAddressAndProceedToCheckoutMweb(String mail) {

        String urlCheck = checkStore(getTabUrl());
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);

        sepetSonrasiHediyeEkraniGecMweb();

        //Üye olmadan devam et buttonunu ardından üye girişi yaparak devam et buttonunun görüldüğünü test eder.
        //    Assert.assertTrue(elementVisibiltyWithSize(LOGIN_PAGE_UYE_OLMADAN_DEVAM_ET_BUTTON), "Üye Olmadan Devam Et buttonu görülmedi.");
        click(LOGIN_PAGE_UYE_OLMADAN_DEVAM_ET_BUTTON);
        sleepInSeconds(2);
        //   Assert.assertTrue(elementVisibiltyWithSize(LOGIN_PAGE_UYE_GIRISI_YAPARAK_DEVAM_ET_BUTTON), "Üye Girişi Yaparak Devam Et buttonu görülmedi.");

        // Guest maili girer
        enterText(LOGIN_PAGE_EMAIL_TEXTBOX_XPATH, getData("userData.email" + mail + ""));

        click(LOGIN_PAGE_SIPARISE_DEVAM_ET_BUTTON_GUEST_MWEB);

        if (elementVisibiltyWithSize(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }


        adressRandomCreateMweb(randomAdressDataGuest(mail));
        DriverManager.getDriver().switchTo().activeElement().sendKeys(Keys.TAB); // Fitmoda otomasyon sorunu için.
        click(ADRES_EKLE_KAYDET_BUTTON);

        sepetNotu("#test#");
        DriverManager.getDriver().switchTo().activeElement().sendKeys(Keys.TAB);


        click(ADRES_PAGE_DEVAM_ET_BUTTON_ID);

        if (urlCheck.equals("fitmoda")) {
            scrollPageToTop();
        }

    }


    @And("complete test order with guest {string} web")
    public void completeTestOrderWithGuestWeb(String mail) {

        String urlCheck = checkStore(getTabUrl());

        click(SEPET_SEPETI_ONAYLA_BUTTON_GUEST);

        if (urlCheck.equals("vitaminler")) {
            sepetSonrasiHediyeEkraniGecWeb();
        }

        Assert.assertTrue(elementVisibiltyWithSize(LOGIN_PAGE_UYE_OLMADAN_DEVAM_ET_BUTTON_WEB), "Üye Olmadan Devam Et buttonu görülmedi.");
        click(LOGIN_PAGE_UYE_OLMADAN_DEVAM_ET_BUTTON_WEB);
        sleepInSeconds(2);
        Assert.assertTrue(elementVisibiltyWithSize(LOGIN_PAGE_UYE_GIRISI_YAPARAK_DEVAM_ET_BUTTON_WEB), "Üye Girişi Yaparak Devam Et buttonu görülmedi.");

        enterText(LOGIN_PAGE_EMAIL_TEXTBOX_XPATH, getData("userData.email" + mail + ""));

        click(LOGIN_PAGE_SIPARISE_DEVAM_ET_BUTTON_GUEST_WEB);

      //  click(SEPET_SATIN_AL_BUTTON_WEB_ID);


      //  sepetSonrasiHediyeEkraniGecWeb();

        if (elementVisibiltyWithSize(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }

        sepetNotuWeb("#test#");

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(SEPET_DEVAM_ET_BUTTON);
        } else if (urlCheck.equals("fitmoda")) {
            click(SEPET_DEVAM_ET_BUTTON_FITMODA);
        }

        Assert.assertTrue(elementVisibiltyWithSize(SEPET_ODEME_ADIMINA_GECMEK_ICIN_ADRES_EKLE_UYARISI));
        click(UYARI_POPUP_TAMAM_BUTTON);
        scrollToElementCenter(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB);
        click(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB);

        String env = getTestUrlConfig();
        adressCreateWeb(randomAdressDataGuest(mail));
        DriverManager.getDriver().switchTo().activeElement().sendKeys(Keys.TAB);

        click(ADRES_EKLE_KAYDET_BUTTON_WEB_ID);
        //    Assert.assertEquals(getText(ADRES_PAGE_TESLIMAT_ADRES), "test automation - TESTAD TESTNSOYAD");
        //  Assert.assertEquals(getText(ADRES_PAGE_FATURA_ADRES), "test automation - TESTAD TESTNSOYAD");

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(SEPET_DEVAM_ET_BUTTON);
        } else if (urlCheck.equals("fitmoda")) {
            click(SEPET_DEVAM_ET_BUTTON_FITMODA);
        }

        Assert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD));
        Assert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KAYITLI_KARTLARIM_BUTTON));

        Assert.assertFalse(elementVisibiltyWithSize(SEPET_ODEME_ADIMI_KVKK_CHECKBOX));
        Assert.assertFalse(elementVisibiltyWithSize(SEPET_ODEME_ADIMI_KVKK_TEXT));

        scrollToElementCenter(ODEME_PAGE_HAVALE_SECENEK_WEB);
        click(ODEME_PAGE_HAVALE_SECENEK_WEB);
        click(ODEME_PAGE_HAVALE_BANKA_SECIM);
        click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI);
        click(ODEME_PAGE_SOZLESME_ONAY_CHECKBOX);
        click(SEPET_SIPARISI_TAMAMLA_BUTTON);

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZ_TAMAMLANDI_TEXT));
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_ODUL_POPUP_MWEB));
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_KAZANILAN_PUAN_FIELD_WEB), "Kazanılan puan alanı guest müşteri için görünmemeli.");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK), "Siparişinizi buradan takip edebilirsiniz. linki görüldü.Guest müşteri görmemeli.");


    }


    @Then("click DEVAM ET button in checkout")
    public void clickDEVAMETButtonInCheckout() {
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
    }


    @And("page refresh mweb")
    public void pageRefreshMweb() {
        refreshPage();
    }

    @And("ADRESS PAGE page appears")
    public void adressPAGEPageAppears() {
        elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON);
    }

    @And("if the gift option is seen")
    public void ifTheGiftOptionIsSeen() {
        sepetSonrasiHediyeEkraniGecMweb();
    }


    @And("SEPETIM page check")
    public void sepetimPageCheck() {
        String urlCheck = checkStore(getTabUrl());
        click(TOP_SEPET_ICON); //silinecek

        // Cookie bar görürse kapatır
        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }


        progressBarBasketCheckMweb();

        // Ilk urunun fiyatını alır ve fiyat gösterim standartına uygunluğunu test eder
        String price = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            price = getText(SEPET_FIYAT_TYPE);
        } else if (urlCheck.equals("fitmoda")) {
            price = getText(SEPET_FIYAT_TYPE);
        }
        softAssert.assertTrue(checkPriceType(price), "Fiyat type standartlarına uygun değil.Görülen fiyat: " + price);


        newIconCheck();

        //Sepet toplam tutar alanı text ve price type test eder
        softAssert.assertEquals(getText(SEPET_TOPLAM_TEXT), "Toplam");
        String toplamTutar = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        softAssert.assertTrue(checkPriceType(toplamTutar), "Toplam tutar type standartlarına uygun değil.Görülen fiyat: " + toplamTutar);

        myMap.put("sepetToplamTutar", getText(SEPET_TOPLAM_TUTAR_MWEB_V2)); //Sepet toplam tutarı diğer steplerde karşılaştırmak için saklar


        // Bu block sepetteki summary alanının kapalı geldiğini clicklendikten sonra açıldığını test eder.
        softAssert.assertEquals(getStyleValue(SEPET_SIPARIS_OZETI_FIELD), "", "Sepet summary alanı kapalı gelmedi.");
        click(SEPET_TOPLAM_TUTAR_FIELD_MWEB_V2);
        Assert.assertEquals(getStyleValue(SEPET_SIPARIS_OZETI_FIELD), "display: block;");

        //Sepet summary içeriğini kontrol eder
        softAssert.assertEquals(getText(SEPET_ARA_TOPLAM_TEXT), "Ara Toplam");
        softAssert.assertEquals(getText(SEPET_KARGO_TEXT), "Kargo");
        String araToplam = getText(SEPET_ARA_TOPLAM_TUTAR_MWEB_V2);
        softAssert.assertTrue(checkPriceType(araToplam), "Ara toplam tutar type standartlarına uygun değil.Görülen fiyat: " + araToplam);
        softAssert.assertTrue(getText(SEPET_KARGO_BILGI).length() > 3);

        // AH-21 kapsamında hatalı kupon kodu test eder. Fiyatın son ödemeye göre dğeişmesi nedeniyle tutar testi yapamıyorum
        hataliKuponKoduTest("test");

        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);

        sepetSonrasiHediyeEkraniGecMweb();
        softAssert.assertAll();
    }

    @And("ADRES page check - {string}")
    public void adresPageCheck(String mail) {
        String urlCheck = checkStore(getTabUrl());

        // Cookie bar görürse kapatır
        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }

        progressBarBasketCheckMweb();

        //Bu method adress alanlarının görünürlük durumlarını test eder
        adresPageAdresFieldCheck();

        // Bu method yeni adres ekler ve geri dönüp eklenen adresin göründüğünü test eder
        adresPageTeslimatAdresDuzenle(mail);

        // Farklı fatura adresi seçer, seçildiğini adres başlığı ile test eder
        faturaTeslimatAdresleriFarkliSecim();

        // Teslimat-fatura adresleri düzenle buttonu test eder
        adresPageAdresDuzenleButtonTest();

        softAssert.assertEquals(getText(ADRES_PAGE_TESLIMAT_SECIMI_TEXT), "Teslimat Seçimi");

        softAssert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN), "shipping-column sameday-delivery", "AGT wrapper açık olarak görüldü.");
        click(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN);
        softAssert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_OPEN), "shipping-column sameday-delivery active", "AGT wrapper açılmadı.");

        sepetNotu("#test#");

        click(ADRES_PAGE_DEVAM_ET_BUTTON_ID);

        softAssert.assertAll();
    }


    @And("ODEME KREDI KARTI page check")
    public void odemeKARTPageCheck() {
        String urlCheck = checkStore(getTabUrl());

        progressBarBasketCheckMweb();

        odemeSecenekleriSecimOpen("Kredi Kartı");

        sleepInSeconds(2);
        krediKartiSecenegiFieldCheck();

        // Form onaylanma kuralını test eder
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"), "Form onaylanmadan devam edilemez metni beklenenden farklı.");
        click(ALERT_TAMAM);

        click(ODEME_PAGE_SATIS_FORM_FIELD);

        iyzitoTestKart();
        softAssert.assertEquals(getCardTypeValue(ODEME_PAGE_KREDI_KART_NO_TEXTBOX), "mastercard", "Mastercard logosu görülmedi."); //MASTERCARD LOGO VISIBILITY

        // SEPET ILK ADIMI ILE BU ADIMDAKI FIYATI KARŞILAŞTIRIR
        String toplamTutar = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        //  softAssert.assertEquals(toplamTutar, myMap.get("sepetToplamTutar"),"Sepet toplam tutarı, sepet adımı ile farklı.");


        // Kredi kartı bilgileri girilince, taksit seçenekleri alanlarının görüldüğünü test eder
     //   softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT));
    //    softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD), "Kredi kartı taksit seçenekleri alanı görülmedi.");
     //   softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_ACTIVE_TAKSIT), "Kredi kartı taksit seçenekleri, aktif taksit görülmedi.");

        // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
        odemeSayfaFormsCheck();

        scrollPageToTop();
        odemeSecenekleriSecimClose("Kredi Kartı");
        softAssert.assertAll();
    }

    @And("ODEME HAVALE page check")
    public void odemeHAVALEPageCheck() {
        String urlCheck = checkStore(getTabUrl());

        progressBarBasketCheckMweb();

        odemeSecenekleriSecimOpen("Havale");

        havaleSecenegiFieldCheck();

        // Banka seçilmeden tamamlanmak istenediğinde görülecek uyarıyı test eder
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.havaleBankaSecin"), "Banka seçin uyarısı beklenenden veya görülmedi.");
        click(ALERT_TAMAM);


        // Garanti bankasını seçer
        click(ODEME_PAGE_HAVALE_BANKA_SECIM_V2);

        havaleBankaLogoCheck();  // Havale bankalarının logolarının kırık olup olmadığını test eder.

        click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI_V2);

        // Seçilen aktif bankanın logosunun göründüğünü test eder
        String bankaLogoImage = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            bankaLogoImage = getImageUrl(ODEME_PAGE_HAVALE_BANKA_LOGO);
        } else if (urlCheck.equals("fitmoda")) {
            bankaLogoImage = getImageUrl(ODEME_PAGE_HAVALE_BANKA_LOGO_FITMODA);
        }
        softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "garanti", "Seçilen banka logo url'i farklı.");

        // Garanti bankası için havale bilgilerinin doğruluğunu test eder
        havaleBilgileriCheck();

        // Form onaylanmadan tamamlanmak istendiğinde görülecek uyarıyı test eder.
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"));
        click(ALERT_TAMAM);

        click(ODEME_PAGE_SATIS_FORM_FIELD);

        // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
        odemeSayfaFormsCheck();

        scrollPageToTop();
        odemeSecenekleriSecimClose("Havale");
        softAssert.assertAll();
    }

    @And("ODEME KAPIDA KREDI KARTI page check")
    public void odemeKAPIDAKREDIKARTIPageCheck() {
        progressBarBasketCheckMweb();

        odemeSecenekleriSecimOpen("Kapıda Kredi Kartı");

        softAssert.assertEquals(getText(ODEME_PAGE_KAPIDA_KREDI_KARTI_METIN), getData("textControl.kapidaKrediKarti"));

        // Form onaylanmadan tamamlanmak istendiğinde görülecek uyarıyı test eder.
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"), "Form onaylanmadan devam edilemez metni beklenenden farklı.");
        click(ALERT_TAMAM);

        click(ODEME_PAGE_SATIS_FORM_FIELD);

        // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
        odemeSayfaFormsCheck();

        scrollPageToTop();
        odemeSecenekleriSecimClose("Kapıda Kredi Kartı");
        softAssert.assertAll();
    }

    @And("ODEME KAPIDA NAKIT page check")
    public void odemeKAPIDANAKITPageCheck() {
        progressBarBasketCheckMweb();

        odemeSecenekleriSecimOpen("Kapıda Nakit");

        sleepInSeconds(2);
        softAssert.assertEquals(getText(ODEME_PAGE_KAPIDA_NAKIT_METIN), getData("textControl.kapidaNakit"));

        // Form onaylanmadan tamamlanmak istendiğinde görülecek uyarıyı test eder.
        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"), "Form onaylanmadan devam edilemez metni beklenenden farklı.");
        click(ALERT_TAMAM);

        click(ODEME_PAGE_SATIS_FORM_FIELD);

        // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
        odemeSayfaFormsCheck();

        scrollPageToTop();
        odemeSecenekleriSecimClose("Kapıda Nakit");
        softAssert.assertAll();

    }

    @And("ODEME ALISVERIS KREDISI check")
    public void odemeALISVERISKREDISICheck() {
        progressBarBasketCheckMweb();

        alisverisKredisiLimitPass();
        softAssert.assertAll();

    }

    @And("progress bar click check mweb")
    public void progressBarClickCheckMweb() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(PROGRESS_BAR_ADRES);
            progressBarBasketCheckMweb();

            click(PROGRESS_BAR_SEPETIM);
            progressBarBasketCheckMweb();

            click(PROGRESS_BAR_ODEME);
            progressBarBasketCheckMweb();
        } else if (urlCheck.equals("fitmoda")) {
            LOGGER.info("Fitmoda mweb için progress bar otomasyonda sayfa altında kaldığını için tıklanamadı.");
        }


    }

    @And("go to address step in basket mweb")
    public void goToAddressStepInBasketMweb() {

        click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);

        sepetSonrasiHediyeEkraniGecMweb();
    }

    @And("AGT delivery selection and go to payment step MWEB")
    public void agtDeliverySelectionGoToPaymentMWEB() {


        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }
        click(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN);
        Assert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_OPEN), "shipping-column sameday-delivery active");
        click(ADRES_PAGE_AGT_TESLIMAT_AKTIF_1);

        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_AGT_TESLIMAT_INFO_MESSAGE));

        click(ADRES_PAGE_SEPET_NOTU_EKLEMEK_ISTIYORUM_TEXT_MWEB);
        Assert.assertEquals(getPlaceholderValue(SEPET_NOTU_TEXTBOX), "Mesajınızı Bu Alana Ekleyebilirsiniz");
        enterText(SEPET_NOTU_TEXTBOX, "#test#");

        click(ADRES_PAGE_DEVAM_ET_BUTTON_ID);
    }

    @And("complete AGT order by {string} transfer mweb")
    public void completeTheOrderByTransfer(String payment) {
        if (payment.equals("havale")) {
            click(ODEME_PAGE_HAVALE_SECENEK);
            sleepInSeconds(1);
            Assert.assertEquals(getDataToogleValue(ODEME_PAGE_HAVALE_SECENEK_BUTTON), "open");

            //      siparisTamamlaFormCheckBoxClick();
            click(ODEME_PAGE_SATIS_FORM_FIELD);
            click(ODEME_PAGE_HAVALE_BANKA_SECIM_V2);
            click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI_V2);


            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
            sleepInSeconds(2);
            siparisAlindiPageCheck();

            click(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK);

            Assert.assertTrue(elementVisibiltyWithSize(SIPARISLARIM_PAGE_BACK_BUTTON));

        } else if (payment.equals("kapıda")) {
            click(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK);
            Assert.assertTrue(elementVisibiltyWithSize(UYARI_POPUP));
            Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.agtKapidaKrediKarti"));
            click(ALERT_TAMAM);

            click(ODEME_PAGE_KAPIDA_NAKIT_SECENEK);
            Assert.assertTrue(elementVisibiltyWithSize(UYARI_POPUP));
            Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.agtKapidaNakit"));
            click(ALERT_TAMAM);

            clearBasketMweb();

        } else if (payment.equals("alışveriş kredisi")) {
            click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);
            sleepInSeconds(1);

            alisverisKredisiLimitPass();

            if (elementVisibiltyWithSize(ODEME_PAGE_ALISVERIS_KREDISI_IMAGE)) {
                click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
                Assert.assertTrue(elementVisibiltyWithSize(ALISVERIS_KREDISI_TEST_BANK));
                backPage();
                clearBasketMweb();
            }
        }
    }


    @And("get total cart amount")
    public void getTotalCartAmount() {
        String urlCheck = checkStore(getTabUrl());
        String totalPriceFull = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB);
        } else if (urlCheck.equals("fitmoda")) {
            totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB_FITMODA);
        }

        totalPriceOld = totalPriceFull.split(" ")[0];
        LOGGER.info("İndirim öncesi toplam tutar: " + totalPriceOld);
    }

    @And("get total cart amount fitmoda")
    public void getTotalCartAmountFitmoda() {
        String totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB_FITMODA);
        totalPriceOld = totalPriceFull.split(" ")[0];
        LOGGER.info("İndirim öncesi toplam tutar: " + totalPriceOld);
    }

    @When("click KUPON KODU checkbox")
    public void clickKUPONKODUCheckbox() {
        click(SEPET_KUPON_KODU_CHECKBOX);
    }

    @And("enter {string} in coupon code")
    public void enterInCouponCode(String couponCode) {
        scrollToElementCenter(SEPET_KUPON_KODU_TEXTBOX);
        enterText(SEPET_KUPON_KODU_TEXTBOX, couponCode);
        LOGGER.info("Kupon kodu olarak girildi: " + couponCode);
    }

    @And("click INDIRIMI KULLAN")
    public void clickINDIRIMIKULLAN() {
        click(SEPET_INDIRIMI_KULLAN_BUTTON);
    }

    @Then("check that the {string} alert message appears")
    public void checkThatTheAlertMessageAppears(String message) {
        String errorMessage = getText(SEPET_HATA_MESAJI);
        Assert.assertEquals(errorMessage, getData("errorMessage." + message + ""));
    }

    @And("check total cart amount is same as previous step")
    public void checkTotalCartAmountIsSameAsPreviousStep() {
        String urlCheck = checkStore(getTabUrl());
        String totalPriceFull = null;
        sleepInSeconds(2);
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB);
        } else if (urlCheck.equals("fitmoda")) {
            totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB_FITMODA);
        }
        totalPriceNew = totalPriceFull.split(" ")[0];
        sleepInSeconds(2);
      //  Assert.assertEquals(totalPriceNew, totalPriceOld);
    }

    @And("check total cart amount is same as previous step fitmoda")
    public void checkTotalCartAmountIsSameAsPreviousStepFitmoda() {
        String totalPriceFull = getText(SEPET_TOPLAM_TUTAR_WEB_FITMODA);
        totalPriceNew = totalPriceFull.split(" ")[0];
        Assert.assertEquals(totalPriceNew, totalPriceOld);
    }


    @And("clear basket")
    public void clearBasketAfterTest() {
        refreshPage();
        click(TOP_SEPET_BUTTON_WEB);
        clearBasket();
        int baskteItems = getListSize(SEPET_URUN_DELETE_ICON_SIZE);
        Assert.assertEquals(baskteItems, 0);
        LOGGER.info("Basket empty");
    }

    @And("clear basket mweb")
    public void clearBasketAfterTestMweb() {
        clearBasketMweb();
    }


    @And("increase the quantity of the {int} product in the cart as {int} mweb")
    public void increaseTheQuantityOfTheProductInTheCartAsMweb(int seq, int num) {
        String urlCheck = checkStore(getTabUrl());

        By element = null;
        By activeCount = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            if (elementVisibiltyWithSize(By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span"))) {
                element = By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[2]");
                activeCount = By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span");
                for (int i = 1; i <= num; i++) {
                    click(element);
                }
                Assert.assertEquals(getText(activeCount), num);
                LOGGER.info("Ürün adeti: " + seq + " olarak güncellendi.");
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        } else if (urlCheck.equals("fitmoda")) {
            if (elementVisibiltyWithSize(By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span"))) {
                element = By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[2]");
                activeCount = By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span");
                for (int i = 1; i <= num; i++) {
                    click(element);
                }
                Assert.assertEquals(getText(activeCount), num);
                LOGGER.info("Ürün adeti: " + seq + " olarak güncellendi.");
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        }

    }

    @And("reduce the quantity of the {int} product in the cart as {int} mweb")
    public void reduceTheQuantityOfTheProductInTheCartAsMweb(int seq, int num) {
        String urlCheck = checkStore(getTabUrl());

        By element = null;
        By activeCount = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            if (elementVisibiltyWithSize(By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span"))) {
                element = By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[1]");
                activeCount = By.xpath("/html/body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/span");
                int activeCountSize = Integer.parseInt(getText(activeCount));
                for (int i = activeCountSize; i == num; i--) {
                    click(element);
                }
                Assert.assertEquals(getText(activeCount), num);
                LOGGER.info("Ürün adeti: " + seq + " olarak güncellendi.");
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        } else if (urlCheck.equals("fitmoda")) {
            if (elementVisibiltyWithSize(By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div"))) {
                element = By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[1]");
                activeCount = By.xpath("/html/body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div");
                int activeCountSize = Integer.parseInt(getText(activeCount));
                for (int i = activeCountSize; i == num; i--) {
                    click(element);
                }
                Assert.assertEquals(getText(activeCount), num);
                LOGGER.info("Ürün adeti: " + seq + " olarak güncellendi.");
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        }

    }

    @And("delete the {int} product from the basket by clicking the trash icon and check product deleted")
    public void deleteTheSequenceProductFromTheBasketByClickingTheTrashIcon(int seq) {
        String urlCheck = checkStore(getTabUrl());
        String prdocutName = getText(By.xpath("(//div[@class='cc-head'])[" + seq + "]/a"));
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(By.xpath("//body/div[2]/div[4]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[1]/img"));
        } else if (urlCheck.equals("fitmoda")) {
            click(By.xpath("//body/div[2]/div[3]/div[1]/div[3]/form/div[1]/div[" + seq + "]/div[2]/div[2]/div[2]/div/div/a[1]/img"));
        }
        Assert.assertNotEquals(getText((By.xpath("(//div[@class='cc-head'])[" + seq + "]/a"))), prdocutName);
        LOGGER.info("Ürün başarıla silindi.");

    }


    @And("update the quantity of the {int} product in the cart as {int}")
    public void updateTheQuantityOfTheProductInTheCartAs(int seq, int num) {

        boolean urlCheck = checkStringContains(getTabUrl(), "fitmoda");
        By element;
        int sequence = seq * 2;
        if (!urlCheck) {
            if (elementVisibiltyWithSize(By.xpath("//body/div[9]/div/form/div/div[1]/div[2]/div[" + sequence + "]/div[3]/span/input"))) {
                element = By.xpath("//body/div[9]/div/form/div/div[1]/div[2]/div[" + sequence + "]/div[3]/span/input");
                click(element);
                DriverManager.getDriver().findElement(element).clear();
                enterText(element, String.valueOf(num));
                DriverManager.getDriver().findElement(element).sendKeys(Keys.RETURN);
                Assert.assertEquals(getProductQuantityInBasket(element), Integer.toString(num));
                storeData("quantity" + seq, seq);
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        } else {
            if (elementVisibiltyWithSize(By.xpath("/html/body/div[7]/div[3]/form/div/div[1]/div[2]/div[" + sequence + "]/div[3]/span/input"))) {
                element = By.xpath("/html/body/div[7]/div[3]/form/div/div[1]/div[2]/div[" + sequence + "]/div[3]/span/input");
                click(element);
                DriverManager.getDriver().findElement(element).clear();
                enterText(element, String.valueOf(num));
                DriverManager.getDriver().findElement(element).sendKeys(Keys.RETURN);
                Assert.assertEquals(getProductQuantityInBasket(element), Integer.toString(num));
                storeData("quantity" + seq, seq);
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        }

    }


    @And("order description field {int} product quantity badge")
    public void orderDescriptionFieldProductQuantityBadge(int num) {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            String xIcon = getText(By.xpath("/html/body/div[9]/div/div[3]/div[2]/div[2]/div/div/div[1]/div/ul/li[1]/div[1]/span"));
            Assert.assertEquals(xIcon, "x" + num);
        } else if (urlCheck.equals("fitmoda")) {
            String xIcon = getText(By.xpath("/html/body/div[7]/div[3]/div[2]/div/div[2]/div/div/div[1]/div/ul/li[1]/div[1]/span"));
            Assert.assertEquals(xIcon, "x" + num);
        }

    }


    @And("click SEPETI ONAYLA")
    public void clickSEPETIONAYLA() {
        boolean urlCheck = checkStringContains(getTabUrl(), "m.");
        if (!urlCheck) {
            click(SEPET_SEPETI_ONAYLA_BUTTON);
        } else {
            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB);
        }
    }

    @And("click SEPETI ONAYLA guest")
    public void clickSEPETIONAYLAGuest() {
        boolean urlCheck = checkStringContains(getTabUrl(), "m.");
        if (!urlCheck) {
            click(SEPET_SEPETI_ONAYLA_BUTTON_GUEST);
        } else {
            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB);
        }
    }


    @And("choose gift action is {string} mweb")
    public void chooseGiftActionIsMweb(String action) {
        String urlCheck = checkStore(getTabUrl());
        if (elementVisibiltyWithSize(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_WEB)) {
            LOGGER.info("Hediye seçimi popupo görüldü.");
            if (action.equals("true")) {

                if (urlCheck.equals("supplementler")) {
                    click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_MWEB_SUPP);
                    LOGGER.info("1. hediye seçildi.");
                } else {
                    // click(GIFT_POPUP_IN_BASKET_PAGE_FIRST_GIFT_VITAMINLER);
                    //LOGGER.info("1. hediye seçildi.");
                }
            }
            if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                click(UYARI_POPUP_TAMAM_BUTTON);
                click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_2_MWEB_SUPP);
                click(SEPET_SEPETI_ONAYLA_BUTTON);
            }
        }
    }

    @And("choose gift popup action is {string} fitmoda")
    public void chooseGiftPopupActionIsFitmoda(String action) {

        if (elementVisibiltyWithSize(SEPET_HEDIYE_SECIM_POPUP_SIZE)) {
            LOGGER.info("Hediye seçimi popupo görüldü.");
            if (action.equals("true")) {
                // click(GIFT_POPUP_IN_BASKET_PAGE_FIRST_GIFT_SUPPLEMENTLER); EKLENMEDİ
                //LOGGER.info("1. hediye seçildi.");
            }
            if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                click(UYARI_POPUP_TAMAM_BUTTON);
                click(SEPET_HEDIYE_SECIM_2);
                click(SEPET_SEPETI_ONAYLA_BUTTON);
            }
        }
    }

    @When("check that the {string} is visible in order steps")
    public void checkThatElementIsVisibleIsOrderSteps(String element) {
        Assert.assertTrue(elementVisibilty(By.xpath("//a[normalize-space()='" + element + "']")));
    }

    @And("click SIPARIS OZETINI GENISLET icon")
    public void clickSIPARISOZETINIGENISLETIcon() {
        sleepInSeconds(2);
        click(ADRES_PAGE_SIPARIS_OZETI_FIELD);
    }


    @And("check that all products have a quantity symbol")
    public void checkThatAllProductsHaveAQuantitySymbol() {
        int iconSize = getListSize(ADRES_PAGE_SIPARIS_OZETI_ICON_SIZE);
        int productSize = getListSize(ADRES_PAGE_SIPARIS_OZETI_URUN_SIZE);
        Assert.assertEquals(iconSize, productSize);

    }


    @And("complete test order")
    public void completeTestOrder() {
        String urlCheck = checkStore(getTabUrl());
        click(SEPET_SEPETI_ONAYLA_BUTTON);

        sepetSonrasiHediyeEkraniGecWeb();

        sepetNotuWeb("#test#");

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(SEPET_DEVAM_ET_BUTTON);
        } else if (urlCheck.equals("fitmoda")) {
            click(SEPET_DEVAM_ET_BUTTON_FITMODA);
        }

        click(ODEME_PAGE_HAVALE_SECENEK_WEB);
        click(ODEME_PAGE_HAVALE_BANKA_SECIM);
        click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI);
        click(ODEME_PAGE_SOZLESME_ONAY_CHECKBOX);
        click(SEPET_SIPARISI_TAMAMLA_BUTTON);
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZ_TAMAMLANDI_TEXT));
    }


    @And("choose gift popup action is {string}")
    public void chooseGiftPopupActionIs(String action) {

        String urlCheck = checkStore(getTabUrl());

        if (elementVisibiltyWithSize(SEPET_HEDIYE_SECIM_POPUP_SIZE)) {
            LOGGER.info("Hediye seçimi popupı görüldü.");
            if (action.equals("true")) {

                if (urlCheck.equals("supplementler")) {
                    click(SEPET_HEDIYE_SECIM_1_SUPPLEMENTLER);
                    LOGGER.info("1. hediye seçildi.");
                } else if (urlCheck.equals("vitaminler")) {
                    click(SEPET_HEDIYE_SECIM_1_VITAMINLER);
                    LOGGER.info("1. hediye seçildi.");
                }
            }
            if (elementVisibiltyWithSize(UYARI_POPUP_TAMAM_BUTTON)) {
                LOGGER.info("Seçilen hediye için stok uyarısı alındı.");
                click(UYARI_POPUP_TAMAM_BUTTON);
                click(SEPET_HEDIYE_SECIM_2);
                click(SEPET_SEPETI_ONAYLA_BUTTON);
            }
        } else {
            LOGGER.info("Sepete hediye popupı görülmedi.");
        }

    }


    @When("SEPETIM page check web")
    public void sepetimPageCheckWeb() {
        //click(TOP_SEPET_ICON); //silinecek

        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }

        String urlCheck = checkStore(getTabUrl());

        Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_WEB), "Sepet progress bar görülmedi.");
        Assert.assertTrue(getText(SEPET_PROGRESS_BAR_AKTIF_TAB_WEB).contains("Sepetim"));
        String price = null;

        Assert.assertFalse(elementVisibiltyWithSize(By.xpath("//div[@class='page-large-title ']")), "Eski page title görüldü.");
        price = getText(SEPET_FIYAT_TYPE_WEB);

        Assert.assertTrue(checkPriceType(price), "Fiyat type standartlarına uygun değil.");

        Assert.assertEquals(getText(SEPET_TOPLAM_TEXT_WEB), "TOPLAM");
        //String toplamTutar = getText(SEPET_TOPLAM_TUTAR_WEB);
        //Assert.assertTrue(checkPriceType(toplamTutar), "Toplam tutar type standartlarına uygun değil.");

        //myMap.put("sepetToplamTutar", getText(SEPET_TOPLAM_TUTAR_WEB));

        Assert.assertEquals(getText(SEPET_ARA_TOPLAM_TEXT_WEB), "ARA TOPLAM");
        Assert.assertEquals(getText(SEPET_KARGO_TEXT_WEB), "KARGO");
        String araToplam = getText(SEPET_ARA_TOPLAM_TUTAR_TEXT_WEB);
        //Assert.assertTrue(checkPriceType(araToplam), "Ara toplam tutar type standartlarına uygun değil.");
        Assert.assertTrue(getText(SEPET_KARGO_BILGI_WEB).length() > 3);

        click(SEPET_SIPARISI_TAMAMLA_BUTTON_WEB);

        if (elementVisibiltyWithSize(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_WEB)) {
            click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_WEB);
        }
        if (elementVisibiltyWithSize(SEPET_HEDIYE_STOK_UYARISI)) {
            click(SEPET_HEDIYE_STOK_UYARISI);
            click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_2_WEB);
            if (elementVisibiltyWithSize(SEPET_HEDIYE_STOK_UYARISI)) {
                click(SEPET_HEDIYE_STOK_UYARISI);
                click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_3_WEB);
            }
        }

        if (!elementVisibiltyWithSize(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB)) {
            click(SEPET_SIPARISI_TAMAMLA_BUTTON_WEB);
        }

    }

    @Then("ADRES page check web")
    public void adresPageCheckWeb() {


        String urlCheck = checkStore(getTabUrl());

        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }

        Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_WEB), "Sepet progress bar görülmedi.");
        //Assert.assertTrue(getText(SEPET_PROGRESS_BAR_AKTIF_TAB_WEB).contains("Adres"));


        sleepInSeconds(2);
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRESI_WRAPPER_WEB));


        Assert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN_WEB), "");
        Assert.assertEquals(getText(ADRES_PAGE_FATURAM_SIPARI_ILE_BIRLIKTE_GONDERILSIN_TEXT_WEB), "Faturam sipariş ile birlikte gönderilsin.");

        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);

        Assert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN_WEB), "display: none;");

        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        sleepInSeconds(1);
        Assert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN_WEB), "display: block;");

        /*
        if (urlCheck.equals("supplementler")) {
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_ADRES_FATURA_ADRESI_WRAPPER));
        } else if (urlCheck.equals("vitaminler")) {
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_ADRES_FATURA_ADRESI_WRAPPER_VITAMINLER));
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_ADRES_FATURA_ADRESI_WRAPPER_FITMODA));
        }

         */

        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB), "Yeni adres ekle buttonu görülmedi.");
        click(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB);
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON_WEB));
        click(ADRES_EKLE_PAGE_ADRES_SECIMINE_GERI_DON_BUTTON_WEB);

        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRES_DUZENLE_BUTTON_WEB)); // burası hangisi olacak teslimat mı fatura mı
        click(ADRES_PAGE_TESLIMAT_ADRES_DUZENLE_BUTTON_WEB);// burası hangisi olacak teslimat mı fatura mı
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON_WEB));
        click(ADRES_EKLE_PAGE_ADRES_SECIMINE_GERI_DON_BUTTON_WEB);

        Assert.assertEquals(getText(ADRES_PAGE_TESLIMAT_SECIMI_TEXT_WEB), "Teslimat Seçenekleri");

        Assert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN), "shipping-column sameday-delivery");
        click(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN);
        Assert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_OPEN), "shipping-column sameday-delivery active");


        click(ADRES_PAGE_STANDART_TESLIMAT_WRAPPER_WEB);
        int kargoSize = getListSize(ADRES_PAGE_KARGO_SECENEGI_SIZE);
/*
            if (kargoSize > 1) {
                By elementKargo1 = By.xpath("/html/body/div[2]/div[4]/div[3]/div[5]/div[2]/div/div[2]/ul/li[1]");
                By elementKargo2 = By.xpath("/html/body/div[2]/div[4]/div[3]/div[5]/div[2]/div/div[2]/ul/li[2]");
                click(elementKargo2);
                Assert.assertEquals(getClassValue(elementKargo2), "nd-standard-shipping selected");
                Assert.assertEquals(getClassValue(elementKargo1), "nd-standard-shipping");
            }
            */

            /*
            if (kargoSize > 1) {
                By elementKargo1 = By.xpath("/html[1]/body[1]/div[2]/div[3]/div[3]/div[5]/div[2]/div[1]/div[2]/ul[1]/li[1]");
                By elementKargo2 = By.xpath("/html[1]/body[1]/div[2]/div[3]/div[3]/div[5]/div[2]/div[1]/div[2]/ul[1]/li[2]");
                click(elementKargo2);
                Assert.assertEquals(getClassValue(elementKargo2), "nd-standard-shipping selected");
                Assert.assertEquals(getClassValue(elementKargo1), "nd-standard-shipping");
            }

             */

        Assert.assertEquals(getPlaceholderValue(SEPET_NOTU_TEXTBOX), "Sipariş ile ilgili notunuzu giriniz");
        enterText(SEPET_NOTU_TEXTBOX, "#test#");

        click(ADRES_PAGE_DEVAM_ET_BUTTON_ID_WEB);

    }


    @And("complete the guest order with the {string} method")
    public void completeTheGuestOrderWithTheMethod(String payment) {
        String urlCheck = checkStore(getTabUrl());
        switch (payment) {
            case "kredi kartı":
                odemeSecenekleriSecimOpen("Kredi Kartı");

                sleepInSeconds(2);
                krediKartiSecenegiFieldCheckGuest();

                Assert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD));

                iyzitoTestKart();
                softAssert.assertEquals(getCardTypeValue(ODEME_PAGE_KREDI_KART_NO_TEXTBOX), "mastercard", "Mastercard logosu görülmedi."); //MASTERCARD LOGO VISIBILITY

                // SEPET ILK ADIMI ILE BU ADIMDAKI FIYATI KARŞILAŞTIRIR
                String toplamTutar = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
                //    softAssert.assertEquals(toplamTutar, myMap.get("sepetToplamTutar"),"Sepet toplam tutarı, sepet adımı ile farklı.");

                // Kredi kartı bilgileri girilince, taksit seçenekleri alanlarının görüldüğünü test eder
                //softAssert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT));
               // softAssert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD), "Kredi kartı taksit seçenekleri alanı görülmedi.");
               // softAssert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_ACTIVE_TAKSIT), "Kredi kartı taksit seçenekleri, aktif taksit görülmedi.");
             //   if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
              //      Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTI_TEK_TEKIM_TUTAR_1), getText(ODEME_PAGE_KREDI_KARTI_TEK_TEKIM_TUTAR_2), "Kredi kartı tek çekim tutarı farklı.");
               // } else if (urlCheck.equals("fitmoda")) {
                //    Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTI_TEK_TEKIM_TUTAR_1_FITMODA), getText(ODEME_PAGE_KREDI_KARTI_TEK_TEKIM_TUTAR_2_FITMODA), "Kredi kartı tek çekim tutarı farklı.");
               // }

                // kredi kartı ödeme tamamla eklenecek
                scrollPageToTop();

                break;
            case "havale":
                progressBarBasketCheckMweb();

                odemeSecenekleriSecimOpen("Havale");

                havaleSecenegiFieldCheck();

                click(ODEME_PAGE_HAVALE_BANKA_SECIM_V2);
                click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI_V2);
                sleepInSeconds(2);

                click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
                Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"));
                click(ALERT_TAMAM);
                click(ODEME_PAGE_SATIS_FORM_FIELD);

                click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);

                siparisAlindiPageCheckGuest();

                break;
            case "kapıda":

                break;
            case "alışveriş kredisi":

                break;
        }
    }

    @And("ODEME KREDI KARTI page check web")
    public void odemeKREDIKARTIPageCheckWeb() {
        String urlCheck = checkStore(getTabUrl());

//        click(TOP_SEPET_ICON_WEB);
//        click(TOP_SEPETE_GIT_ICON_WEB);
//        click(SEPET_SIPARISI_TAMAMLA_BUTTON_WEB);
//        click(SEPET_SIPARISI_TAMAMLA_BUTTON_WEB);

        Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_WEB), "Sepet progress bar görülmedi.");
        Assert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_TAB_WEB), "3\nÖdeme");

        Assert.assertEquals(getListSize(ODEME_PAGE_ODEME_SECENEKLERI_SIZE_WEB), 5);

        click(ODEME_PAGE_KREDI_KARTI_SECENEK_WEB);
        click(ODEME_PAGE_KREDI_KARTI_SECENEK_WEB);
        sleepInSeconds(2);
        Assert.assertTrue(getClassValue(ODEME_PAGE_KREDI_KARTI_SECENEK_WRAPPER_WEB).contains("selected"));

        sleepInSeconds(2);
        krediKartiSecenegiFieldCheckWeb();

        click(ODEME_SIPARISI_TAMAMLA_BUTTON_WEB);
        Assert.assertTrue(getText(UYARI_POPUP_WEB).contains(getData("uyariMessage.formOnayla")));
        click(ALERT_TAMAM_WEB);

        //      siparisTamamlaFormCheckBoxClick();
        click(ODEME_PAGE_SATIS_FORM_CHECKBOX_WEB);


        click(ODEME_SIPARISI_TAMAMLA_BUTTON_WEB);
        Assert.assertTrue(elementVisibiltyWithSize(UYARI_POPUP_WEB));
        Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTI_EKSIK_ALANLAR_HATA_MESAJI), getData("errorMessage.krediKartiEksikAlanlarWeb"));
        click(ALERT_TAMAM_WEB);

        click(ODEME_PAGE_KAYITLI_KARTLARIM_BUTTON_WEB);
        Assert.assertEquals(getText(ODEME_PAGE_KAYITLI_KARTINIZ_YOK_TEXT_WEB), "Kayıtlı kartınız bulunmamaktadır.");
        click(ODEME_PAGE_YENI_KART_EKLE_BUTTON_WEB);

        iyzitoTestKartWeb();
        //Assert.assertEquals(getCardTypeValue(ODEME_PAGE_KREDI_KART_NO_TEXTBOX), "mastercard"); //MASTERCARD LOGO VISIBILITY

        //String toplamTutar = getText(SEPET_TOPLAM_TUTAR_WEB);
        // Assert.assertEquals(toplamTutar, myMap.get("sepetToplamTutar")); // SEPET ILK ADIMI ILE BU ADIMDAKI FIYATI KARŞILAŞTIRIR

        Assert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT_WEB));
        Assert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD_WEB));
        Assert.assertTrue(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_ACTIVE_TAKSIT_WEB));


        //Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTI_TEK_TEKIM_TUTAR_1_WEB), getText(SEPET_TOPLAM_TUTAR_WEB));


        click(ODEME_PAGE_ONBILGILENDIRME_FORMU_WEB);
        //iframe text kontrolü gelecek
        click(ODEME_PAGE_SOZLESME_POPUP_KAPAT_ICON_WEB);

        click(ODEME_PAGE_MESAFELISATIS_SOZLESMESI_WEB);
        //iframe text kontrolü gelecek
        click(ODEME_PAGE_SOZLESME_POPUP_KAPAT_ICON_WEB);

        //AYDINLATMA METNI VE RIZA METNI SONRA EKLENECEK
        //scrcollPageToTop();
        click(ODEME_PAGE_KREDI_KARTI_SECENEK_WEB);
        Assert.assertFalse(getClassValue(ODEME_PAGE_KREDI_KARTI_SECENEK_WRAPPER_WEB).contains("selected")); // KREDI KARTI ALANI KAPANDI




    }


    @And("go to address step in basket web")
    public void goToAddressStepInBasketWeb() {

        click(SEPET_SIPARISI_TAMAMLA_BUTTON_WEB);

        sepetSonrasiHediyeEkraniGecWeb();
    }

    @And("AGT delivery selection and go to payment step WEB")
    public void agtDeliverySelectionAndGoToPaymentStepWEB() {
        if (elementVisibilty(COOKIE_BAR_CLOSE)) {
            click(COOKIE_BAR_CLOSE);
        }
        click(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_HIDDEN);
        Assert.assertEquals(getClassValue(ADRES_PAGE_ADRESINE_HEMEN_TESLIM_WRAPPER_OPEN), "shipping-column sameday-delivery active");
        click(ADRES_PAGE_AGT_TESLIMAT_AKTIF_1);

        click(ADRES_PAGE_SEPET_NOTU_EKLEMEK_ISTIYORUM_TEXT_WEB);
        Assert.assertEquals(getPlaceholderValue(ADRES_PAGE_SEPET_NOTU_EKLEMEK_ISTIYORUM_TEXT_WEB), "Sipariş ile ilgili notunuzu giriniz");
        enterText(SEPET_NOTU_TEXTBOX, "#test#");

        String urlCheck = checkStore(getTabUrl());

        sleepInSeconds(2);
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(ADRES_PAGE_DEVAM_ET_BUTTON_ID_WEB);
        } else if (urlCheck.equals("fitmoda")) {
            click(ADRES_PAGE_DEVAM_ET_BUTTON_ID_WEB_FITMODA);
        }

    }

    @And("complete AGT order by {string} transfer Web")
    public void completeAGTOrderByTransferWeb(String payment) {
        if (payment.equals("havale")) {
            click(ODEME_PAGE_KREDI_KARTI_SECENEK_WEB);
            click(ODEME_PAGE_HAVALE_BUTON_WEB2);
            sleepInSeconds(2);
            // Assert.assertTrue(getClassValue(ODEME_PAGE_HAVALE_SECENEK_WEB).contains("selected"));

            //      siparisTamamlaFormCheckBoxClick();
            click(ODEME_PAGE_SATIS_FORM_CHECKBOX_WEB);
            click(ODEME_PAGE_HAVALE_BANKA_SECIM);
            click(ODEME_PAGE_HAVALE_BANKA_SECIM_GARANTI_WEB);


            click(ODEME_SIPARISI_TAMAMLA_BUTTON_WEB);
            sleepInSeconds(2);
            siparisAlindiPageCheckWeb();

            click(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK);

            Assert.assertEquals(getText(SIPARISLARIM_PAGE_BACK_BUTTON_WEB), "Siparişlerim");


//        } else if (payment.equals("kapıda")) {
//            click(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK);
//            Assert.assertTrue(elementVisibiltyWithSize(UYARI_POPUP));
//            Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.agtKapidaKrediKarti"));
//            click(ALERT_TAMAM);
//
//            click(ODEME_PAGE_KAPIDA_NAKIT_SECENEK);
//            Assert.assertTrue(elementVisibiltyWithSize(UYARI_POPUP));
//            Assert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.agtKapidaNakit"));
//            click(ALERT_TAMAM);
//
//            clearBasketMweb();
//
//        } else if (payment.equals("alışveriş kredisi")) {
//            click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);
//            sleepInSeconds(1);
//
//            alisverisKredisiLimitPass();
//
//            if (elementVisibiltyWithSize(ODEME_PAGE_ALISVERIS_KREDISI_IMAGE)) {
//                click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
//                Assert.assertTrue(elementVisibiltyWithSize(ALISVERIS_KREDISI_TEST_BANK));
//                backPage();
//                clearBasketMweb();
//            }
            click(SIPARISLERIM_PAGE_SIPARISIMI_IPTAL_ET_WEB);
            click(SIPARISLERIM_PAGE_SIPARISIMI_IPTAL_ET_DIGER_WEB);
            enterText(SIPARISLERIM_PAGE_SIPARISIMI_IPTAL_ET_MESAJ_WEB, "#test#");
            click(SIPARISLERIM_PAGE_SIPARISIMI_IPTAL_ET_SIPARISI_IPTAL_ET_WEB);
            Assert.assertEquals(getText(SIPARISLERIM_PAGE_SIPARISINIZ_IPTAL_EDILDI_WEB), "Siparişiniz başarılı bir şekilde iptal edildi.");
        }
    }

    @And("check Belirtilen üründen sepette {string} bulunması koşulu discount {string} - {string}")
    public void checkBelirtilenÜründenSepetteBulunmasıKoşuluDiscount(String count, String disocuntType, String code) {
        String urlCheck = checkStore(getTabUrl());

        // İndirim kodu aktif ise, kodu girer, adet sağlanmadan indirim olmadığı kontrül yapar
        if (code.equals("true")) {

            if (elementVisibiltyWithSize(SEPET_KUPON_KODU_SIL_BUTTON)){
                click(SEPET_KUPON_KODU_SIL_BUTTON);
            }
            if (!elementVisibiltyWithSize(SEPET_KUPON_KODU_ACTIVE)) {
                click(SEPET_KUPON_KODU_CHECKBOX);
            }
            enterInCouponCode("TEST AUTOMATION");

            clickINDIRIMIKULLAN();
            String errorMessage = getText(SEPET_HATA_MESAJI);
            Assert.assertEquals(errorMessage, getData("errorMessage.KuponKoduHatası"));
            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB), "Ürün sayısı sağlanmadan indirim görüntülendi.");
            } else if (urlCheck.equals("fitmoda")) {
                Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB_FITMODA), "Ürün sayısı sağlanmadan indirim görüntülendi.");
            }
        }


        By firstCount;
        // Adet sağlar indirim kontrolü yapar, tekrar adet şartını geçer indiirim olmadığı kontrolü yapar
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            firstCount = By.xpath("//body/div[9]/div/form/div/div[1]/div[2]/div[2]/div[3]/span/input");
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(1));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB), "Ürün sayısı sağlanmadan indirim görüntülendi.");

            updateTheQuantityOfTheProductInTheCartAs(1, 2);

            if (code.equals("true")) {
                clickKUPONKODUCheckbox();
                enterInCouponCode("TEST AUTOMATION");
                clickINDIRIMIKULLAN();
            }

            Assert.assertEquals(getProductQuantityInBasket(firstCount), count);
            Assert.assertTrue(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB), "Ürün sayısı sağlandı indirim görüntülenmedi.");
            if (disocuntType.equals("fiyat")) {
                String sepetIndirimTutarNew = getTutar(SEPET_INDIRIM_TUTAR_WEB);
                Assert.assertEquals(sepetIndirimTutarNew, "-100,00");
            } else if (disocuntType.equals("yüzdelik")) {
                String araToplam = getTutar(SEPET_ARA_TOPLAM_TUTAR_WEB);
                String sepetIndirimTutarNew = getTutar(SEPET_INDIRIM_TUTAR_WEB);

                double araToplamType = formatPriceDouble(araToplam);
                String sepetIndirimTutarNewType = String.valueOf(formatPriceDouble(sepetIndirimTutarNew));

                String tutarHesap = String.valueOf((araToplamType) / 10);
                Assert.assertEquals(sepetIndirimTutarNewType, "-" + tutarHesap);
            }

            updateTheQuantityOfTheProductInTheCartAs(1, 3);
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(3));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB), "Ürün sayısı sağlanmadan indirim görüntülendi.");

            updateTheQuantityOfTheProductInTheCartAs(1, 1);
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(1));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB), "Ürün sayısı sağlanmadan indirim görüntülendi.");
        } else if (urlCheck.equals("fitmoda")) {
            firstCount = By.xpath("//body/div[9]/div/form/div/div[1]/div[2]/div[2]/div[3]/span/input");
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(1));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB_FITMODA), "Ürün sayısı sağlanmadan indirim görüntülendi.");

            updateTheQuantityOfTheProductInTheCartAs(1, 2);

            if (code.equals("true")) {
                clickKUPONKODUCheckbox();
                enterInCouponCode("TEST AUTOMATION");
                clickINDIRIMIKULLAN();
            }

            Assert.assertEquals(getProductQuantityInBasket(firstCount), count);
            Assert.assertTrue(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB_FITMODA), "Ürün sayısı sağlandı indirim görüntülenmedi.");
            if (disocuntType.equals("fiyat")) {
                String sepetIndirimTutarNew = getTutar(SEPET_INDIRIM_TUTAR_WEB_FITMODA);
                Assert.assertEquals(sepetIndirimTutarNew, "-100,00");
            } else if (disocuntType.equals("yüzdelik")) {
                String araToplam = getTutar(SEPET_ARA_TOPLAM_TUTAR_WEB_FITMODA);
                String sepetIndirimTutarNew = getTutar(SEPET_INDIRIM_TUTAR_WEB_FITMODA);

                double araToplamType = formatPriceDouble(araToplam);
                String sepetIndirimTutarNewType = String.valueOf(formatPriceDouble(sepetIndirimTutarNew));

                String tutarHesap = String.valueOf((araToplamType) / 10);
                Assert.assertEquals(sepetIndirimTutarNewType, "-" + tutarHesap);
            }

            updateTheQuantityOfTheProductInTheCartAs(1, 3);
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(3));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB_FITMODA), "Ürün sayısı sağlanmadan indirim görüntülendi.");

            updateTheQuantityOfTheProductInTheCartAs(1, 1);
            Assert.assertEquals(getProductQuantityInBasket(firstCount), Integer.toString(1));
            Assert.assertFalse(elementVisibiltyWithSize(SEPET_INDIRIM_TUTAR_WEB_FITMODA), "Ürün sayısı sağlanmadan indirim görüntülendi.");
        }

    }

    @And("if there is a gift, choose and pass")
    public void ifThereIsAGiftChooseAndPass() {
        boolean urlCheck = checkStringContains(getTabUrl(), "m.");
        if (!urlCheck) {
            sepetSonrasiHediyeEkraniGecWeb();
        } else {
            sepetSonrasiHediyeEkraniGecMweb();
            }
    }

    @And("the ADRES EKLE BUTTON appears")
    public void theADRESEKLEBUTTONappears() {


        boolean urlCheck = checkStringContains(getTabUrl(), "m.");
        if (!urlCheck) {

            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_WEB));
        } else {

            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_MWEB));
        }
    }
}

