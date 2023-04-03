package objectRepository;

import org.openqa.selenium.By;

public class ProductDetailOR {

    public static final By STICKY_BAR_URUN_DETAY = By.id("sticky-element-2423");
    public static final By STICKY_BAR_INDIRIM_URUN_DETAY = By.id("sticky-discount-percentage-2423");
    public static final By STICKY_BAR_SEPETE_EKLE_BUTTON = By.id("sticky-add-to-cart-btn-2423");
    public static final By STICKY_BAR_INDIRIMLI_FIYAT = By.cssSelector(".new-price-2423");
    public static final By STICKY_BAR_INDIRIMSIZ_FIYAT = By.cssSelector(".old-price-2423");
    public static final By URUN_DETAY_DETAYLI_ACIKLAMA_FIELD = By.xpath("//a[normalize-space()='DETAYLI AÇIKLAMA']");
    public static final By URUN_DETAY_MASAUSTU_GORUNUM_LINK = By.id("showDesktopLink");
    public static final By URUN_DETAY_SEPETE_EKLE_BUTTON_ID = By.id("add-product-to-cart");
    public static final By URUN_DETAY_FOOTER_ORJUNAL_URUN_FIELD_FITMODA = By.xpath("//a[@href='/t/orijinal-urun']");
    public static final By URUN_DETAY_KOMBINASYONDA_NELER_VAR_FIELD_MWEB = By.xpath("//div[@class='product-combination']");
    public static final By URUN_SEPETE_EKLENDI_POPUP = By.xpath("//span[contains(text(),'BİLGİLENDİRME')]");
    public static final By URUN_DETAY_BEDEN_SECINIZ_POPUP_FITMODA = By.xpath("//button[normalize-space()='Tamam']");
    public static final By URUN_DETAY_VIDEO_POSITION_FITMODA = By.cssSelector("div[class='col-lg-12 product-specifications-area'] div[class='title']");
    public static final By URUN_DETAY_SEPETE_EKLE_BUTTON = By.xpath("//a[@id='add-product-to-cart']");
    public static final By URUN_DETAY_ALISVERISE_DEVAM_ET_BUTTON = By.xpath("//div[@id='add-to-cart-after']//div[@class='pop-button-container']");
    public static final By URUN_DETAY_URUN_SEPETE_EKLENDI_POPUP_MWEB = By.xpath("//div[@class='fancybox-wrap fancybox-desktop fancybox-type-html fancybox-opened']");
    public static final By URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE = By.xpath("//body[1]/div[22]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li");
    public static final By URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_MWEB = By.xpath("/html/body/div[8]/div/div/div/div/div/div[3]/a[1]");
    public static final By URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_TEST = By.xpath("//body[1]/div[21]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li");
    public static final By URUN_DETAY_BEDEN_AROMA_SECIMI_SIZE_FITMODA = By.xpath("//body[1]/div[16]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li");
    public static final By BEDEN_AROMA_SECIMI_POPUP_NWEB = By.xpath("/html[1]/body[1]/div[8]/div[1]/div[1]/div[1]/div[1]/div");
    public static final By URUN_DETAY_HEDIYE_SECIMI_POPUP_MWEB = By.xpath("//span[contains(text(),'HEDİYE SEÇİMİ')]");
    public static final By URUN_DETAY_HEDIYE_SECIMI_SIZE = By.xpath("(//li[@class='gift-select-option-v2'])");
    public static final By URUN_DETAY_SATIN_AL_BUTTON = By.xpath("//a[normalize-space()='SATIN AL']");
    public static final By URUN_DETAY_SEPETE_GIT_BUTTON_MWEB = By.xpath("//a[normalize-space()='Sepete Git']");
    public static final By URUN_DETAY_SATIN_AL_BUTTON_FITMODA = By.xpath("//div[@class='fancybox-wrap fancybox-desktop fancybox-type-html wrapper-add-to-cart-after fancybox-opened']//a[2]");
    public static final By URUN_DETAY_KOMBINASYONDA_NELER_VAR_FIELD_WEB = By.xpath("//div[@class='bundleproduct child-product-list clearfix']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE = By.xpath("(//div[.='SEPETE EKLE'])[6]");
    public static final By URUN_DETAY_TITLE = By.xpath("//h1[@class='product-page-title']");
    public static final By URUN_DETAY_PRICE = By.xpath("//span[@class='product-price']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_TITLE = By.xpath("//span[@class='rn-ismi']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_PRODUCT_PRICE = By.xpath("//span[@class='buy-price']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP = By.xpath("//div[@class='product-card-pop-up']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_KAPAT = By.xpath("//div[@class='sw-close']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_STARS = By.xpath("(//div[@class='grad-rate-color'])[41]");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_AROMA_SECIMI_TITLE = By.xpath("//span[contains(@class,'Aroma-Seimi2')]");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_HEDIYE_SECIMI_TITLE = By.xpath("//span[@class='special-offer-title']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_URUN_DETAYINA_GIT_BUTTON = By.xpath("//div[@class='productDetailButtonText']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SKT = By.xpath("//span[@class='skt-head']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_OK_BUTTON = By.xpath("//a[@class='sv-arrow-right']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_GIFT_SIZE = By.xpath("//div[@class='special-offer-select']//select/option");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SELECT_GIFT = By.xpath("//div[@class='special-offer-select']//select");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_MINUS_BUTTON = By.xpath("//div[@id='btnTxtMinus']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_PLUS_BUTTON = By.xpath("//div[@id='btnPlus']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_TITLE = By.xpath("//span[@class='Adet']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_ADET_NUMBERS = By.xpath("//span[@id='quantityText']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_SEPETE_EKLE_BUTTON = By.xpath("//div[@class='sepeteEkleButton']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_SEPETE_GIT_SMALL_POPUP_TEXT = By.xpath("//p[@class='toast-text']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_SEPETE_GIT_SMALL_POPUP_SEPETE_GIT = By.xpath("//a[@class='toast-cart-text']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_5_ADET_MESSAGE = By.xpath("//div[@class='warning-box']");
    public static final By DESKTOP_HIZLI_SEPETE_EKLE_POPUP_5_ADET_MESSAGE_KAPAT = By.xpath("//div[@class='error-info-close']");
}
