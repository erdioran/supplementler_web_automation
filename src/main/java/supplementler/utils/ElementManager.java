package supplementler.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.FileReader;
import java.util.Map;

public class ElementManager {


    private ElementManager() {
    }

    public static Map<String, String> getElement(String elementName) throws Exception {
        String[] fileNames = {"src/test/java/objectRepository/loginPage.json", "src/test/java/objectRepository/creditsPage.json", "src/test/java/objectRepository/common.json"};
        for (String fileName : fileNames) {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(fileName));
            if (obj.containsKey(elementName)) {
                JSONObject element = (JSONObject) obj.get(elementName);
                String value = (String) element.get("value");
                String type = (String) element.get("type");
                return Map.of("value", value, "type", type);
            }
        }
        throw new Exception("Element not found");
    }

    public static By returnElement(String element) throws Exception {
        Map<String, String> elementValues = getElement(element);
        String type=elementValues.get("type");

        switch (type) {
            case "id":
                return By.id(elementValues.get("value"));
            case "xpath":
                return By.xpath(elementValues.get("value"));
            case "cssSelector":
                return By.cssSelector(elementValues.get("value"));
            case "tagName":
                return By.tagName(elementValues.get("value"));
            case "className":
                return By.className(elementValues.get("value"));
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
