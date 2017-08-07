package core.classicmethods;

import api.android.Android;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by lumihai on 5/24/2017.
 */
public class Waiters {
    //fluent wait  for an element (used for clicks - wait for element before click)
    public void waitForElementVIsibility(By element) {
        FluentWait<AndroidDriver> wait = new FluentWait<AndroidDriver>(Android.driver).withTimeout(20, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

}
