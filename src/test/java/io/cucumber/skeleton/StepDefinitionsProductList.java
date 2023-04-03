package io.cucumber.skeleton;

import io.cucumber.java.en.And;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Converter;
import supplementler.base.DriverManager;

import javax.naming.SizeLimitExceededException;
import java.util.List;

import java.util.HashMap;

import static base.AutomationMethods.*;
import static base.AutomationMethods.scrollToElementCenter;
import static base.AutomationMethods.getTabUrl;
import static base.ProjectMethods.productListPageCategoryFilter_1_ProductCount;
import static base.ProjectMethods.productListPageCategoryProductCount;
import static objectRepository.GeneralOR.*;
import static objectRepository.HomePageOR.*;
import static objectRepository.ProductListOR.*;
import static supplementler.utils.Helper.sleepInSeconds;
import static supplementler.utils.Helper.sleepMs;

public class StepDefinitionsProductList {
    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsProductList.class);
    static HashMap<String, Integer> myMapInt = new HashMap<String, Integer>();

    @And("check that landing page is displayed with {string}")
    public void checkThatLandingPageIsDisplayedWith(String filter) {


        boolean urlCheckPlatform = checkStringContains(getTabUrl(), "m.");
        LOGGER.info("Boolen değer : " + urlCheckPlatform);
        if (!urlCheckPlatform) {
            LOGGER.info("Web Control");
            Assert.assertFalse(elementVisibiltyWithSize(PAGE_STATUS), "Langind page sayfası görüntülenemedi.");
            Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI), "Landing page sayfasında ürün görüntülenmedi");

            if (filter.equals("inaktif")) {
                if (elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER)) {
                    sleepInSeconds(120);
                    refreshPage();
                    Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
                    Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
                } else {
                    Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
                    Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
                }

            } else if (filter.equals("aktif")) {
                if (!elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER)) {
                    sleepInSeconds(120);
                    refreshPage();
                    Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
                    Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
                } else {
                    Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
                    Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
                }
            }
        } else{
            LOGGER.info("mWeb Control");


            //mWeb tarafı

            String urlCheck = checkStore(getTabUrl());
            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                Assert.assertFalse(elementVisibiltyWithSize(PAGE_STATUS_MWEB), "Langind page sayfası görüntülenemedi.");
                Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI_MWEB), "Landing page sayfasında ürün görüntülenmedi");

                if (filter.equals("inaktif")) {
                    if (elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB)) {
                        sleepInSeconds(90);
                        refreshPage();
                        Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                        Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                    } else {
                        Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                        Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                    }

                } else if (filter.equals("aktif")) {
                    if (!elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB)) {
                        sleepInSeconds(90);
                        refreshPage();
                        Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                        Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                    } else {
                        Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                        Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                    }
            } else if (urlCheck.equals("fitmoda")) {
                    Assert.assertFalse(elementVisibiltyWithSize(PAGE_STATUS_MWEB), "Langind page sayfası görüntülenemedi.");
                    Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI_MWEB_FITMODA), "Landing page sayfasında ürün görüntülenmedi");

                    if (filter.equals("inaktif")) {
                        if (elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB)) {
                            sleepInSeconds(90);
                            refreshPage();
                            Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                            Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                        } else {
                            Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                            Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                        }

                    } else if (filter.equals("aktif")) {
                        if (!elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB)) {
                            sleepInSeconds(90);
                            refreshPage();
                            Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                            Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                        } else {
                            Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_FILTERELEME_MWEB));
                            Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_BUTTON));
                        }
                    }
                }
            }
        }

//        String urlCheck = checkStore(getTabUrl());
//        Assert.assertFalse(elementVisibiltyWithSize(PAGE_STATUS), "Langind page sayfası görüntülenemedi.");
//        Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_LISTELENEN_URUN_ALANI), "Landing page sayfasında ürün görüntülenmedi");
//
//        if (filter.equals("inaktif")) {
//            if (elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER)) {
//                sleepInSeconds(35);
//                refreshPage();
//                Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
//                Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
//            } else {
//                Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
//                Assert.assertFalse(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
//            }
//
//        } else if (filter.equals("aktif")) {
//            if (!elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER)) {
//                sleepInSeconds(35);
//                refreshPage();
//                Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
//                Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
//            } else {
//                Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_KATEGORI_FILTER));
//                Assert.assertTrue(elementVisibiltyWithSize(URUN_LISTELEME_PAGE_SIRALAMA_DROPDOWN));
//            }
//        }
    }


    @And("open the campaigns page")
    public void openTheCampaignsPage() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(URUN_LISTELEME_HEADER_CAMPAIGNS);
        } else if (urlCheck.equals("fitmoda")) {
            click(URUN_LISTELEME_HEADER_DISCOUNT);
            LOGGER.info("CLİCK EDİLDİ");
        }
    }

    @And("open the combinations page")
    public void openTheCombinationsPage() {
        String urlCheck = checkStore(getTabUrl());
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            click(URUN_LISTELEME_HEADER_COMBINATIONS);
        } else if (urlCheck.equals("fitmoda")) {
            click(URUN_LISTELEME_HEADER_COMBINATIONS_STORE_3);

        }
    }

    //Listeleme sayfasında, son sayfaya geçiş
    @And("switch to last page")
    public static void switchToLastPage() {

        if (elementVisibiltyWithSize(URUN_LISTELEME_PAGER_LAST)) {
            click(URUN_LISTELEME_PAGER_LAST);
            LOGGER.info("Son Click edildi.");
        } else if (elementVisibiltyWithSize(URUN_LISTELEME_PAGER_NUMBER)) {
            int indexSelect = getAllIndex(URUN_LISTELEME_PAGER_NUMBER).size();
            indexSelect -= 1;
            getAllIndex(URUN_LISTELEME_PAGER_NUMBER).get(indexSelect).click();
            LOGGER.info("En büyük index click edildi.");
        } else {

        }
    }

    @And("Out of stock product icon and price control")
    public void outOfStockProductIconAndPriceControl() {

        if (elementVisibiltyWithSize(TittleKombinasyonSupplementler) || elementVisibiltyWithSize(TittleKombinasyonVitaminler)) {

            int IconType = getListSize(OUT_OF_STOCK_ICON);
            int ButtonType = getListSize(URUN_LISTELEME_KOMBINASYONLAR_URUN_INCELE_BUTTON_CONTROL);


            Assert.assertEquals(IconType, ButtonType, "Tükendi ikon ve Ürün incele button sayıları uyuşmuyor");
            LOGGER.info("Icon Sayısı : " + IconType + "  Button sayısı : " + ButtonType);

            click(URUN_LISTELEME_KOMBINASYONLAR_URUN_INCELE_BUTTON_CLICK_Class);
            LOGGER.info("Stoğu tükenmiş ilk üründe, ürün incele butonuna tıklanılır.");

            elementVisibilty(By.xpath("//div[@class='eitem']"));
            LOGGER.info("ürün detay sayfası Bu ürünümüz için stok tükenmiştir... metni kontrol edildi. ");
        } else {


            int IconType = DriverManager.getDriver().findElements(OUT_OF_STOCK_ICON).size();
            int ButtonType = DriverManager.getDriver().findElements(URUN_LISTELEME_URUN_INCELE_BUTTON_CONTROL).size();
            int ButtonType1 = DriverManager.getDriver().findElements(ALL_LIST_URUN_INCELE_BUTTON_CONTROL1).size();

            ButtonType += ButtonType1;

            Assert.assertEquals(IconType, ButtonType, "Tükendi ikon ve Ürün incele button sayıları uyuşmuyor");
            LOGGER.info("Icon Sayısı : " + IconType + "  Button sayısı : " + ButtonType);


            click(URUN_LISTELEME_URUN_INCELE_BUTTON_CLICK);
            LOGGER.info("Stoğu tükenmiş ilk üründe, ürün incele butonuna tıklanılır.");


            elementVisibilty(By.xpath("//div[@class='eitem']"));
            LOGGER.info("ürün detay sayfası Bu ürünümüz için stok tükenmiştir... metni kontrol edildi. ");
        }
    }

    @And("the number of products in the category filter must match the number of products listed")
    public void theNumberOfProductsInTheCategoryFilterMustMatchTheNumberOfProductsListed() {


        //Platform alınır.
        boolean urlCheckPlatform = checkStringContains(getTabUrl(), "www");
        LOGGER.info("Boolen değer : " + urlCheckPlatform);

        //Web ise içine girer
        if(urlCheckPlatform){

        String urlCheck = checkStore(getTabUrl());

        int productCount = 0;
        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO));

        } else if (urlCheck.equals("fitmoda")) {
            productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_FITMODA));
        }

        Assert.assertEquals(productCount, productListPageCategoryProductCount(), "Kategori filtresi total sayısı ile listelenene ürün sayısı eşleşmiyor.");

        }else{

            //mWEB
            String urlCheck = checkStore(getTabUrl());
            if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
                int productCount=0;
                productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_MWEB));
                sleepInSeconds(2);
                click(URUN_LISTELEME_PAGE_FILTERELEME_MWEB);
                sleepInSeconds(2);
                click(URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_MWEB);
                click(URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_PROTEIN_TOZU_MWEB);
                click(URUN_LISTELEME_PAGE_FILTERELEME_UYGULA_BUTTON);
                sleepInSeconds(2);

                int productCountNew=0;
                productCountNew=Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_MWEB));

                Assert.assertNotEquals(productCount, productCountNew, "Filtreleme işlemi sonrasında, listelenen ürün adet değeri değişmedi.");

            } else if (urlCheck.equals("fitmoda")) {

                sleepInSeconds(15);
                refreshPage();
                int productCount=0;
                productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_MWEB));

                sleepInSeconds(2);
                click(URUN_LISTELEME_PAGE_FILTERELEME_MWEB);
                sleepInSeconds(2);
                click(URUN_LISTELEME_PAGE_FILTERELEME_KATEGORI_MWEB);
                click(URUN_LISTELEME_PAGE_FILTERELEME_ILK_KATEGORI__MWEB);
                click(URUN_LISTELEME_PAGE_FILTERELEME_UYGULA_BUTTON_FITMODA);
                sleepInSeconds(2);

                int productCountNew=0;
                productCountNew=Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_MWEB));

                Assert.assertNotEquals(productCount, productCountNew, "Filtreleme işlemi sonrasında, listelenen ürün adet değeri değişmedi.");

            }
            }
        }


    @And("click first category filter")
    public void clickFirstCategoryFilter() {
        boolean urlCheckPlatform = checkStringContains(getTabUrl(), "www");
        LOGGER.info("Boolen değer : " + urlCheckPlatform);

        //Web ise içine girer
        if(urlCheckPlatform) {
            click(URUN_LISTELEME_PAGE_KATEGORI_FILTRESI_SAYISI_1);
        }else if(!urlCheckPlatform){
            sleepInSeconds(2);
        }
    }

    @And("get first category count")
    public void getFirstCategoryCount() {
        myMapInt.put("firt_category_count", productListPageCategoryFilter_1_ProductCount());
    }

    @And("check number of products listed")
    public void checkNumberOfProductsListed() {

        sleepInSeconds(5);
        String urlCheck = checkStore(getTabUrl());

        int productCount = 0;

        if (urlCheck.equals("supplementler") || urlCheck.equals("vitaminler")) {
            productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO));
        } else if (urlCheck.equals("fitmoda")) {
            productCount = Integer.parseInt(getText(URUN_LISTELEME_PAGE_LISTELENEN_URUN_SAYISI_INFO_FITMODA));
        }
        Assert.assertEquals(productCount, myMapInt.get("firt_category_count"));
    }

    @And("check {string} in first product tittle")
    public void checkInFirstProductTittle(String text){
        Assert.assertTrue(getText(URUN_LISTELEME_ILK_URUN_TITLE_MWEB).contains(text));
    }
}
