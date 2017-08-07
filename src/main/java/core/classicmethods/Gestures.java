package core.classicmethods;

import api.android.Android;
import core.MyLogger;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by lumihai on 5/24/2017.
 */
public class Gestures {

    public static WebDriverWait waitDriver = new WebDriverWait(Android.driverIos, 30);
    Waiters waithelper = new Waiters();
    Swipe swipe = new Swipe();

    public static WebDriverWait waiting() {
        return waitDriver;
    }

    //find element by xpath and direction
    public void findElement(String xpath, Direction direction) {
        findElement(xpath, direction, HorizontalPosition.CENTER);
    }

    //find element by xpath, direction and horizontal position
    public void findElement(String xpath, Direction direction, HorizontalPosition horizontalPosition) {
        findElement(By.xpath(xpath), direction, null, horizontalPosition);
    }

    //find element by By and direction
    public void findElement(By by, Direction direction) {
        findElement(by, direction, null);
    }

    //find element by By, direction and strict id
    public void findElement(By by, Direction direction, String strictId) {
        findElement(by, direction, strictId, HorizontalPosition.CENTER);
    }

    //find element By by, direction, strict id and horizontal position
    @SuppressWarnings("Since15")
    public void findElement(By by, Direction direction, String strictId, HorizontalPosition horizontalPosition) {
        int retry = 0;
        do {
            try {
                WebElement we = null;
                if ((strictId == null) || (strictId.isEmpty())) {
                    we = Android.driverIos.findElement(by);
                } else {
                    List<WebElement> weList = Android.driverIos.findElements(by);
                    for (WebElement item : weList) {
                        String name = item.getAttribute(Attribute.TEXT.toString());
                        if (name.compareTo(strictId) == 0) {
                            we = item;
                            break;
                        }
                    }
                    if (we == null) {
                        MyLogger.log.info(String.format("Element was not found" + strictId));
                    }
                }
                if (we.isDisplayed()) {
                    System.out.println("Element found and visible");
                    return;
                } else {
                    System.out.println(String.format("Element found but it is not visible", retry));
                    retry++;
                    switch (direction) {
                        case DOWN:
                            swipe.scrollDown(horizontalPosition);
                            break;
                        case UP:
                            swipe.scrollUp(horizontalPosition);
                            break;

                    }
                }
            } catch (NoSuchElementException e) {
                retry++;
                System.out.println(String.format("Element found but it is not visible", retry));
                if (retry > 2) {
                    switch (direction) {
                        case DOWN:
                            swipe.scrollDown(horizontalPosition);
                            break;
                        case UP:
                            swipe.scrollUp(horizontalPosition);
                            break;

                    }
                }
            }
        } while (retry < 10);
    }

    //click on mobile element of type By
    public void clickOn(By element) {
        waithelper.waitForElementVIsibility(element);
        Android.driverIos.findElement(element).click();
    }

    //clear xpath element
    public void clear(String xpath) {
        try {
            clear(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            MyLogger.log.info(String.format("Xpath not found" + xpath));
            throw e;
        }
    }

    //clear element (used for elements which are not xpaths_
    public void clear(By by) {
        try {
            Android.driverIos.findElement(by).clear();
        } catch (NoSuchElementException e) {
            System.out.println("Element not found to clear");
        }
    }

    //click on element by id with should be clickable or not
    public void click(By by, boolean isclickable) {
        WebElement we = null;
        we = Android.driverIos.findElement(by);
        if (we != null) {
            if (isclickable) {
                waiting().until(ExpectedConditions.elementToBeClickable(we));
                we.click();
            } else {
                Point locationElement = we.getLocation();
                Dimension sizeElement = we.getSize();
                int tapX = locationElement.x + (sizeElement.width / 2);
                int tapY = locationElement.y + (sizeElement.height / 2);
//                getDriver().tap(1, tapX, tapY, 1);
//                new TouchAction(getDriver()).tap(tapX, tapY).release().perform();
                new TouchAction(Android.driverIos).press(tapX, tapY).waitAction().release().perform();
                return;
            }
        }
    }

    //click using xpath
    public void click(String xpath) {
        try {
            click(xpath, Attribute.XPATH);
        } catch (NoSuchElementException e) {
            MyLogger.log.info(String.format("Xpath was not found" + xpath));
            throw e;
        }
    }

    //click on web element using the attribute and attribute value
    public void click(String attributeValue, Attribute attribute) {
        click(attributeValue, attribute, true);
    }

    //click on web element using the attribute and attribute value + boolean that value is strict
    public void click(String attributeValue, Attribute attribute, boolean strictValue) {
        click(attributeValue, attribute, strictValue, HorizontalPosition.CENTER, VerticalPosition.CENTER, true);
    }

    //click on web element using attribute, attribute value, boolean strict value, horizontal/vertical position, boolean should be clickable
    public void click(String attributeValue, Attribute attribute, boolean strictValue, HorizontalPosition horizontalPositionFinger, VerticalPosition verticalPositionFinger, boolean isclickable) {
        click(attributeValue, attribute, strictValue, horizontalPositionFinger, verticalPositionFinger, isclickable, 1);
    }

    //click on web element using attributes, strict value and horizontal/vertical position
    public void click(String attributeValue, Attribute attribute, boolean strictValue, HorizontalPosition horizontalPositionFinger, VerticalPosition verticalPositionFinger, boolean isclickable, int timeToPressInMilliSeconds) {
        WebElement we = null;
        switch (attribute) {
            case XPATH:
                we = Android.driverIos.findElement(By.xpath(attributeValue));
                break;
            case TEXT:
                List<WebElement> weList = Android.driverIos.findElements(By.name(attributeValue));
                for (WebElement item : weList) {
                    String name = item.getAttribute(Attribute.TEXT.toString());
                    if (strictValue) {
                        if (name.compareTo(attributeValue) == 0) {
                            we = item;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                break;
        }

        if (we != null) {
            if (isclickable) {
                waiting().until(ExpectedConditions.elementToBeClickable(we));
                we.click();
                return;
            } else {
                Point locationElement = we.getLocation();
                Dimension sizeElement = we.getSize();
                int tapX = 0;
                int tapY = 0;
                switch ((horizontalPositionFinger)) {
                    case CENTER:
                        tapX = locationElement.x + (sizeElement.width / 2);
                        break;
                    case LEFT:
                        tapX = locationElement.x + 1;
                        break;
                    case RIGHT:
                        tapX = locationElement.x + sizeElement.width - 1;
                        break;
                    default:
                        break;
                }
                switch (verticalPositionFinger) {
                    case CENTER:
                        tapX = locationElement.y + (sizeElement.height / 2);
                        break;
                    case UP:
                        tapX = locationElement.y + 1;
                        break;
                    case DOWN:
                        tapX = locationElement.y + sizeElement.height - 1;
                        break;
                    default:
                        break;
                }
//                getDriver().tap(1, tapX, tapY, timeToPressInMilliSeconds);
//                new TouchAction(getDriver()).press(tapX, tapY).waitAction().release().perform();
                new TouchAction(Android.driverIos).tap(tapX, tapY).release().perform();
                return;
            }
        } else {
            System.out.println("Element was not found" + attributeValue);
        }

    }

    public void sendText(By by, String inputText) {
        try {
            WebElement we = Android.driverIos.findElement(by);
            we.clear();
            we.sendKeys();
            Android.driverIos.findElement(by).sendKeys(inputText);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("elemen was not found to clear or to send keys to it");
        }
    }

    public void clickCustomUsermail(String usermail) {
        WebElement we = Android.driverIos.findElement(By.xpath("//android.widget.TextView[@resource-id='de.telekom.mail:id/drawer_item_text_view' and contains(@text,'" + usermail + "')]"));
        we.click();
    }


    //list with attributes from detecting web elements
    public enum Attribute {
        LABEL, NAME, VALUE, XPATH, TEXT, TAGNAME, VISIBLE, COLOR, CHECKED, RESOURCEID;

        /**
         * Returns the name of the enum constant, in lowercase
         */
        @Override
        public String toString() {
            String s = super.toString();
            return s.toLowerCase();
        }
    }

    //position helpers (horizontal position)
    public enum HorizontalPosition {
        LEFT, RIGHT, CENTER;
    }

    //position helpers (Vertical position)
    public enum VerticalPosition {
        UP, CENTER, DOWN, ONETHIRD;
    }

    //direction for finding elements
    public enum Direction {
        UP, DOWN;
    }
}
