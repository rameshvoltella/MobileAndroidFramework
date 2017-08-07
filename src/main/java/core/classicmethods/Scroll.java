package core.classicmethods;

import api.android.Android;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;

/**
 * Created by lumihai on 5/25/2017.
 */
public class Scroll {

    private JavascriptExecutor js = (JavascriptExecutor) Android.driverIos;

    public void left(RemoteWebElement element) {
        scroll(element, "left");
    }

    public void right(RemoteWebElement element) {
        scroll(element, "right");
    }

    public void up(RemoteWebElement element) {
        scroll(element, "up");
    }

    public void down(RemoteWebElement element) {
        scroll(element, "down");
    }

    private void scroll(RemoteWebElement element, String direction) {
        HashMap<String, String> scrollMap = new HashMap<String, String>();
        scrollMap.put("direction", direction);
        scrollMap.put("element", element.getId());
        js.executeScript("mobile:scroll", scrollMap);
    }

}
