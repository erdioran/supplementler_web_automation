package base;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import supplementler.base.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import supplementler.utils.ConfigManager;
import io.cucumber.skeleton.StepDefinitionsBasket;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static objectRepository.AdressAddOR.*;
import static objectRepository.BasketOR.*;
import static objectRepository.GeneralOR.*;
import static objectRepository.LoginPageOR.*;
import static objectRepository.MailOR.*;
import static objectRepository.ProductDetailOR.*;
import static objectRepository.ProductListOR.URUN_LISTELEME_PAGE_KATEGORI_FILTER_SIZE;
import static objectRepository.ProductListOR.URUN_LISTELEME_PAGE_KATEGORI_FILTRESI_SAYISI_1;
import static objectRepository.SiparisAlindiOR.*;
import static objectRepository.UserInfoOR.*;
import static supplementler.utils.DataManager.getData;
import static supplementler.utils.Helper.sleepInSeconds;
import static supplementler.utils.Helper.sleepMs;

public class ProjectMethods extends AutomationMethods {

    static Faker faker = new Faker(new Locale("tr"));
    private static final Logger LOGGER = LogManager.getLogger(ProjectMethods.class);

    public static void pageLoad(String url) {
        DriverManager.getDriver().get(url);
    }


    static SoftAssert softAssert = new SoftAssert();

    static HashMap<String, Integer> myMap = new HashMap<String, Integer>();
    static HashMap<String, String> myMapString = new HashMap<String, String>();

    static String secilenAGT;


    public static void storeData(String key, int value) {
        myMap.put(key, value);
    }

    public static void myData(String key, String value) {
        myMapString.put(key, value);
    }

    public static String retrieveData2(String key) {
        String retrieveValue = myMapString.get(key);
        System.out.println("Value retrieved: " + retrieveValue);
        return retrieveValue;
    }

    public static int retrieveData(String key) {
        int retrievedValue = myMap.get(key);
        System.out.println("Value retrieved: " + retrievedValue);
        return retrievedValue;
    }


    public static void clickTopMenu(String menu) { // Methoda gönderilen katerogi adını top menuda bulur ve clickler
        click(By.xpath("//li[@class='main-link-wrapper']//a[@title='" + menu + "']"));
        LOGGER.info(menu + " select in left menu.");
    }

    public static void login(String email, String password) {
        enterText(LOGIN_PAGE_EMAIL_TEXTBOX, email);
        enterText(LOGIN_PAGE_SIFRE_TEXTBOX, password);
        click(LOGIN_PAGE_GIRIS_YAP_BUTTON);
        LOGGER.info("Login action with this data; email: " + email + " password: " + password);
    }

    public static void loginPopup(String email, String password) {
        enterText(LOGIN_PAGE_EMAIL_TEXTBOX, email);
        enterText(LOGIN_PAGE_SIFRE_TEXTBOX, password);
        sleepInSeconds(2);
        click(LOGIN_PAGE_GIRIS_YAP_POPUP_BUTTON);
        sleepInSeconds(10);
        LOGGER.info("Login action with this data; email: " + email + " password: " + password);

    }

    public static void loginMweb(String email, String password) {
        enterText(LOGIN_PAGE_EMAIL_TEXTBOX_XPATH, email);
        enterText(LOGIN_PAGE_SIFRE_TEXTBOX_MWEB, password);
        click(LOGIN_PAGE_GIRIS_YAP_BUTTON_MWEB);
        LOGGER.info("Login action with this data; email: " + email + " password: " + password);
    }


    public static void clickCategortInLefMenuMweb(String category) { //MWEB için, left menude methoda gönderilen katerogiyi bulur ve clickler
        String urlCheck = checkStore(getTabUrl());

        click(LEFT_MENU_MWEB);
        click(LEFT_MENU_KATEGORILER_MWEB);
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(By.xpath("//span[contains(text(),'" + category + "')]"));
            click(By.cssSelector("a[data-label='Tüm " + category + " Ürünleri']"));
        } else if (urlCheck.equals("fitmoda")) {
            click(By.xpath("//span[@class='mlabel'][contains(text(),'" + category + "')]"));
            click(By.cssSelector("a[data-label='Tüm " + category + " Ürünleri']"));
        }
        LOGGER.info(category + " select in left menu. And click listed all product");
    }

    public static void clickPageInLefMenuMweb(String page) { // MWEB için, left menude kategpri altında, methoda gönderilen page i bulur ve clickler
        String urlCheck = checkStore(getTabUrl());

        click(LEFT_MENU_MWEB);
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(By.xpath("//span[contains(text(),'" + page + "')]"));

        } else if (urlCheck.equals("fitmoda")) {
            click(By.xpath("//span[@class='mlabel'][contains(text(),'" + page + "')]"));
        }
        LOGGER.info(page + " page select in left menu.");
    }


    public static void clearBasket() { // WEB için, sepeti temizler
        String urlCheck = checkStore(getTabUrl());
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            while (elementVisibiltyWithSize(SEPET_URUN_DELETE_ICON_1_WEB)) {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(SEPET_URUN_DELETE_ICON_1_WEB));
                element.click();
                click(SEPET_URUNU_SIL_BUTTON_POPUP);
                //   wait.until(ExpectedConditions.stalenessOf(element));
            }
        } else if (urlCheck.equals("fitmoda")) {
            while (elementVisibiltyWithSize(SEPET_URUN_DELETE_ICON_1_WEB)) {
                click(SEPET_URUN_ADET_INPUT_1_WEB_FITMODA);
                DriverManager.getDriver().findElement(SEPET_URUN_ADET_INPUT_1_WEB_FITMODA).clear();
                enterText(SEPET_URUN_ADET_INPUT_1_WEB_FITMODA, String.valueOf(0));
                if (!elementVisibiltyWithSize(By.xpath("//h2[contains(text(),'Sepetin Boş')]"))) {
                    DriverManager.getDriver().findElement(SEPET_URUN_ADET_INPUT_1_WEB_FITMODA).sendKeys(Keys.RETURN);
                }
                //   wait.until(ExpectedConditions.stalenessOf(element));
            }
        }
    }


    public static void clearBasketMweb() { // MWEB için, sepeti temizler
        refreshPage();
        sleepInSeconds(2);
        click(TOP_SEPET_ICON);

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        while (elementVisibiltyWithSize(SEPET_URUN_DELETE_ICON_1_MWEB_V1)) {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(SEPET_URUN_DELETE_ICON_1_MWEB_V1));
            element.click();
            //   wait.until(ExpectedConditions.stalenessOf(element));
        }

        int baskteItems = getListSize(SEPET_URUN_DELETE_ICON_SIZE_MWEB);
        Assert.assertEquals(baskteItems, 0);
        LOGGER.info("Basket empty");
        click(TOP_SITE_LOGO);
    }


    public static String getProductQuantityInBasket(By by) { // Sepetteki elementi gönderilen ürünün, adetini döndürür.
        String quantity = DriverManager.getDriver().findElement(by).getAttribute("value");
        return quantity;
    }

    public static void adreesPagePhoneNumber(String phoneNumber) { // WEB Adres ekle sayfasında, gönderilen string türündeki telefon numarasını telefon no textbox'ına tek tek girer.
        for (char c : phoneNumber.toCharArray()) {
            DriverManager.getDriver().findElement(ADRES_EKLE_TELEFON).sendKeys(Character.toString(c));
        }
    }

    public static void adreesPagePhoneNumberWeb(String phoneNumber) {// MWEB Adres ekle sayfasında, gönderilen string türündeki telefon numarasını telefon no textbox'ına tek tek girer.
        for (char c : phoneNumber.toCharArray()) {
            DriverManager.getDriver().findElement(ADRES_EKLE_TELEFON_WEB).sendKeys(Character.toString(c));
        }
    }

    public static void kullaniciBilgileriPhoneNumber(String phoneNumber) { // Kullanıcı bilgileri sayfasında, gönderilen string türündeki telefon numarasını telefon no textbox'ına tek tek girer.
        for (char c : phoneNumber.toCharArray()) {
            DriverManager.getDriver().findElement(UYELIK_BILGILERI_TELEFON_MWEB).sendKeys(Character.toString(c));
        }
    }

    public static void adressCreateMweb(String adressAd, String adressSoyad, String adressIl, String adressIlce, String adressSemt, String adressDetay, String adressTitle, String adressMail, String phoneNumber) {
        // MWEB adres oluştururken kullanılır.
        enterText(ADRES_EKLE_AD, adressAd);
        enterText(ADRES_EKLE_SOYAD, adressSoyad);
        click(ADRES_EKLE_IL);
        click(By.xpath("//option[contains(text(),'" + adressIl + "')]"));
        click(ADRES_EKLE_ILCE);
        click(By.xpath("//option[@value='" + adressIlce + "']"));
        click(ADRES_EKLE_SEMT);
        click(By.xpath("//*[@id=\"Address_CityCountyDistrictId\"]/option[" + adressSemt + "]"));
        enterText(ADRES_EKLE_ADRES, adressDetay);
        enterText(ADRES_EKLE_BAŞLIK, adressTitle);
        enterText(ADRES_EKLE_MAIL, adressMail);
        click(ADRES_EKLE_TELEFON);
        adreesPagePhoneNumber(phoneNumber);
    }

    public static void adressRandomCreateMweb(String[] testData) {
        // MWEB adres oluştururken kullanılır.
        enterText(ADRES_EKLE_AD, testData[0]);
        enterText(ADRES_EKLE_SOYAD, testData[1]);
        click(ADRES_EKLE_IL);
        String adressIl = testData[2];
        click(By.xpath("//option[contains(text(),'" + adressIl + "')]"));
        click(ADRES_EKLE_ILCE);
        String adressIlce = testData[3];
        click(By.xpath("//option[@value='" + adressIlce + "']"));
        click(ADRES_EKLE_SEMT);
        String adressSemt = testData[4];
        click(By.xpath("//*[@id=\"Address_CityCountyDistrictId\"]/option[" + adressSemt + "]"));
        enterText(ADRES_EKLE_ADRES, testData[5]);
        enterText(ADRES_EKLE_BAŞLIK, testData[6]);
        enterText(ADRES_EKLE_MAIL, testData[7]);
        click(ADRES_EKLE_TELEFON);
        adreesPagePhoneNumber(testData[8]);
    }

    public static void adressCreateWeb(String[] testData) {
        // WEB adres oluştururken kullanılır.
        enterText(ADRES_EKLE_AD_WEB, testData[0]);
        enterText(ADRES_EKLE_SOYAD_WEB, testData[1]);
        click(ADRES_EKLE_IL_WEB);
        click(By.xpath("//div[@class='row']//option[@value='41'][contains(text(),'İstanbul')]"));
        click(ADRES_EKLE_ILCE_WEB);
        click(By.xpath("//div[@id='AddAddressGeneric']//option[@value='498'][contains(text(),'Kadıköy')]"));
        click(ADRES_EKLE_SEMT_WEB);
        click(By.xpath("//option[@value='1059']"));
        enterText(ADRES_EKLE_ADRES_WEB, testData[5]);
        enterText(ADRES_EKLE_BAŞLIK_WEB, testData[6]);
        enterText(ADRES_EKLE_MAIL_WEB, testData[7]);
        click(ADRES_EKLE_TELEFON_WEB);
        adreesPagePhoneNumberWeb(testData[8]);
    }


    public static void deleteMail() {
        // Hotmail'de son gelen maili inbox'dan ve silinmiş maillerden siler.


        try {
            click(HOTMAIL_SELECT_ALL_INBOX);
            click(HOTMAIL_DELETE_ALL_INBOX);

            if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_S)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_S);
            } else if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_V)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_V);
            } else if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_F)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_F);
            }

            click(HOTMAIL_DELETED_ITEMS);
            click(HOTMAIL_SELECT_ALL_INBOX_COP);
            click(HOTMAIL_DELETE_ALL_INBOX);

            if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_2_S)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_2_S);
            } else if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_2_V)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_2_V);
            } else if (elementVisibiltyWithSize(HOTMAIL_DELETE_LAST_MAIL_OK_2_F)) {
                click(HOTMAIL_DELETE_LAST_MAIL_OK_2_F);
            }
        } catch (Exception e) {
            LOGGER.info("Mail silinemedi.");
        }
    }

    public static void uyelikBilgileriUpadte(String ad, String soyad) {

        enterText(UYELIK_BILGILERI_ADINIZ_MWEB, ad);
        enterText(UYELIK_BILGILERI_SOYADINIZ_MWEB, soyad);
        pageScroollDownToTargetElement(UYELIK_BILGILERI_KAYDET_BUTTON);
        click(UYELIK_BILGILERI_INAKTIF_CINSIYET_MWEB);


    }

    public static void newIconCheck() {
        // MWEB checkout v2 kapsamında, yeni iconları test eder. Delete icon, artı icon, eksi icon işlevlelerini ve icon designlarını test ederi
        String urlCheck = checkStore(getTabUrl());

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            String firstProduct = getText(SEPET_FIRST_PRODUCT_NAME_MWEB);
            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_DELETE_ICON_DESIGN_1_MWEB), getData("iconUrl.sepetDeleteIconMweb"));
            click(SEPET_URUN_DELETE_ICON_1_MWEB);

            Assert.assertNotEquals(firstProduct, getText(SEPET_FIRST_PRODUCT_NAME_MWEB), "Delete icon çalışmadı, silinmek istenen ürün hala sepette.");

            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_ARTI_ICON_DESING_1_MWEB), getData("iconUrl.sepetArtiIconMweb"));
            click(SEPET_URUN_ARTI_ICON_1_MWEB);
            Assert.assertEquals(getText(SEPET_FIRST_PRODUCT_COUNT_MWEB), "2");

            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_EKSI_ICON_DESING_1_MWEB), getData("iconUrl.sepetEksiIconMweb"));
            click(SEPET_URUN_EKSI_ICON_1_MWEB);
            Assert.assertEquals(getText(SEPET_FIRST_PRODUCT_COUNT_MWEB), "1");
            LOGGER.info("Delete,Eksi,Artı buttınları işlev ve image test yapıldı.");
        } else if (urlCheck.equals("fitmoda")) {

            String firstProduct = getText(SEPET_FIRST_PRODUCT_NAME_MWEB_FITMODA);
            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_DELETE_ICON_DESIGN_1_MWEB_FITMODA), getData("iconUrl.sepetDeleteIconMweb"));
            click(SEPET_URUN_DELETE_ICON_1_MWEB_FITMODA);

            Assert.assertNotEquals(firstProduct, getText(SEPET_FIRST_PRODUCT_NAME_MWEB_FITMODA), "Delete icon çalışmadı, silinmek istenen ürün hala sepette.");

            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_ARTI_ICON_DESING_1_MWEB_FITMODA), getData("iconUrl.sepetArtiIconMweb"));
            click(SEPET_URUN_ARTI_ICON_1_MWEB_FITMODA);
            Assert.assertEquals(getText(SEPET_FIRST_PRODUCT_COUNT_MWEB_FITMODA), "2");

            Assert.assertEquals(getSepetIconSvg(SEPET_URUN_EKSI_ICON_DESING_1_MWEB_FITMODA), getData("iconUrl.sepetEksiIconMweb"));
            click(SEPET_URUN_EKSI_ICON_1_MWEB_FITMODA);
            Assert.assertEquals(getText(SEPET_FIRST_PRODUCT_COUNT_MWEB_FITMODA), "1");
            LOGGER.info("Delete,Eksi,Artı buttınları işlev ve image test yapıldı.");
        }
    }

    public static void plusCountInCard(int seq, int num) {
        // MWEB için, sepette, seq değeri için hangi sıradaki ürünün, num değeri ile kaç adet arttırlacağını belirler.

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


    public static void minusCountInCard(int seq, int num) {
        // MWEB için, sepette, seq değeri için hangi sıradaki ürünün, num değeri ile kaç adet azaltılacağını belirler.

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

    public static void krediKartiSecenegiFieldCheck() {
        // MWEB chekout V2 kapsamında, ödeme sayfası kredi kartı seçeneğinde, tüm alanları kontrol eder.
        String urlCheck = checkStore(getTabUrl());
        //Text alanları
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_EKLE_TEXT));
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            softAssert.assertEquals(getText(ODEME_PAGE_KAYITLI_KARTINIZ_YOK_TEXT), "Kayıtlı kartınız bulunmamaktadır.");
        } else if (urlCheck.equals("fitmoda")) {
            softAssert.assertEquals(getText(ODEME_PAGE_KAYITLI_KARTINIZ_YOK_TEXT_FITMODA), "Kayıtlı kartınız bulunmamaktadır.");
        }
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_UZERINDEKI_ISIM_SOYISIM_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_NUMARASI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_SON_KULLANMA_TARIHI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_GUVENLIK_KODU_TEXT));
        //    softAssert.assertEquals(getText(ODEME_PAGE_KART_TAKSIT_SECENEKLERI_KART_BILGILERINI_GIRDIKTEN_TEXT), "Taksit Seçenekleri kart bilgilerini girdikten sonra görüntülenecektir.");
        softAssert.assertEquals(getText(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD), "Kredi kartı bilgilerimi sonraki alışverişler için kaydetmek istiyorum");
        softAssert.assertEquals(getText(ODEME_PAGE_KART_SECURE_3D_ILE_ODEMEK_ISTIYORUM_TEXT), "3D Secure ile ödemek istiyorum");
        softAssert.assertEquals(getText(ODEME_PAGE_SOZLESME_ONAY_MWEB), "Ön Bilgilendirme Formu’nu ve Mesafeli Satış Sözleşmesi’ni okudum ve kabul ediyorum.");
        softAssert.assertEquals(getText(ODEME_PAGE_KART_KAYITLI_KARTINIZ_BULUNMAMAKTADIR_TEXT), "Kayıtlı kartınız bulunmamaktadır.");

        //   softAssert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT), "Kredi kartı taksit seçenekleri, kart bilgileri girmeden görüldü.");
        //   softAssert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD), "Kredi kartı taksit seçenekleri, kart bilgileri girmeden görüldü.");

    }

    public static void krediKartiSecenegiFieldCheckWeb() {
        // WEB chekout V2 kapsamında, ödeme sayfası kredi kartı seçeneğinde, tüm alanları kontrol eder.
        String urlCheck = checkStore(getTabUrl());

        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_UZERINDEKI_ISIM_SOYISIM_TEXT_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_NUMARASI_TEXT_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_SON_KULLANMA_TARIHI_TEXT_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_GUVENLIK_KODU_TEXT_WEB));
        Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD), "Kredi kartı bilgilerimi sonraki alışverişler için kaydetmek istiyorum");
        Assert.assertEquals(getText(ODEME_PAGE_KART_3D_SECURE_KULLAN_TEXT), "3D Secure kullan");
        Assert.assertEquals(getText(ODEME_PAGE_SOZLESME_ONAY_WEB), "Ön Bilgilendirme Formu’nu ve Mesafeli Satış Sözleşmesi’ni okudum ve kabul ediyorum.");

        Assert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT_WEB));

    }


    public static void krediKartiSecenegiFieldCheckGuest() {
        // MWEB chekout V2 kapsamında, ödeme sayfası kredi kartı seçeneğinde, tüm alanları kontrol eder.
        String urlCheck = checkStore(getTabUrl());
        //Text alanları
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_EKLE_TEXT));
        if (!ConfigManager.isHeadless()) {
            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                softAssert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KAYITLI_KARTINIZ_YOK_TEXT), "Kayıtlı kartınız bulunmamaktadır. texti görüldü");
            } else if (urlCheck.equals("fitmoda")) {
                softAssert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KAYITLI_KARTINIZ_YOK_TEXT_FITMODA), "Kayıtlı kartınız bulunmamaktadır. texti görüldü");
            }
        }
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_UZERINDEKI_ISIM_SOYISIM_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_NUMARASI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_SON_KULLANMA_TARIHI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_GUVENLIK_KODU_TEXT));
        //    softAssert.assertEquals(getText(ODEME_PAGE_KART_TAKSIT_SECENEKLERI_KART_BILGILERINI_GIRDIKTEN_TEXT), "Taksit Seçenekleri kart bilgilerini girdikten sonra görüntülenecektir.");
        softAssert.assertFalse(elementVisibiltyWithSize(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD), "Kredi kartı bilgilerimi sonraki alışverişler için kaydetmek istiyorum. texti görüldü");
        softAssert.assertEquals(getText(ODEME_PAGE_KART_SECURE_3D_ILE_ODEMEK_ISTIYORUM_TEXT), "3D Secure ile ödemek istiyorum");
        softAssert.assertEquals(getText(ODEME_PAGE_SOZLESME_ONAY_MWEB), "Ön Bilgilendirme Formu’nu ve Mesafeli Satış Sözleşmesi’ni okudum ve kabul ediyorum.");

        //   softAssert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT), "Kredi kartı taksit seçenekleri, kart bilgileri girmeden görüldü.");
        //   softAssert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD), "Kredi kartı taksit seçenekleri, kart bilgileri girmeden görüldü.");


    }


    //    public static void krediKartiSecenegiFieldCheckWeb() {
//        // WEB chekout V2 kapsamında, ödeme sayfası kredi kartı seçeneğinde, tüm alanları kontrol eder.
//        String urlCheck = checkStore(getTabUrl());
//
//        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_UZERINDEKI_ISIM_SOYISIM_TEXT_WEB));
//        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_NUMARASI_TEXT_WEB));
//        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_SON_KULLANMA_TARIHI_TEXT_WEB));
//        Assert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_KART_GUVENLIK_KODU_TEXT_WEB));
//        Assert.assertEquals(getText(ODEME_PAGE_KART_TAKSIT_SECENEKLERI_KART_BILGILERINI_GIRDIKTEN_TEXT_WEB), "Taksit Seçenekleri kart bilgilerini girdikten sonra görüntülenecektir.");
//        Assert.assertEquals(getText(ODEME_PAGE_KREDI_KARTINI_KAYDET_FIELD), "Kredi kartı bilgilerimi sonraki alışverişler için kaydetmek istiyorum");
//        Assert.assertEquals(getText(ODEME_PAGE_KART_3D_SECURE_KULLAN), "3D Secure kullan");
//        Assert.assertEquals(getText(ODEME_PAGE_SOZLESME_ONAY_WEB), "Ön Bilgilendirme Formu’nu ve Mesafeli Satış Sözleşmesi’ni okudum ve kabul ediyorum.");
//
//        Assert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_TEXT_WEB));
//        Assert.assertFalse(elementVisibilty(ODEME_PAGE_KREDI_KARTI_TAKSIT_SECENEKLERI_FIELD_WEB));
//
//
//    }
    public static void krediKartiEnterText(String adSoyad, String kartNo, String ay, String yil, String cvv) {
        // Mweb ödeme sayfası kredi kartı seçenğinde, alanları doldurur.


        enterText(ODEME_PAGE_KREDI_KART_AD_SOYAD_TEXTBOX, adSoyad);
        enterText(ODEME_PAGE_KREDI_KART_NO_TEXTBOX, kartNo);
        //  click(ODEME_PAGE_KREDI_KART_AY_DROPDOWN);
        click(By.xpath("//option[@value='" + ay + "']"));
        //      click(ODEME_PAGE_KREDI_KART_YIL_DROPDOWN);
        click(By.xpath("//option[@value='" + yil + "']"));
        enterText(ODEME_PAGE_KREDI_KART_CVV_TEXTBOX, cvv);
        Assert.assertEquals(getCardTypeValue(ODEME_PAGE_KREDI_KART_NO_TEXTBOX), "mastercard");

    }

    public static void krediKartiEnterTextWeb(String adSoyad, String kartNo, String ay, String yil, String cvv) {
        // Mweb ödeme sayfası kredi kartı seçenğinde, alanları doldurur.


        enterText(ODEME_PAGE_KREDI_KART_AD_SOYAD_TEXTBOX, adSoyad);
        enterText(ODEME_PAGE_KREDI_KART_NO_TEXTBOX, kartNo);
        //  click(ODEME_PAGE_KREDI_KART_AY_DROPDOWN);
        click(By.xpath("//option[@value='" + ay + "']"));
        //      click(ODEME_PAGE_KREDI_KART_YIL_DROPDOWN);
        click(By.xpath("//option[@val='" + yil + "']"));
        enterText(ODEME_PAGE_KREDI_KART_CVV_TEXTBOX, cvv);
        //Assert.assertEquals(getCardTypeValue(ODEME_PAGE_KREDI_KART_NO_TEXTBOX), "mastercard");

    }


    public static void iyzitoTestKart() {
        // Kredi kartı alanları doldurma methoduna, iyzico test kartı değerlerini girer.

        krediKartiEnterText(getData("iyzicoTestKart.adSoyad"), getData("iyzicoTestKart.kartNo"), getData("iyzicoTestKart.ay"), getData("iyzicoTestKart.yil"), getData("iyzicoTestKart.cvv"));
    }

    public static void iyzitoTestKartWeb() {
        // Kredi kartı alanları doldurma methoduna, iyzico test kartı değerlerini girer.

        krediKartiEnterTextWeb(getData("iyzicoTestKart.adSoyad"), getData("iyzicoTestKart.kartNo"), getData("iyzicoTestKart.ay"), getData("iyzicoTestKart.yil"), getData("iyzicoTestKart.cvv"));
    }


    public static void havaleSecenegiFieldCheck() {
        // MWEB chekout V2 kapsamında, ödeme sayfası havale seçeneğinde, tüm alanları kontrol eder.

        //Text alanları
        softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_ONCE_BANKA_SECINIZ_UYARI), "Sipariş tutarını göndermek istediğiniz bankayı seçiniz.");
        softAssert.assertTrue(elementVisibilty(ODEME_PAGE_HAVALE_YAPILACAK_BANKA_TEXT));
        softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_DIKKAT_EDILMESI_GEREKENLER_TEXT), "Dikkat Edilmesi Gerekenler\nSiparişinizi gönderebilmemiz için seçtiğiniz hesabımıza 7 gün içerisinde havale yapmanız gereklidir. Aksi halde siparişiniz iptal edilecektir.\nÖdemenizin işleme alınması için havale esnasında açıklama alanına sipariş tamamlandıktan sonra oluşan sipariş numaranızı mutlaka belirtmelisiniz.");

    }


    public static void havaleBilgileriCheck() {
        // MWEB chekout V2 kapsamında, ödeme sayfası havale seçeneğinde test olarak Garanti bankası bilgilerini test eder.

        //Text alanları
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_HAVALE_HESAP_ADI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_HAVALE_IBAN_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_HAVALE_HESAP_NO_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_HAVALE_SUBE_ADI_TEXT));
        softAssert.assertTrue(elementVisibiltyWithSize(ODEME_PAGE_HAVALE_SUBE_KODU_TEXT));

        // ŞU AN SADECE GARANTIYI KONTROL EDIYORUZ. GEREKTIGINDE DINAMIK YAPARIZ
        String urlCheck = checkStore(getTabUrl());

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_HESAP_ADI), "Dikey Vitamin Kozmetik ve Gıda Takviyeleri Pazarlama Ticaret A.Ş");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_IBAN), "TR67 0006 2000 4480 0006 2775 40");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_HESAP_NO), "6277540");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_SUBE_ADI), "Maslak Bulvar Şubesi");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_SUBE_KODU), "448");
        } else if (urlCheck.equals("fitmoda")) {
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_HESAP_ADI_FITMODA), "Dikey Vitamin Kozmetik ve Gıda Takviyeleri Pazarlama Ticaret A.Ş");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_IBAN_FITMODA), "TR67 0006 2000 4480 0006 2775 40");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_HESAP_NO_FITMODA), "6277540");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_SUBE_ADI_FITMODA), "Maslak Bulvar Şubesi");
            softAssert.assertEquals(getText(ODEME_PAGE_HAVALE_SUBE_KODU_FITMODA), "448");
        }
    }

    public static void siparisTamamlaFormCheckBoxClick() {
        // Mweb ödeme sayfasında, onay formu checkbox'ı clicklenemediği için, element içinde koordinata göre clickler.

        String urlCheck = checkStore(getTabUrl());
        WebElement label = DriverManager.getDriver().findElement(ODEME_PAGE_SATIS_FORM_FIELD);
        if (urlCheck.equals("supplementler")) {
            sleepInSeconds(1);
            new Actions(DriverManager.getDriver()).moveToElement(label).moveByOffset(180, 2).click().perform(); //Checkbox tıklanamadığı için, elemtentte sözleşme linkleri olmayan bir koordinata tıkladık
        } else if (urlCheck.equals("vitaminler")) {
            sleepInSeconds(1);
            new Actions(DriverManager.getDriver()).moveToElement(label).moveByOffset(140, 2).click().perform(); //Checkbox tıklanamadığı için, elemtentte sözleşme linkleri olmayan bir koordinata tıkladık

        } else if (urlCheck.equals("fitmoda")) {
            sleepInSeconds(1);
            new Actions(DriverManager.getDriver()).moveToElement(label).moveByOffset(160, 2).click().perform(); //Checkbox tıklanamadığı için, elemtentte sözleşme linkleri olmayan bir koordinata tıkladık
        }
    }


    public static void alisverisKredisiLimitPass() {
        // Mweb ödeme testinde, alışveriş kredisi test edilmesi için, eğer 400 tl limite ulaşılmadı uyarısı alırsa, sepete gönder, ürünleri arttırır. 400'ü geçer ise, devam eder ve alışveriş kredisini kontrol eder.

        String urlCheck = checkStore(getTabUrl());

        odemeSecenekleriSecimOpen("Alışveriş Kredisi");

        if (!elementVisibiltyWithSize(ODEME_PAGE_ONBILGILENDIRME_FORMU)) {
            softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.alisverisKredisiLimit"), "Alışveriş kredisi uyarı metni beklenenden farklı.");
            click(ALERT_TAMAM);
            scrollPageToTop();
            click(TOP_SEPET_ICON);
            String toplamTutar = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
            softAssert.assertTrue(Integer.parseInt(getPriceWithoutComma(toplamTutar)) < 400, "Sepet tutarı 400 ve üzeri oluğu halde alışveriş kredisi limiti uyarısı alındı.");

            for (int i = 1; i < 4; i++) {
                if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                    click(SEPET_URUN_ARTI_ICON_1_MWEB);
                } else if (urlCheck.equals("fitmoda")) {
                    click(SEPET_URUN_ARTI_ICON_1_MWEB_FITMODA);
                }
                i++;
            }

            if (Integer.parseInt(getPriceWithoutComma(getText(SEPET_TOPLAM_TUTAR_MWEB_V2))) > 400) {
                click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
                sepetSonrasiHediyeEkraniGecMweb();
                if (!elementVisibiltyWithSize(SEPET_STOK_UYARI_MESAJI_MWEB)) {

                    click(ADRES_PAGE_DEVAM_ET_BUTTON_ID);
                    odemeSecenekleriSecimOpen("Alışveriş Kredisi");
                    sleepInSeconds(2);

                    softAssert.assertTrue(getImageDisplayStatus(ODEME_PAGE_ALISVERIS_KREDISI_IMAGE), "Alışveriş kredisi görseli kırık.");

                    click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
                    softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"), "Form onaylanmadan devam edilemez metni beklenenden farklı.");
                    click(ALERT_TAMAM);

                    click(ODEME_PAGE_SATIS_FORM_FIELD);

                    // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
                    odemeSayfaFormsCheck();

                    scrollPageToTop();
                    click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);

                    odemeSecenekleriSecimClose("Alışveriş Kredisi");

                } else if (elementVisibiltyWithSize(SEPET_STOK_UYARI_MESAJI_MWEB)) {
                    LOGGER.info("Stok sınırına ulaşıldı. Test tamamlandı. Alışveriş kredisi test edilemedi.");
                    click(SEPET_STOK_UYARI_MESAJI_MWEB);
                }

            } else {
                LOGGER.info("Sepet tutarı 400 TL sınırına ulaşılamadı. Alışveriş kredisi test edilemedi.");
            }

        } else {
            sleepInSeconds(2);
            // softAssert.assertTrue(getImageDisplayStatus(ODEME_PAGE_ALISVERIS_KREDISI_IMAGE), "Alışveriş kredisi görseli kırık.");

            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
            softAssert.assertEquals(getText(UYARI_POPUP), getData("uyariMessage.formOnayla"), "Form onaylanmadan devam edilemez metni beklenenden farklı.");
            click(ALERT_TAMAM);

            click(ODEME_PAGE_SATIS_FORM_FIELD);

            // Ön bilgilendirme ve mesafeli satış sözleşmelerinin açıldığını ve formun doğru olduğunu test eder.
            odemeSayfaFormsCheck();

            scrollPageToTop();
            click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);
            odemeSecenekleriSecimClose("Alışveriş Kredisi");
        }
        softAssert.assertAll();
    }


    public static void siparisAlindiPageCheck() {
        // MWEB sipariş alındı sayfasında, havale yöntemi için alanları test eder.

        String urlCheck = checkStore(getTabUrl());
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZ_TAMAMLANDI_TEXT_MWEB));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT));
        Assert.assertTrue(Integer.parseInt(getText(SIPARIS_ALINDI_PAGE_SIPARIS_NUMARASI)) > 7);
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_HAVALE_SIPARIS_NO_INFO), getData("textControl.havaleSipariNoInfo"));

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_SIPARIS_ODEME_YONTEMI), "Havale");
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_SIPARIS_ODEME_YONTEMI_FITMODA), "Havale");
        }

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_KAZANILAN_PUAN_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_KAZANILAN_PUAN_BOTTOM_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARIS_DETAYI_BUTTON));

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_FIELD));

        if (urlCheck.equals("supplementler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        } else if (urlCheck.equals("vitaminler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_VITAMINLER), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_VITAMINLER), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_VITAMINLER), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_FITMODA), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_FITMODA), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_FITMODA), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI_FITMODA), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        }

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_1_ITEM));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_ADRES_2_FIELD));


    }


    public static void agtTimeGetText(By by) {

        LOGGER.info(getText(ADRES_PAGE_AGT_TESLIMAT_AKTIF_1));
        secilenAGT = getText(ADRES_PAGE_AGT_TESLIMAT_AKTIF_1);
    }


    public static void siparisAlindiPageCheckWeb() {
        // WEB sipariş alındı sayfasında, havale yöntemi için alanları test eder.

        String urlCheck = checkStore(getTabUrl());
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZ_TAMAMLANDI_TEXT_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT_WEB));
        Assert.assertTrue(Integer.parseInt(getText(SIPARIS_ALINDI_PAGE_SIPARIS_NUMARASI_WEB)) > 7);
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_HAVALE_SIPARIS_NO_INFO_WEB), getData("textControl.havaleSipariNoInfo"));


        //Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_SIPARIS_ODEME_YONTEMI_WEB), "Havale");


        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_KAZANILAN_PUAN_FIELD_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_KAZANILAN_PUAN_BOTTOM_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT_WEB));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_SATIN_ALDIGINIZ_URUNLER_TEXT_WEB));

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_HAVALE_ALERT_WEB));


        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_WEB), getData("havaleInfo.ibanGaranti"));
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_WEB), getData("havaleInfo.hesapNoGaranti"));
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_WEB), getData("havaleInfo.subeKoduGaranti"));
        //Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI_WEB), getData("havaleInfo.hesapAdiGaranti"));
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI_WEB), getData("havaleInfo.subeAdiGaranti"));

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_TESLIMAT_SAATI_WEB), secilenAGT);
            LOGGER.info(getText(SIPARIS_ALINDI_PAGE_TESLIMAT_SAATI_WEB));
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_TESLIMAT_SAATI_WEB_FITMODA), secilenAGT);
        }


        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_FIELD_WEB));
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_1_ITEM_WEB));
            Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_ADRES_2_FIELD_WEB));
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_1_ITEM_WEB_FITMODA));
            Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_ADRES_2_FIELD_WEB_FITMODA));
        }


    }


    public static void siparisAlindiPageCheckGuest() {
        // MWEB sipariş alındı sayfasında, havale yöntemi için alanları test eder.

        String urlCheck = checkStore(getTabUrl());
        sleepInSeconds(1);
        Assert.assertEquals(getText(SIPARIS_ALINDI_SIPARISINIZ_TAMAMLANDI_TEXT_MWEB), "Siparişiniz Başarıyla Tamamlandı");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT));
        Assert.assertTrue(Integer.parseInt(getText(SIPARIS_ALINDI_PAGE_SIPARIS_NUMARASI)) > 7);
        Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_HAVALE_SIPARIS_NO_INFO), getData("textControl.havaleSipariNoInfo"));

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_SIPARIS_ODEME_YONTEMI), "Havale");
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_SIPARIS_ODEME_YONTEMI_FITMODA), "Havale");
        }

        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_KAZANILAN_PUAN_FIELD), "Kazanılan puan alanı guest müşteri için görünmemeli.");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_KAZANILAN_PUAN_BOTTOM_FIELD), "Kazanılan puan bottom alanı guest müşteri için görünmemeli.");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_TEXT), "Siparişinizi buradan takip edebilirsiniz. görüldü.Guest müşteri görmemeli.");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARISINIZI_BURADAN_TAKIP_EDEBILIRSINIZ_LINK), "Siparişinizi buradan takip edebilirsiniz. linki görüldü.Guest müşteri görmemeli.");
        Assert.assertFalse(elementVisibiltyWithSize(SIPARIS_ALINDI_SIPARIS_DETAYI_BUTTON), "Sipariş detayı buttonu görüldü.Guest müşteri görmemeli.");

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_FIELD));

        if (urlCheck.equals("supplementler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_GUEST), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_GUEST), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_GUEST), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI_GUEST), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        } else if (urlCheck.equals("vitaminler")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_VITAMINLER_GUEST), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_VITAMINLER_GUEST), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_VITAMINLER_GUEST), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI_GUEST), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_IBAN_FITMODA_GUEST), getData("havaleInfo.ibanGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_NO_FITMODA_GUEST), getData("havaleInfo.hesapNoGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_KODU_FITMODA_GUEST), getData("havaleInfo.subeKoduGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_HESAP_ADI_FITMODA_GUEST), getData("havaleInfo.hesapAdiGaranti"));
            Assert.assertEquals(getText(SIPARIS_ALINDI_PAGE_BANKA_BILGILERI_SUBE_ADI), getData("havaleInfo.subeAdiGaranti"));
        }

        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_FIELD));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_URUNLER_1_ITEM));
        Assert.assertTrue(elementVisibiltyWithSize(SIPARIS_ALINDI_PAGE_ADRES_2_FIELD));

    }

    public static void progressBarBasketCheckMweb() {
        // Progress barın aktif tabın adını, number'ını test eder. Aktif olmayan iki tab'ın numberlarını check ederek göründüklerini test eder

        String step = checkBasketStep(getTabUrl());
        Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR), "Sepet progress bar görülmedi.");

        switch (step) {
            case "cart":
                Assert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_TAB), "Sepetim");
                softAssert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_NUMBER), "1", "Progress bar aktif sekme number görülmedi.");

                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_2), "Progress bar 2. sekme görülmedi.");
                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_3), "Progress bar 3. sekme görülmedi.");
                break;
            case "billingaddress":
                Assert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_TAB), "Adres");
                softAssert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_NUMBER), "2", "Progress bar aktif sekme number görülmedi.");

                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_1), "Progress bar 1. sekme görülmedi.");
                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_3), "Progress bar 3. sekme görülmedi.");
                break;
            case "pay":
                Assert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_TAB), "Ödeme");
                softAssert.assertEquals(getText(SEPET_PROGRESS_BAR_AKTIF_NUMBER), "3", "Progress bar aktif sekme number görülmedi.");

                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_1), "Progress bar 1. sekme görülmedi.");
                Assert.assertTrue(elementVisibiltyWithSize(SEPET_PROGRESS_BAR_NUMBER_2), "Progress bar 2. sekme görülmedi.");
                break;
        }
    }


    public static void hataliKuponKoduTest(String couponCode) {
        String urlCheck = checkStore(getTabUrl());

        String totalPriceFull = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        String totalPriceOld = totalPriceFull.split(" ")[0];
        LOGGER.info("İndirim öncesi toplam tutar: " + totalPriceOld);


        scrollToElementCenter(SEPET_KUPON_KODU_CHECKBOX_MWEB); // Fitmoda ekranın altında kalan elemente tıklayamama sorunu çözümü için

        click(SEPET_KUPON_KODU_CHECKBOX_MWEB);

        enterText(SEPET_KUPON_KODU_TEXTBOX, couponCode);
        LOGGER.info("Kupon kodu olarak girildi: " + couponCode);

        click(SEPET_INDIRIMI_KULLAN_BUTTON);

        String errorMessage = getText(SEPET_HATA_MESAJI_MWEB);
        Assert.assertEquals(errorMessage, getData("errorMessage.KuponKoduHatası"));

        sleepInSeconds(2);
        String totalPriceFull_new = getText(SEPET_TOPLAM_TUTAR_MWEB_V2);
        String totalPriceNew = totalPriceFull_new.split(" ")[0];
        //   Assert.assertEquals(totalPriceNew, totalPriceOld);
    }

    public static void adresPageAdresFieldCheck() {
        String urlCheck = checkStore(getTabUrl());

        // Teslimat adresi alanının görüldüğü - Faturam sipariş ile birlikte gönderilsin alanının active olduğunu test eder
        if (urlCheck.equals("supplementler")) {
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRESI_WRAPPER));
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group active currently-supp-checkbox-wrapper", "Faturam sipariş ile birlikte gönderilsin alanı active olarak gelmedi.");

        } else if (urlCheck.equals("vitaminler")) {
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRESI_WRAPPER_VITAMINLER));
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group active currently-supp-checkbox-wrapper currently-vit-checkbox-wrapper", "Faturam sipariş ile birlikte gönderilsin alanı active olarak gelmedi.");
        } else if (urlCheck.equals("fitmoda")) {
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRESI_WRAPPER_FITMODA));
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group active currently-supp-checkbox-wrapper currently-fit-checkbox-wrapper", "Faturam sipariş ile birlikte gönderilsin alanı active olarak gelmedi.");
        }
        // Fatura adresi alanının görülmediğini test eder
        softAssert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN), "display: none;", "Fatura adresi alanı görüldü. Test kapsamında kapalı olarak gelmedi gerek.");
        // aturam sipariş ile birlikte gönderilsin textini test eder
        softAssert.assertEquals(getText(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "Faturam sipariş ile birlikte gönderilsin.");

        // Fatura adresi alanının açıldığını test eder
        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        sleepInSeconds(1);
        softAssert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN), "display: block;");

        // Fatura adresi alanının görüldüğü - Faturam sipariş ile birlikte gönderilsin alanının active olmadığını test eder
        if (urlCheck.equals("supplementler")) {
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group currently-supp-checkbox-wrapper");
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_FATURA_ADRESI_WRAPPER));
        } else if (urlCheck.equals("vitaminler")) {
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group currently-supp-checkbox-wrapper currently-vit-checkbox-wrapper");
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_FATURA_ADRESI_WRAPPER_VITAMINLER));
        } else if (urlCheck.equals("fitmoda")) {
            softAssert.assertEquals(getClassValue(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_WRAPPER), "dikey-group currently-supp-checkbox-wrapper currently-fit-checkbox-wrapper");
            Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_FATURA_ADRESI_WRAPPER_FITMODA));
        }

        // Fatura adresi alanının kapandığını test eder
        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        sleepInSeconds(1);
        softAssert.assertEquals(getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN), "display: none;", " Fatura adresi alanı kapanmadı.");

    }

    public static void adresPageTeslimatAdresDuzenle(String mail) {
        softAssert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_MWEB), "Yeni adres ekle buttonu görülmedi.");
        click(ADRES_PAGE_YENI_ADRES_EKLE_BUTTON_MWEB);

        softAssert.assertTrue(elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON), "Bu adresi kullan buttonu görülmedi.");

        adressRandomCreateMweb(randomAdressData(mail));
        DriverManager.getDriver().switchTo().activeElement().sendKeys(Keys.TAB);

        click(ADRES_EKLE_KAYDET_BUTTON);

        adresPageAdresTextCheck(mail);

        //Test sonunda fatura adresia alanı kapatılır
        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
    }

    public static String[] randomAdressData(String mail) {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String address = faker.address().fullAddress();
        String adresTitle = faker.lorem().word();
        String randomNumber = faker.number().digits(2);
        //     String phone = "3" + faker.number().digits(8);

        myData("name", name);
        myData("surname", surname);
        myData("address", address);
        myData("adresTitle" + mail, "Adres" + randomNumber);

        String[] testData = {name, surname, "İstanbul", "498", "3", address, "Adres" + randomNumber, getData("userData.email" + mail + ""), "375778414"};
        return testData;
    }

    public static String[] randomAdressDataGuest(String mail) {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String address = faker.address().fullAddress();
        String adresTitle = faker.lorem().word();
        String randomNumber = faker.number().digits(2);
        //     String phone = "3" + faker.number().digits(8);

        myData("name", name);
        myData("surname", surname);
        myData("address", address);
        myData("adresTitle", "Adres" + randomNumber);
        //  myData("phone", phone);

        String[] testData = {name, surname, "İstanbul", "498", "3", address, "Adres" + randomNumber, getData("userData.email" + mail + ""), "375778414"};
        return testData;
    }

    public static void adresPageAdresTextCheck(String mail) {

        softAssert.assertEquals(getText(ADRES_PAGE_TESLIMAT_ADRES_BASLIK), myMapString.get("adresTitle" + mail), "Son eklenen adres, aktif teslimat adres olarak görülmedi.");
        click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        softAssert.assertEquals(getText(ADRES_PAGE_FATURA_ADRES_BASLIK), myMapString.get("adresTitle" + mail), "Son eklenen adres, aktif fatura adres olarak görülmedi.");

        // Diğer alanların elementi alıp, regex methodu ekleyip oradan check etmek gerekli
    }

    public static void faturaTeslimatAdresleriFarkliSecim() {
        if (getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN).equals("display: none;")) {
            click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        }

        myMapString.put("faturaAdresTitle2", getText(ADRES_PAGE_FATURA_ADRES_BASLIK));
        click(ADRES_PAGE_FATURA_ADRES_SECIM);

        click(ADRES_PAGE_FATURA_ADRES_SECIM_1);

        softAssert.assertNotEquals(getText(ADRES_PAGE_FATURA_ADRES_BASLIK), myMapString.get("faturaAdresTitle2"), "Farklı fatura adresi seçilemedi.");
    }

    public static void adresPageAdresDuzenleButtonTest() {
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_TESLIMAT_ADRES_DUZENLE_BUTTON), "Teslimat adres düzenle buttonu görülmedi.");
        click(ADRES_PAGE_TESLIMAT_ADRES_DUZENLE_BUTTON);
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON));
        click(ADRES_EKLE_PAGE_ADRES_SECIMINE_GERI_DON_BUTTON);

        if (getStyleValue(ADRES_PAGE_FATURA_ADRESI_HIDDEN).equals("display: none;")) {
            click(ADRES_PAGE_FATURAM_SIPARIS_ILE_BIRLIKTE_GONDERILSIN_TEXT);
        }

        Assert.assertTrue(elementVisibiltyWithSize(ADRES_PAGE_FATURA_ADRES_DUZENLE_BUTTON), "Teslimat adres düzenle buttonu görülmedi.");
        click(ADRES_PAGE_FATURA_ADRES_DUZENLE_BUTTON);
        Assert.assertTrue(elementVisibiltyWithSize(ADRES_EKLE_PAGE_BU_ADRESI_KULLAN_BUTTON));
        click(ADRES_EKLE_PAGE_ADRES_SECIMINE_GERI_DON_BUTTON);
    }

    public static void standartTeslimatSecimCheck() {
        String urlCheck = checkStore(getTabUrl());

        click(ADRES_PAGE_STANDART_TESLIMAT_WRAPPER);
        if (elementVisibiltyWithSize(ADRES_PAGE_STANDART_TESLIMAT_SECIM_2)) {
            click(ADRES_PAGE_STANDART_TESLIMAT_SECIM_2);
            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                Assert.assertEquals(getClassValue(ADRES_PAGE_STANDART_TESLIMAT_SECIM_2_AKTIFLIK_DURUM), "nd-standard-shipping selected");
                Assert.assertEquals(getClassValue(ADRES_PAGE_STANDART_TESLIMAT_SECIM_1_AKTIFLIK_DURUM), "nd-standard-shipping");
            } else if (urlCheck.equals("fitmoda")) {
                Assert.assertEquals(getClassValue(ADRES_PAGE_STANDART_TESLIMAT_SECIM_2_AKTIFLIK_DURUM_FITMODA), "nd-standard-shipping selected");
                Assert.assertEquals(getClassValue(ADRES_PAGE_STANDART_TESLIMAT_SECIM_1_AKTIFLIK_DURUM_FITMODA), "nd-standard-shipping");
            }

        }
    }

    public static void sepetNotuWeb(String not) {
        String urlCheck = checkStore(getTabUrl());

        if (urlCheck.equals("supplementler") || urlCheck.equals("fitmoda")) {
            scrollToElementCenter(SEPET_NOTU_TEXTBOX);
            enterText(SEPET_NOTU_TEXTBOX, not);
        } else if (urlCheck.equals("vitaminler")) {
            scrollToElementCenter(SEPET_NOTU_TEXTBOX_CSS);
            enterText(SEPET_NOTU_TEXTBOX_CSS, not);
        }
    }

    public static void sepetNotu(String not) {
        click(ADRES_PAGE_SEPET_NOTU_EKLEMEK_ISTIYORUM_TEXT_MWEB);
        softAssert.assertEquals(getPlaceholderValue(SEPET_NOTU_TEXTBOX), "Mesajınızı Bu Alana Ekleyebilirsiniz");
        enterText(SEPET_NOTU_TEXTBOX, not);
    }

    public static void clearUserAdress() {
        boolean urlCheck = checkStringContains(getTabUrl(), "m.");
        if (!urlCheck) {

            // WEB İÇİN EKLENECEK
            click(TOP_SITE_LOGO_WEB);
        } else {
            click(LEFT_MENU_MWEB);
            click(LEFT_MENU_HESABIM_MWEB);
            click(LEFT_MENU_HESABIM_ADRESLERIM_MWEB);

            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
            while (elementVisibiltyWithSize(ADRESLERIM_DELETE_ICON_2_MWEB)) {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ADRESLERIM_DELETE_ICON_2_MWEB));
                element.click();
                DriverManager.getDriver().switchTo().alert().accept();
                //   wait.until(ExpectedConditions.stalenessOf(element));
            }

            int baskteItems = getListSize(ADRESLERIM_SIZE_MWEB);
            Assert.assertEquals(baskteItems, 1);

            LOGGER.info("1 adres test için bırakıldı.");

            click(TOP_SITE_LOGO);
        }
    }

    public static void odemeSecenekleriSecimOpen(String secim) {
        Assert.assertEquals(getListSize(ODEME_PAGE_ODEME_SECENEKLERI_SIZE), 5);

        switch (secim) {
            case "Kredi Kartı":
                click(ODEME_PAGE_KREDI_KARTI_SECENEK);
                sleepInSeconds(1);
                Assert.assertEquals(getDataToogleValue(ODEME_PAGE_KREDI_KARTI_SECENEK_BUTTON), "open");
                break;
            case "Havale":
                click(ODEME_PAGE_HAVALE_SECENEK);
                sleepInSeconds(1);
                Assert.assertEquals(getDataToogleValue(ODEME_PAGE_HAVALE_SECENEK_BUTTON), "open");
                break;
            case "Kapıda Kredi Kartı":
                click(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK);
                sleepInSeconds(1);
                Assert.assertEquals(getDataToogleValue(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK_BUTTON), "open");
                break;
            case "Kapıda Nakit":
                click(ODEME_PAGE_KAPIDA_NAKIT_SECENEK);
                sleepInSeconds(1);
                Assert.assertEquals(getDataToogleValue(ODEME_PAGE_KAPIDA_NAKIT_SECENEK_BUTTON), "open");
                break;
            case "Alışveriş Kredisi":
                click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);
                sleepInSeconds(1);
                break;
        }

    }

    public static void odemeSecenekleriSecimClose(String secim) {
        switch (secim) {
            case "Kredi Kartı":
                click(ODEME_PAGE_KREDI_KARTI_SECENEK);
                softAssert.assertEquals(getDataToogleValue(ODEME_PAGE_KREDI_KARTI_SECENEK_BUTTON), "", "Kredi kartı seçenek alanı kapanmadı."); // KREDI KARTI ALANI KAPANDI
                break;
            case "Havale":
                click(ODEME_PAGE_HAVALE_SECENEK);
                softAssert.assertEquals(getDataToogleValue(ODEME_PAGE_HAVALE_SECENEK_BUTTON), "", "Havale seçenek alanı kapanmadı.");
                break;
            case "Kapıda Kredi Kartı":
                click(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK);
                softAssert.assertEquals(getDataToogleValue(ODEME_PAGE_KAPIDA_KREDI_KARTI_SECENEK_BUTTON), "", "Kapıda Kredi Kartı seçenek alanı kapanmadı.");
                break;
            case "Kapıda Nakit":
                click(ODEME_PAGE_KAPIDA_NAKIT_SECENEK);
                softAssert.assertEquals(getDataToogleValue(ODEME_PAGE_KAPIDA_NAKIT_SECENEK_BUTTON), "", "Kapıda Nakit seçenek alanı kapanmadı.");
                break;
            case "Alışveriş Kredisi":
                click(ODEME_PAGE_ALISVERIS_KREDISI_SECENEK);
                sleepInSeconds(1);
                break;
        }
    }

    public static void odemeSayfaFormsCheck() {

        scrollToElementCenter(ODEME_PAGE_ONBILGILENDIRME_FORMU);

        click(ODEME_PAGE_ONBILGILENDIRME_FORMU);
        switchDriverIframe(ODEME_PAGE_FORM_IFRAME);
        softAssert.assertEquals(getText(ODEME_PAGE_ONBILGILENDIRME_FORMU_BASLIK), "ÖN BİLGİLENDİRME FORMU", "Ön bilgilendirme formu başlık alanı farklı.");
        switchDriverDefault();
        click(ODEME_PAGE_SOZLESME_POPUP_KAPAT_ICON);


        click(ODEME_PAGE_MESAFELISATIS_SOZLESMESI);
        switchDriverIframe(ODEME_PAGE_FORM_IFRAME);
        softAssert.assertEquals(getText(ODEME_PAGE_MESAFELISATIS_SOZLESMESI_BASLIK), "MESAFELİ SATIŞ SÖZLEŞMESİ", "Mesafelei satış sözleşmesi formu başlık alanı farklı.");
        switchDriverDefault();
        click(ODEME_PAGE_SOZLESME_POPUP_KAPAT_ICON);

    }

    public static void havaleBankaLogoCheck() {
        int bankaLogoAdet = getListSize(ODEME_PAGE_HAVALE_BANKA_LIST_SIZE);
        String bankaLogoImage = null;
        String bankaName = null;
        for (int i = 1; i <= bankaLogoAdet; i++) {
            bankaLogoImage = getImageUrl(By.xpath("//*[@id=\"moneytransfer_container_pay\"]/div/div[4]/table[2]/tbody/tr[" + i + "]/th/img"));
            bankaName = getText(By.xpath("//*[@id=\"moneytransfer_container_pay\"]/div/div[4]/table[2]/tbody/tr[" + i + "]"));

            softAssert.assertTrue(getImageDisplayStatus(By.xpath("//*[@id=\"moneytransfer_container_pay\"]/div/div[4]/table[2]/tbody/tr[" + i + "]/th/img")), "Banka logo görseli kırık.");

            switch (bankaName) {
                case "Garanti Bankası":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "garanti", "Banka logo url'i farklı.");
                    break;
                case "İş Bankası":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "isbankasi", "Banka logo url'i farklı.");
                    break;
                case "Akbank":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "akbank", "Banka logo url'i farklı.");
                    break;
                case "Denizbank":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "denizbank", "Banka logo url'i farklı.");
                    break;
                case "Finansbank":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "finansbank", "Banka logo url'i farklı.");
                    break;
                case "PTT":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "ptt", "Banka logo url'i farklı.");
                    break;
                case "Yapı Kredi Bankası":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "yapikredi", "Banka logo url'i farklı.");
                    break;
                case "Ziraat Bankası":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "ziraatbankasi", "Banka logo url'i farklı.");
                    break;
                case "Halk Bankası":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "ziraatbankasi", "Banka logo url'i farklı.");
                    break;
                case "TEB":
                    softAssert.assertEquals(getBankNameFromLogoUrl(bankaLogoImage), "teb", "Banka logo url'i farklı.");
                    break;
            }
        }
    }


    public static void sepetSonrasiHediyeEkraniGecMweb() {
        if (elementVisibiltyWithSize(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_MWEB)) {
            click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_1_MWEB);
        }
        if (elementVisibiltyWithSize(SEPET_HEDIYE_STOK_UYARISI_MWEB)) {
            click(SEPET_HEDIYE_STOK_UYARISI_MWEB);
            click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_2_MWEB);
            if (elementVisibiltyWithSize(SEPET_HEDIYE_STOK_UYARISI_MWEB)) {
                click(SEPET_HEDIYE_STOK_UYARISI_MWEB);
                click(SEPET_SONRASI_HEDIYE_SECIM_PAGE_HEDIYE_3_MWEB);
            }
        }

        if (elementVisibiltyWithSize(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2)) {
            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        }
    }

    public static void sepetSonrasiHediyeEkraniGecWeb() {
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

        if (elementVisibiltyWithSize(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2)) {
            click(SEPET_SIPARISI_TAMAMLA_BUTTON_MWEB_V2);
        }
    }


    public static void userInfoUpdateMweb() {
        String ad = faker.name().firstName();
        String soyad = faker.name().lastName();
        int gun = faker.number().numberBetween(13, 28);
        int ay = faker.number().numberBetween(1, 12);
        int yil = faker.number().numberBetween(1950, 2007);
        enterText(UYELIK_BILGILERI_ADINIZ_MWEB, ad);
        enterText(UYELIK_BILGILERI_SOYADINIZ_MWEB, soyad);
//        String cinsiyet = getText(UYELIK_BILGILERI_INAKTIF_CINSIYET_MWEB);
//        click(UYELIK_BILGILERI_INAKTIF_CINSIYET_MWEB);
        pageScroollDownToTargetElement(UYELIK_BILGILERI_KAYDET_BUTTON);
        click(By.xpath("//option[@value='" + gun + "']"));
        click(By.xpath("//option[normalize-space()='Nisan']"));
        click(By.xpath("//option[@value='" + yil + "']"));
        click(UYELIK_BILGILERI_KVKK);
        scrollToElementCenter(UYELIK_BILGILERI_KAYDET_BUTTON);
        click(UYELIK_BILGILERI_KAYDET_BUTTON);
        Assert.assertEquals(getText(UYELIK_BILGILERINIZ_GUNCELLENDI_MESSAGE), getData("uyariMessage.uyelikBilgisiGuncellendi"));
        click(UYELIK_BILGILERINIZ_GUNCELLENDI_TAMAM_BUTTON);
        Assert.assertEquals(getValue(UYELIK_BILGILERI_ADINIZ_MWEB), ad);
        Assert.assertEquals(getValue(UYELIK_BILGILERI_SOYADINIZ_MWEB), soyad);
//        Assert.assertEquals(getText(UYELIK_BILGILERI_AKTIF_CINSIYET_MWEB),cinsiyet);
    }

    public static String getTutar(By by) {
        String urlCheck = checkStore(getTabUrl());
        String tutarFull = null;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            tutarFull = getText(by);
        } else if (urlCheck.equals("fitmoda")) {
            tutarFull = getText(by);
        }

        String tutar = tutarFull.split(" ")[0];
        LOGGER.info("Tutar: " + tutar);
        return tutar;
    }


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
                Assert.assertEquals(getProductQuantityInBasket(element), Integer.toString(seq));
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
                Assert.assertEquals(getProductQuantityInBasket(element), Integer.toString(seq));
                storeData("quantity" + seq, seq);
            } else {
                LOGGER.info(seq + ". ürün hediye. Adet değiştirilemedi.");
                storeData("quantity" + seq, 1);
            }

        }
    }


    public static int productListPageCategoryProductCount() {
        int total = 0;
        int categorySize = getListSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER_SIZE);
        for (int i = 1; i <= categorySize; i++) {
            By category = By.xpath("//div[@class='product-filters']//div[@class='filterscroll scroll-pane']//li[" + i + "]");
            String text = getText(category);
            LOGGER.info("category text " + text);
            int startIndex = text.indexOf("(");
            int endIndex = text.indexOf(")");
            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                String numberStr = text.substring(startIndex + 1, endIndex);
                try {
                    int number = Integer.parseInt(numberStr);
                    LOGGER.info("number " + number);
                    total += number;
                } catch (NumberFormatException e) {
                    System.out.println("Cannot convert " + numberStr + " to integer.");
                }
            } else {
                System.out.println("Cannot find number in " + text);
            }
        }
        LOGGER.info("total " + total);
        return total;

    }


    public static int productListPageCategoryFilter_1_ProductCount() {

        int number = 0;
        String text = getText(URUN_LISTELEME_PAGE_KATEGORI_FILTRESI_SAYISI_1);
        LOGGER.info("category text " + text);
        int startIndex = text.indexOf("(");
        int endIndex = text.indexOf(")");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String numberStr = text.substring(startIndex + 1, endIndex);
            try {
                number = Integer.parseInt(numberStr);
                LOGGER.info("number " + number);
            } catch (NumberFormatException e) {
                System.out.println("Cannot convert " + numberStr + " to integer.");
            }
        } else {
            System.out.println("Cannot find number in " + text);
        }
        return number;
    }


}
