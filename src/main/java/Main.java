import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lumihai on 6/29/2017.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("udid", "192.168.92.101:5555");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "D:/AppiumApk/Emma-prodautomation.apk");
        caps.setCapability("newCommandTimeout", "600");

        AndroidDriver driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);

        driver.pressKeyCode(AndroidKeyCode.HOME);

        driver.findElementByAndroidUIAutomator("new Uiselector().text(\"Settings\")").click();
        driver.findElementByAndroidUIAutomator("new Uiselector().textContains(\"Wi\").testContains(\"Fi\")").click();
        driver.findElementByXPath("//android.widget.TextView[@text=\"Connected\"]/preceding:android.widget.TextView[1]");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String SSID = driver.findElementByXPath("//android.widget.TextView[@text=\"Connected\"]/preceding:android.widget.TextView[1]").getAttribute("name");
        System.out.println("Connected to: " + SSID);


//        driver.findElementByAndroidUIAutomator("new UiSelector().description(\"Apps\")").click();
//        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"YouTube\")").click();
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        driver.findElementByAndroidUIAutomator("new resourceId().text(\"com.northghost.appsecurity:id/number_1\")").click();
//        driver.findElementByAndroidUIAutomator("new resourceId().text(\"com.northghost.appsecurity:id/number_1\")").click();
//        driver.findElementByAndroidUIAutomator("new resourceId().text(\"com.northghost.appsecurity:id/number_1\")").click();
//        driver.findElementByAndroidUIAutomator("new resourceId().text(\"com.northghost.appsecurity:id/number_1\")").click();

//        /**
//         * USING SINGLE PROPERTIES
//         */
//        driver.findElementByAndroidUIAutomator("new Uiselector().resourceId(\"android:id/title\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().description(\"ASTAnash, Connected, no Internet,Wifi signal full.\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().text(\"Hello World\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().descriptionContains(\"Hello World\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().descriptionMatches(\"Hello World\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().textContains(\"Hello World\")").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().checked(true/false)").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().index(1)").click();
//
//        /**
//         * Chaining properties
//         */
//        driver.findElementByAndroidUIAutomator("new Uiselector().resourceId(\"com.android.settings:id/switch_widget\").checked(true)").click();
//        driver.findElementByAndroidUIAutomator("new Uiselector().resourceId(\"com.android.settings:id/switch_widget\").text(\"some text\").checked(true)").click();

        driver.quit();

    }
}
