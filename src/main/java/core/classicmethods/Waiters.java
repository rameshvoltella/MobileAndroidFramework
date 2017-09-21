package core.classicmethods;

import api.android.Android;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static core.classicmethods.Gestures.WAIT_TIME_IN_SECONDS;

/**
 * Created by lumihai on 5/24/2017.
 */
public class Waiters {
    //fluent wait  for an element (used for clicks - wait for element before click)
    public void waitForElementVIsibility(By element) {
        FluentWait<AndroidDriver> wait = new FluentWait<AndroidDriver>(Android.driver).withTimeout(20, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForElementVIsibilityIOS(By element) {
        FluentWait<IOSDriver> wait = new FluentWait<IOSDriver>(Android.driverIos).withTimeout(20, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForElementVIsibilityIOS2(By element) {
        FluentWait<IOSDriver> wait = new FluentWait<IOSDriver>(Android.driverIos).withTimeout(20, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public WebElement waitForElement(By by, WaitCondition waitCondition) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(Android.driverIos, WAIT_TIME_IN_SECONDS);

            switch (waitCondition) {
                case CLICKABLE:
                    element = wait.until(ExpectedConditions.elementToBeClickable(by));
                    break;

                case VISIBLE:
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    break;

                case INVISIBLE:
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return element;
    }

    public enum WaitCondition {
        CLICKABLE, VISIBLE, INVISIBLE;

        /**
         * Returns the name of the enum constant, in lowercase
         */
        @Override
        public String toString() {
            String s = super.toString();
            return s.toLowerCase();
        }
    }

}
