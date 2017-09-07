package core.classicmethods;

import api.android.Android;
import core.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lumihai on 5/24/2017.
 */
public class AssertsUtils {

    public Waiters waiters = new Waiters();

    //another type of wait for elements which appear in some circumstances
    public boolean isElementExist(By by) {
        try {
            int count = Android.driverIos.findElements(by).size();
            if (count >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }

    public void isElementDisplayed(By by) {
        Boolean reult = null;
        try {
            waiters.waitForElementVIsibilityIOS(by);
            reult = Android.driverIos.findElement(by).isDisplayed();
        } catch (TimeoutException e) {
            reult = false;
        }
        assertTrue("Element" + by + "was not displayed", reult);
    }

    public void isElementEnabled(By by) {
        Boolean reult = null;
        try {
            waiters.waitForElementVIsibilityIOS(by);
            reult = Android.driverIos.findElement(by).isEnabled();
        } catch (TimeoutException e) {
            reult = false;
        }
        assertTrue("Element" + by + "was not enabled", reult);
    }

    public void isElementNotDisplayed(By by) {
        boolean result = isElementPresentBoolean(by);
        if (result) {
            result = Android.driverIos.findElement(by).isDisplayed();
        }
        assertTrue("Element" + by + "was displayed", result);
    }


    public void isElementNotEnabled(By by) {
        boolean result = isElementPresentBoolean(by);
        if (result) {
            result = Android.driverIos.findElement(by).isEnabled();
        }
        assertTrue("Element" + by + "was enabled", !result);
    }

    public boolean isElementNotExist(By by) {
        int count = Android.driverIos.findElements(by).size();
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementVisible(By by) {
        try {
            if (Android.driverIos.findElement(by).isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void isElementPresent(By by) {
        boolean result = (Android.driverIos.findElements(by).size() > 0) ? true : false;
        assertTrue("element" + by + "was not present", result);
    }

    public boolean isElementPresentBoolean(By by) {
        boolean isPresent = false;
        try {
            Waiters waiters = new Waiters();
            waiters.waitForElementVIsibility(by);
            isPresent = true;
        } catch (TimeoutException e) {
            System.out.println("Element" + by + "was not displayed");
        }
        return isPresent;
    }

    public void isNotDisplayed(By by) {
        Boolean result = null;
        try {
            result = Android.driverIos.findElement(by).isDisplayed();
        } catch (Exception e) {
            result = false;
        }
        assertTrue("element" + by + "was not displayed", result);
    }

    public void AssertContains(String xPath, String expectedValue, Attribute attribute, String errorMessage) {
        AssertContains(By.xpath(xPath), expectedValue, attribute, errorMessage);
    }

    public void AssertContains(By by, String expectedValue, Attribute attribute, String errorMessage) {
        AssertContains(by, null, expectedValue, attribute, errorMessage);
    }

    public void AssertContains(By by, String type, String expectedValue, Attribute attribute, String errorMessage) {
        assertTrue(errorMessage, Android.driverIos.findElement(by).getAttribute(attribute.toString())
                .contains(expectedValue));
    }

    public void AssertExists(String xpath, String errorMessage) {
        AssertExists(By.xpath(xpath), errorMessage);
    }

    public void AssertExists(By by, String errorMessage) {
        try {
            Android.driverIos.findElement(by);
        } catch (NoSuchElementException e) {
            System.out.println("Element was not found");
            MyLogger.log.info(String.format("XPATH %s not found.", by));
            throw e;
        }
    }

    public void AssertEquals(String xpath, String expectedValue, Attribute attribute, String errorMessage) {
        AssertEquals(By.xpath(xpath), expectedValue, attribute, errorMessage);
    }

    public void AssertEquals(By by, String expectedValue, Attribute attribute, String errorMessage) {
        AssertEquals(by, null, expectedValue, attribute, errorMessage);
    }

    public void AssertEquals(By by, String type, String expectedValue, Attribute attribute, String errorMessage) {
        assertEquals(expectedValue, Android.driverIos.findElement(by).getAttribute(attribute.toString()));
    }

    public enum Attribute {
        LABEL, NAME, VALUE, XPATH, TEXT, TAGNAME, VISIBLE, COLOR, CHECKED;

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
