package objectRepository;

import org.openqa.selenium.By;

public class ProductListOR {
    public static final By URUN_LISTELEME_PAGE_KATEGORI_FILTER = By.xpath("//div[@class='product-filters']");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_MWEB = By.xpath("//a[@class='filter-button']");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_MWEB = By.xpath("//li[@data-id='rootCategories']");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_MWEB_FITMODA = By.xpath("//li[@class='rootCategoryClass active']");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_PROTEIN_TOZU_MWEB = By.xpath("(//li[@class='item'])[1]");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_ILK_KATEGORI__MWEB = By.xpath("//*[@id=\"facetFilter\"]/div[2]/div[1]/ul/li[1]");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_PROTEIN_TOZU_MWEB_FITMODA = By.xpath("(//li[@class='active'])[1]");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_UYGULA_BUTTON = By.id("rootCategoriesApply");
    public static final By URUN_LISTELEME_PAGE_FILTERELEME_UYGULA_BUTTON_FITMODA = By.id("filterBtn");

    public static final By URUN_LISTELEME_PAGE_FILTERELEME_DETAY = By.className("filter-detail");
    public static final By URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN = By.xpath("//span[@class='sorting-dropdown']");
    public static final By URUN_LISTELEME_PAGE_SIRALAMA_BUTTON = By.xpath("//a[@class='sort-button']");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI = By.xpath("(//div[@class='land-pbox'])");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_FITMODA = By.xpath("(//div[@class='col-lg-4 col-md-4 col-sm-4 cproduct'])");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI = By.xpath("//div[@class='category-products cfix']");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI_MWEB = By.xpath("(//div[@class='lp-product-showcase clearfix'])[1]");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI_MWEB_FITMODA = By.xpath("//div[@class='lp-product-showcase productlist clearfix']");
    public static final By URUN_LISTELEME_PAGE_KATEGORI_FILTER_SIZE = By.xpath("//div[@class='product-filters']//div[@class='filterscroll scroll-pane']//li");
    public static final By URUN_LISTELEME_PAGE_KATEGORI_FILTRESI_SAYISI_1 = By.xpath("//div[@class='product-filters']//div[@class='filterscroll scroll-pane']//li[1]");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO = By.cssSelector("div[class='supp-filter-left'] span");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_MWEB = By.cssSelector("div[class='lp-total-count'] strong");
    public static final By URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_FITMODA = By.cssSelector("div[class='col-lg-6 col-md-6 col-sm-6'] strong");
    public static final By URUN_LISTELEME_PAGE_PAGER_NUMBER_LAST= By.xpath("//a[normalize-space()='»']");
    public static final By URUN_LISTELEME_HEADER_CAMPAIGNS=By.xpath("(//span[contains(text(),'Kampanyalar')])[1]");
    public static final By URUN_LISTELEME_HEADER_COMBINATIONS=By.xpath("//a[@class='kombinelink']");
    public static final By URUN_LISTELEME_HEADER_COMBINATIONS_STORE_3=By.xpath("//span[@class='combined-fit-svg']");

    public static final By URUN_LISTELEME_HEADER_DISCOUNT=By.xpath("(//i[@class='icon icon-sales-1'])[1]");
    public static final By OUT_OF_STOCK_ICON=By.xpath("(//div[@class='outStock p-icon'])");
    public static final By URUN_LISTELEME_URUN_INCELE_BUTTON_CONTROL=By.xpath("(//a[@class='product-button-static'][contains(text(),'ÜRÜNÜ İNCELE')])");
    public static final By URUN_LISTELEME_URUN_INCELE_BUTTON_CLICK=By.xpath("(//a[@class='product-button-static'][contains(text(),'ÜRÜNÜ İNCELE')])[1]");
    public static final By ALL_LIST_URUN_INCELE_BUTTON_CONTROL1=By.xpath("//div[@class='product-button-static']");
    public static final By URUN_LISTELEME_KOMBINASYONLAR_URUN_INCELE_BUTTON_CONTROL=By.xpath("(//div[@class='product-button-static'][contains(text(),'ÜRÜNÜ İNCELE')])");
    public static final By URUN_LISTELEME_KOMBINASYONLAR_URUN_INCELE_BUTTON_CLICK=By.xpath("(//div[@class='product-button-static'][contains(text(),'ÜRÜNÜ İNCELE')])[1]");
    public static final By URUN_LISTELEME_KOMBINASYONLAR_URUN_INCELE_BUTTON_CLICK_Class=By.xpath("(//div[@class='product-button-static'][contains(text(),'ÜRÜNÜ İNCELE')])[1]");
    public static final By URUN_LISTELEME_PAGER_LAST=By.className("pager-last");
    public static final By URUN_LISTELEME_PAGER_NUMBER=By.className("pager-number");
    public static final By TittleKombinasyonSupplementler=By.xpath("(//h1[normalize-space()='Kombinasyonlar'])[1]");
    public static final By TittleKombinasyonVitaminler=By.xpath("//h1[normalize-space()='Vitaminler Kombinasyonlar']");





}
