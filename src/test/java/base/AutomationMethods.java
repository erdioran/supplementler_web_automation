package base;

import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import supplementler.base.DriverManager;
import supplementler.utils.ConfigManager;
import supplementler.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static objectRepository.ProductListOR.URUN_LISTELEME_PAGER_NUMBER;
import static supplementler.utils.ElementManager.*;


public class AutomationMethods {


    private static final Logger LOGGER = LogManager.getLogger(AutomationMethods.class);

    public static String getBase64Screenshot() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    }


    public static void enterText(String element, String textToEnter) throws Exception {
        WebElement webElement = findObject(returnElement(element));
        webElement.clear();
        webElement.sendKeys(textToEnter);
    }

    public static String getText(String element) throws Exception {
        return findObject(returnElement(element)).getText().trim();
    }

    public static WebElement findObject(By by) throws Exception {
        FluentWait<WebDriver> wait = getFluentWait();
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static List<WebElement> waitAllElement(By selector) {
        FluentWait<WebDriver> wait = getFluentWait();
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selector));
    }


    public static void click(String element) throws Exception {
        waitForIntervalsAndClick(returnElement(element), 1, ConfigManager.getExplicitWaitTime());
    }


    public static void waitForIntervalsAndClick(By by, int interval, int maxWait) throws Exception {
        boolean elementExists = false;
        int counter = 0;
        while (counter <= maxWait) {
            try {
                DriverManager.getDriver().findElement(by).click();
                elementExists = true;
                counter = maxWait + 1;
            } catch (Exception e) {
                LOGGER.info("Web element [{}] | Click attempt : [{}]", by.toString(), counter);
                Helper.sleepInSeconds(interval);
                counter++;
                elementExists = false;
            }
        }
        if (!elementExists) {
            DriverManager.getDriver().findElement(by).click();
        }
    }


    public static FluentWait<WebDriver> getFluentWait(int intervalInSeconds, int maxWaitTimeInSeconds) {
        return new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(intervalInSeconds))
                .pollingEvery(Duration.ofSeconds(maxWaitTimeInSeconds))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    public static FluentWait<WebDriver> getFluentWait() {
        return getFluentWait(1, ConfigManager.getExplicitWaitTime());
    }
    public static void switchToTab() {
        // açık olan diğer taba geçer.

        String main = DriverManager.getDriver().getWindowHandle();
        for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
            if (!main.contentEquals(windowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                LOGGER.info("Tab switch");
                break;
            }
        }
    }

    public static int getTabCount() {
        // açık tab sayısını döndürür

        return DriverManager.getDriver().getWindowHandles().size();
    }

    public static String getTabUrl() {
        // aktif tab'ın url'ini döndürür

        return DriverManager.getDriver().getCurrentUrl();
    }


    public static boolean elementVisibilty(By by) {
        boolean result = DriverManager.getDriver().findElement(by).isDisplayed();
        return result;
    }


    public static boolean elementVisibiltyWithSize(By by) {
        // by ile gönderilen elementi, tüm sayfa kodu içinde arar. olup olmadığını döndürür

        return DriverManager.getDriver().findElements(by).size() > 0;
    }


    public static boolean emailPatternMatches(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }

    public static boolean checkStringContains(String text, String value) {
        // text stringini içinde, value değerinin olup olmadığını döndürür

        if (text.contains(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static String checkStore(String text) {
        // tab url'indeki nokta karakterleri arasını döndürür.  www.supplementler.com -> supplementler

        String start = ".";
        String end = ".";
        int startIndex = text.indexOf(start) + start.length();
        int endIndex = text.indexOf(end, startIndex);
        return text.substring(startIndex, endIndex);
    }

    public static String checkBasketStep(String text) {
        // tab url'indeki / karakterinden sonrasını döndürür.  www.supplementler.com/cart -> cart

        String[] parts = text.split("/");
        return parts[parts.length - 1];

    }

    public static int getListSize(By by) {
        // by ile gönderilen elementin, size'ını (sayfada kaç adet olduğunu) döndürür)

        return DriverManager.getDriver().findElements(by).size();
    }

    public static int getRandomNumberBetween(int start, int end) {
        // start - end sayıları arasında random bir sayı döndürür.

        return (int) (Math.random() * end) + start;
    }

    public static void pageScroollDownToTargetElement(By by) {
        // by değeri ile gönderilen elemente kadar, sayfayı aşağı scroll eder.

        DriverManager.getDriver().findElement(by).sendKeys(Keys.ARROW_DOWN);
        LOGGER.info("Page scroll down");
    }

    public static void pageScroollUpToTargetElement(By by) {
        // by değeri ile gönderilen elemente kadar, sayfayı yukarı scroll eder.

        DriverManager.getDriver().findElement(by).sendKeys(Keys.ARROW_UP);
        LOGGER.info("Page scroll up");
    }

    public static void scrollToElementCenter(By by) {
        // Elementi sayfanın ortasında tutacak şekilde scroll eder
        WebElement element = DriverManager.getDriver().findElement(by);

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        int windowHeight = DriverManager.getDriver().manage().window().getSize().getHeight();
        int elementPositionY = element.getLocation().getY();
        int elementHeight = element.getSize().getHeight();

        int scrollToY = elementPositionY - (windowHeight / 2) + (elementHeight / 2);
        js.executeScript("window.scrollTo(0, " + scrollToY + ")");
    }


    public static String getElementBackgroundColor(By by) {
        String buttonColor = DriverManager.getDriver().findElement(by).getCssValue("background-color");
        return buttonColor;
    }

    public static String getElementFontColor(By by) {
        String buttonColor = DriverManager.getDriver().findElement(by).getCssValue("color");
        return buttonColor;
    }

    public static void backPage() {
        // tarayıcıda back işlevi yapar

        DriverManager.getDriver().navigate().back();
    }

    public static void refreshPage() {
        // sayfayı refreshler

        DriverManager.getDriver().navigate().refresh();
    }

    public static String getStockStatusMweb(By by) {
        String stockStatus = DriverManager.getDriver().findElement(by).getAttribute("data-outofstock");
        return stockStatus;
    }


    public static String getSyleBilgilendirmePopup(By by) {
        String stockStatus = DriverManager.getDriver().findElement(by).getAttribute("syle");
        return stockStatus;
    }

    public static String getImageUrl(By by) {
        String stockStatus = DriverManager.getDriver().findElement(by).getAttribute("src");
        return stockStatus;
    }

    public static String getSepetIconSvg(By by) {
        String stockStatus = DriverManager.getDriver().findElement(by).getAttribute("d");
        return stockStatus;
    }

    public static String getValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("value");
        return value;
    }

    public static String getClassValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("class");
        return value;
    }

    public static String getPlaceholderValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("placeholder");
        return value;
    }

    public static String getStyleValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("style");
        return value;
    }

    public static boolean getImageDisplayStatus(By by) {
        // by değeri ile gönderilen elementin(img türünde), kırıl olup olmadığını döndürür.

        boolean imageDisplay;
        WebElement img = DriverManager.getDriver().findElement(by);
        Boolean display = (Boolean) ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("return arguments[0].complete "
                        + "&& typeof arguments[0].naturalWidth != \"undefined\" "
                        + "&& arguments[0].naturalWidth > 0", img);
        if (display) {
            imageDisplay = true;
        } else {
            imageDisplay = false;
        }
        return imageDisplay;
    }

    public static String getDataToogleValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("data-toggle");
        return value;
    }

    public static String getCardTypeValue(By by) {
        String value = DriverManager.getDriver().findElement(by).getAttribute("data-card-type");
        return value;
    }

    public static void keyboardEnter() {
        // KlavYeden enter tuşuna basar.

        Actions actions = new Actions(DriverManager.getDriver());
        actions.sendKeys(Keys.ENTER).perform();
    }


    public static void scrollPageToTop() {
        // Sayfayı en üste scroll eder.

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(0, 0)");
    }

    public static boolean checkPriceType(String price) {
        // price stringinin, fiyat type'ına uygunluğunu kontrol eder. Örnek type: 100,00 TL

        return Pattern.compile("^\\d{1,8},\\d{2}\\s[A-Z]{2}$")
                .matcher(price)
                .matches();
    }

    public static boolean checkSKT(String skt){
        // son kullanma tarihilerinin formatını kontrol eder
        return Pattern.compile("Son kullanma tarihi\\s+(\\d{2}\\.\\d{2}\\.\\d{4})")
                .matcher(skt)
                .matches();
    }

    public static boolean checkPriceTypePopup(String price) {
        // price stringinin, fiyat type'ına uygunluğunu kontrol eder. Örnek type: 100,00 TL

        return Pattern.compile("^\\d{1,8}\\s[A-Z]{2}$")
                .matcher(price)
                .matches();
    }

    public static String getBankNameFromLogoUrl(String bankLogo) {
        // banklogo olarak gönderilemn url'in, / ile .svg arasındaki değeri döndürür. Banka name'i döndürür.

        int startIndex = bankLogo.lastIndexOf("/") + 1;
        int endIndex = bankLogo.lastIndexOf(".svg");
        return bankLogo.substring(startIndex, endIndex);
    }

    public static String getPriceWithoutComma(String str) {
        // str değerinin, "," karakterinden öncesini döndürür. Sepette fiyat x'den fazla olsun gibi test için kullanılır.

        int commaIndex = str.indexOf(",");
        return str.substring(0, commaIndex);

    }

    public static void switchDriverIframe(By by) {
        DriverManager.getDriver().switchTo().frame(DriverManager.getDriver().findElement(by));
    }

    public static void switchDriverDefault() {
        DriverManager.getDriver().switchTo().defaultContent();
    }

    public static double formatPriceDouble(String number) {
        // Fiyat indirim hesaplamlarında kullanılır. virgül'ü nokta'ya çevirir, double türüne çevirir. Nokta sonuda tek hane varsa, supplementlerde kullanımında oldığu gibi sonuna bir 0 ekler. 198,8 -> 198.80 gibi
        double numberNew = Double.parseDouble(number.replace(',', '.'));
        return numberNew;
    }


    public static String formatPriceAddChar(String number) {
        // Fiyat indirim hesaplamlarında kullanılır. Nokta sonuda tek hane varsa, supplementlerde kullanımında oldığu gibi sonuna bir 0 ekler. 198,8 -> 198.80 gibi
        String formatted = String.format("%.2f", number);
        return formatted;
    }


    public static void getChecked(By by,String status) {
        WebElement checkBox = DriverManager.getDriver().findElement(by);

        if (status.equals("aktif")){
            if (!checkBox.isSelected()) {
                checkBox.click();
                LOGGER.info("Chekbox seçildi.");
            }else {
                LOGGER.info("Chekbox zaten seçili.");
            }
        }else if (status.equals("inaktif")){
            if (checkBox.isSelected()) {
                checkBox.click();
                LOGGER.info("Checkbox kaldırıldı.");
            }else {
                LOGGER.info("Checkbox zaten aktif değil.");
            }
        }

    }

    public static void closeTab() {
        // açık olan tabı kapatır
        DriverManager.getDriver().close();
    }


    public static void closeTabOtherThanTheMainTab() {
        // Bu yöntem, driver nesnesi üzerinden yönetilen tüm sekmelerin tanımlarını alır ve ana sekme dışındaki tüm sekmeleri dolaşır.
        // Dolaşırken, her bir sekme için ana sekmeden farklı bir ID kontrolü yapar.
        // Ana sekmeden farklı bir ID'ye sahip olan sekmeler kapatılır ve sonunda ana sekme tekrar seçil

            String mainTab = DriverManager.getDriver().getWindowHandle();
            Set<String> allTabs = DriverManager.getDriver().getWindowHandles();

            for (String tab : allTabs) {
                if (!tab.equals(mainTab)) {
                    DriverManager.getDriver().switchTo().window(tab);
                    DriverManager.getDriver().close();
                }
            }

            DriverManager.getDriver().switchTo().window(mainTab);

    }

    public static List<WebElement> getAllIndex(By by) {
        List<WebElement> list = DriverManager.getDriver().findElements(by);
        LOGGER.info("testtest "+list);
        return list;
    }

    //Web Adresini mWeb olarak değiştirir.
    public static String replaceUrlToMweb(String originalString) {
        String newUrl= originalString.replace("www", "m");
        return newUrl;
    }
}
