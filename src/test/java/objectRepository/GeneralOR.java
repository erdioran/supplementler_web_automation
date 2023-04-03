package objectRepository;

import org.openqa.selenium.By;

public class GeneralOR {
    public static final By ALERT_TAMAM = By.xpath("//button[normalize-space()='Tamam']");
    public static final By ALERT_TAMAM_WEB = By.xpath("//input[@value='Tamam']");
    public static final By UYARI_POPUP_TAMAM_BUTTON = By.xpath("//input[@value='Tamam']");
    public static final By UYARI_POPUP = By.xpath("//div[@class='ajs-content']");
    public static final By UYARI_POPUP_WEB = By.xpath("//div[@class='message-container']");//span[@class='fa fa-bars']
    public static final By LEFT_MENU_MWEB = By.xpath("//a[@id='open-menu']");
    public static final By LEFT_MENU_MWEB_VITAMINLER= By.xpath("//span[@class='fa fa-bars']");
    public static final By TOP_SITE_LOGO = By.xpath("//div[@class='logo-top']//a");
    public static final By TOP_SITE_LOGO_WEB = By.xpath("//*[@id=\"headerLinksTop\"]/div[3]/div[1]");
    public static final By TOP_SEPET_ICON = By.xpath("//div[@class='right-cart']");
    public static final By TOP_SEPET_ICON_WEB = By.xpath("//div[@class='cart-inner-info']");
    public static final By TOP_SEPETE_GIT_ICON_WEB = By.xpath("//a[@class='checkout-button btn btn-success']");
    public static final By TOP_HESABIM_ICON_WEB = By.xpath("//i[@class='icon icon-alt-usericon']");
    public static final By TOP_HESABIM_ICON_MWEB = By.id("BodyTop-LoginButton");
    public static final By TOP_KULLANICI_BILGILERIM_BUTTON_WEB = By.xpath("//ul[@id='aut-user-menu']//a[contains(text(),'Kullanıcı Bilgilerim')]");
    public static final By TOP_KULLANICI_BILGILERIM_BUTTON_MWEB = By.xpath("//a[contains(text(),'KULLANICI BİLGİLERİM')]");
    public static final By TOP_BAR_KULLANICI_ADI = By.xpath("//div[@id='HeaderLinks-userShortName']");
    public static final By TOP_BAR_LOGOUT_WEB = By.id("logoutBtn");
    public static final By TOP_BAR_FIT_TEST_SUPPLEMENTLER = By.xpath("//span[normalize-space()='Fit Test']");
    public static final By TOP_BAR_KISISEL_TAVSIYE_VITAMINLER = By.xpath("//span[contains(text(),'Kişisel Tavsiye')]");
    public static final By FIT_TEST_POPUP_CLOSE_ICON = By.xpath("//a[@class='closeFitFinder']");
    public static final By TOP_SEPET_BUTTON_WEB = By.xpath("//a[@class='basketLink']");
    public static final By LEFT_MENU_KATEGORILER_MWEB = By.xpath("//span[normalize-space()='Kategoriler']");
    public static final By LEFT_MENU_HESABIM_MWEB = By.xpath("//span[contains(text(),'Hesabım')]");
    public static final By LEFT_MENU_HESABIM_ADRESLERIM_MWEB = By.xpath("//a[contains(text(),'ADRESLERİM')]");
    public static final By COOKIE_BAR_CLOSE = By.xpath("//div[@class='cookie_container__holder_closeButton']");
    public static final By COOKIE_BAR_CLOSE_MWEB = By.xpath("//div[@class='cookie_container__holder_closeButton']");
    public static final By AKILLI_SIRALAMA_PAHALIDAN_UCUZA = By.xpath("//a[@title='Fiyat Azalan']");
    public static final By FOOTER_FIELD_MWEB = By.xpath("//div[@class='footer-card']");
    public static final By PAGE_STATUS = By.className("code");
    public static final By PAGE_STATUS_MWEB = By.xpath("//h1[contains(text(),'Aradığınız sayfa bulunamadı.')]");



}
