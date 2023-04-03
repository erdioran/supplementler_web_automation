package objectRepository;

import org.openqa.selenium.By;

public class AdminOR {

    public static final By SEARCH_EMAIL = By.id("SearchEmail");
    public static final By SEARCH_BUTTON_CUSTOMER_PAGE = By.id("search-customers");
    public static final By MUSTERI_BILGILERI_TAB = By.xpath("//a[contains(text(),'Müşteri Bilgileri')]");
    public static final By KAYDET_CUSTOMER_DETAIL = By.xpath("//button[normalize-space()='Kaydet']");
    public static final By CUSTOMER_ROLES_TAB = By.cssSelector("tbody tr td:nth-child(7)");
    public static final By TAB_HEADER = By.xpath("//div[@class='title']");
    public static final By MUSTERI_ROLLERI_BUTTON_CUSTOMER_DETAIL = By.xpath("//a[contains(text(),'Müşteri Rolleri')]");
    public static final By DUZENLE_BUTTON_CUSTOMER_LIST = By.xpath("//a[normalize-space()='Düzenle']");
    public static final By AYAR_ADI_TEXTBOX = By.id("Name");
    public static final By SEARCH_BUTTON = By.id("btnSearch");
    public static final By MARKA_FIELD_FIRST_ITEM=By.xpath("//body/div[1]/div/div[4]/div/div[2]/div/table[2]/tbody/tr/td/div/form/table/tbody/tr[1]/td[3]/input");
    public static final By Admin_Area_Manage_Only_Orders=By.xpath("//span[normalize-space()='Admin area. Manage Only Orders']");
    public static final By ADMIN_INDIRIMLER_YENI_EKLE_BUTTON=By.xpath("//a[normalize-space()='Yeni ekle']");
    public static final By ADMIN_INDIRIMLER_NAME_TEXTBOX=By.id("Name");
    public static final By ADMIN_INDIRIMLER_TUR_YUZDELIK_CHECKBOX=By.id("UsePercentage");
    public static final By ADMIN_INDIRIMLER_TUR_FIYAT_CHECKBOX=By.id("IsDiscountPrice");
    public static final By ADMIN_INDIRIMLER_TUR_FIYAT_TEXTBOX=By.id("DiscountAmount");
    public static final By ADMIN_INDIRIMLER_TUR_YUZDELIK_TEXTBOX=By.id("DiscountPercentage");
    public static final By ADMIN_INDIRIMLER_KUPON_KODU_GEREKTIRIR_CHECKBOX=By.id("RequiresCouponCode");
    public static final By ADMIN_INDIRIMLER_KUPON_KODU_TEXTBOX=By.id("CouponCode");
    public static final By ADMIN_INDIRIMLER_MUSTERI_ID_TEXTBOX=By.id("AssignedCustomerId");
    public static final By ADMIN_INDIRIMLER_HEMEN_BITIR_BUTTON=By.id("terminateDiscount");
    public static final By ADMIN_INDIRIMLER_AND_DATE=By.id("EndDateUtc");
    public static final By ADMIN_INDIRIMLER_DELETE=By.id("discount-delete");
    public static final By ADMIN_INDIRIMLER_PAGE_TITLE=By.xpath("//div[@class='title']");
    public static final By ADMIN_INDIRIMLER_DELETE_ALLERT_SIL=By.xpath("//input[@value='Sil']");
    public static final By ADMIN_KAYDET_VE_DEVAM_ET_BUTTON=By.xpath("//button[normalize-space()='Kaydet ve Devam Et']");
    public static final By ADMIN_INDIRIMLER_KAYDET_BUTTON = By.xpath("//button[@name='save'][normalize-space()='Kaydet']");
    public static final By ADMIN_INDIRIMLER_MUSTERILERE_ATANMIS_INDIRIM_LISTESI_BUTTON = By.xpath("//a[contains(text(),'Müşterilere atanmış indirim listesi')]");
    public static final By ADMIN_INDIRIMLER_INDIRIM_KISITLAMALARI_TAB = By.xpath("//a[contains(text(),'İndirim kısıtlamaları')]");
    public static final By ADMIN_INDIRIMLER_REQUIREMENT_TYPE = By.id("AddDiscountRequirement");
    public static final By ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU = By.xpath("//option[@value='DiscountRequirement.ProductCountEqual']");
    public static final By ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_ID_TEXTBOX = By.id("DiscountRulesProductCountEqual0_DiscountRequirementModel_ProductId");
    public static final By ADMIN_INDIRIMLER_REQUIREMENT_TYPE_BELIRTILEN_URUNDEN_X_ADET_BULUNMASI_KOSULU_PRODUCT_QUANTITY_TEXTBOX = By.id("DiscountRulesProductCountEqual0_DiscountRequirementModel_ProductQuantity");
    public static final By ADMIN_INDIRIMLER_REQUIREMENT_KAYDET_BUTTON = By.id("saveCustomerIdrequirement0");
    public static final By ADMIN_INDIRIMLER_INDIRIM_NAME_TEXT = By.xpath("//div[@class='title']");
    public static final By ADMIN_CIKIS_BUTTON = By.xpath("//a[contains(text(),'Çıkış?')]");
    public static final By ADMIN_INDIRIMLER_SIL_BUTTON=By.id("discount-delete");
    public static final By ADMIN_INDIRIMLER_SIL_POPUP_BUTTON=By.xpath("//input[@value='Sil']");
    public static final By ADMIN_LANDING_PAGE_AKTIF_CHECKBOX_INPUT=By.xpath("(//input[@id='IsActive'])[1]");
    public static final By ADMIN_LANDING_PAGE_AKTIF_CHECKBOX=By.id("IsActive");
    public static final By ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX_INPUT=By.xpath("//input[@id='IsFiltering']");
    public static final By ADMIN_LANDING_PAGE_SIRALAMA_CHECKBOX=By.id("IsFiltering");
    public static final By ADMIN_LANDING_PAGE_LINK=By.xpath("/html/body/div[1]/div/div[4]/div[3]/form/table/tbody/tr[17]/td[2]/a");
}