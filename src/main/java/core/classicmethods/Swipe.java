package core.classicmethods;

import api.android.Android;
import core.MyLogger;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSTouchAction;
import org.openqa.selenium.*;

import java.time.Duration;

public class Swipe {

    public static void swipeUp() {
        Dimension screenDim = Android.driver.manage().window().getSize();
        int height = screenDim.height;
        int width = screenDim.width;
        MyLogger.log.info("Swipe up using android driver swipe command");
//        Android.driver.swipe(width / 2, height - height / 3, width / 2, height / 6, 1000);
    }

    public static void swipeUpLeftPartOfScreen() {
        Dimension screenDim = Android.driver.manage().window().getSize();
        int height = screenDim.height;
        int width = screenDim.width;
        MyLogger.log.info("Swipe up in the left part of the screen using android driver swipe command");
//        Android.driver.swipe(width / 5, height - height / 3, width / 5, height / 6, 1000);
    }

    public static void swipeDown() {
        Dimension screenDim = Android.driver.manage().window().getSize();
        int height = screenDim.height;
        int width = screenDim.width;
        MyLogger.log.info("Swipe up using android driver swipe command");
//        Android.driver.swipe(width / 2, height / 6, width / 2, height - height / 3, 1000);
    }

    public void swipeLeftWithTouchiOS() {
        Dimension screenDim = Android.driverIos.manage().window().getSize();
        int height = screenDim.height;
        int width = screenDim.width;
        MyLogger.log.info("Swipe right using android driver swipe command");
        new TouchAction(Android.driverIos).press(width, height / 2).waitAction(Duration.ofSeconds(1)).moveTo(- width, height / 2).release().perform();
    }

    public void swipeRightWithTouchiOS() {
        Dimension screenDim = Android.driverIos.manage().window().getSize();
        int height = screenDim.height;
        int width = screenDim.width;
        MyLogger.log.info("Swipe right using android driver swipe command");
        new TouchAction(Android.driverIos).press(0, height / 2).waitAction(Duration.ofSeconds(1)).moveTo(width, height / 2).release().perform();


    }

    public static void refreshEmailListUntilEmailIsDisplayed(String subject) {
        int retry = 0;
        while (retry < 20) {
            try {
                WebElement we = null;
                MyLogger.log.info("Trying to find element" + subject);
                we = Android.driver.findElementById(subject);
                if (we.isDisplayed()) {
                    break;
                }
            } catch (Throwable e) {
                MyLogger.log.info("Element" + subject + "was not found so we will swipe to find it");
                swipeDown();
            }
            retry++;
        }
    }

    public static void findElementToClickOnItUpInPage2(By by) {
        WebElement we = null;
        int count = 0;
        while (count < 10) {
            try {
                MyLogger.log.info("Trying to see if element" + by + "is visible before swiping");
                we = Android.driver.findElement(by);
                if (we.isDisplayed()) {
                    MyLogger.log.info("Element" + by + " is visible so no need to swipe");
                    break;
                }
            } catch (WebDriverException e) {
                if (e.getMessage().contains("could not be located")) {
                    MyLogger.log.info("Performing swipe down to find element" + by + " up in page");
                    swipeDown();
                } else {
                    MyLogger.log.info("Verify method because element " + by + "is still not visible after swiping");
                }
            }
            count++;
        }
    }

    public static void swipeTwoFingers_FromLeftEdgeToRight() {
        Dimension screenDim = Android.driver.manage().window().getSize();

        int height = screenDim.height;
        int width = screenDim.width;

        TouchAction ta1 = new TouchAction(Android.driver);
        TouchAction ta2 = new TouchAction(Android.driver);
        MultiTouchAction ma = new MultiTouchAction(Android.driver);
        ta1.press(0, height / 4).waitAction(Duration.ofSeconds(1)).moveTo(2 * width / 3, 0).release();
        ta2.press(0, height / 4).waitAction(Duration.ofSeconds(1)).moveTo(2 * width / 3, 0).release();
        ma.add(ta1).add(ta2).perform();
    }

    public void findElementToClickOnItDownInPageLeftPart(By by) {
        WebElement we = null;
        int count = 0;
        while (count < 10) {
            try {
                we = Android.driver.findElement(by);
                if (we.isDisplayed()) {
                    break;
                }
            } catch (Throwable e) {
                if (e.getMessage().contains("could not be located")) {
                    swipeUp();
                } else {
                    System.out.println("please verify method because element is still not visible");
                }
            }
            count++;
        }

    }

    public void up_inElement(By by, int durationMilliSeconds) {

        Android.driver.findElement(by).isDisplayed();

        WebElement we = Android.driver.findElement(by);
        Point location = we.getLocation();
        Dimension size = we.getSize();

        int leftwidthpoint = location.x;
        int upheightpoint = location.y;
        int height = size.height;
        int width = size.width;

        // get middle of the element
        int startPointHorizontal = leftwidthpoint + (width / 2);
        // get lower point of element
        int startPointVertical = upheightpoint + (3 * height / 4);
        // get upper point of element
        int endPointVertical = upheightpoint + 1;
        // int endPointVertical = upheightpoint + (height / 4);

//        Android.driver.swipe(startPointHorizontal, startPointVertical, startPointHorizontal, endPointVertical, durationMilliSeconds);
    }

    public void down_inElement(By by, int durationMilliSeconds) {

        Android.driver.findElement(by).isDisplayed();

        WebElement we = Android.driver.findElement(by);
        Point location = we.getLocation();
        Dimension size = we.getSize();

        int leftwidthpoint = location.x;
        int upheightpoint = location.y;
        int height = size.height;
        int width = size.width;

        // get middle of the element
        int startPointHorizontal = leftwidthpoint + (width / 2);
        // get upper point of element
        int startPointVertical = upheightpoint + (height / 4);
        // get lower point of element
        int endPointVertical = upheightpoint + height - 1;

//        Android.driver.swipe(startPointHorizontal, startPointVertical, startPointHorizontal, endPointVertical, durationMilliSeconds);
    }


    public void right_inElement(By by, int durationMilliSeconds) {

        Android.driver.findElement(by).isDisplayed();

        WebElement we = Android.driver.findElement(by);
        Point location = we.getLocation();
        Dimension size = we.getSize();

        int leftwidthpoint = location.x;
        int upheightpoint = location.y;
        int height = size.height;
        int width = size.width;

        // get middle of the element
        int startPointHorizontal = leftwidthpoint + (width / 2);
        // get upper point of element
        int startPointVertical = leftwidthpoint + (2 * height / 4);
        // get lower point of element
        int endPointHorizontal = leftwidthpoint + 1;

        // swipe(startPointHorizontal, startPointVertical, startPointHorizontal,
        // endPointVertical, durationMilliSeconds);
//        Android.driver.swipe(startPointHorizontal, startPointVertical, endPointHorizontal, startPointHorizontal,
//                durationMilliSeconds);
    }

    public void scrollDown(Gestures.HorizontalPosition horizontalPosition) {
        int durationmlseconds = 1000;
        up(durationmlseconds, horizontalPosition);
    }

    //scroll up using duration, position of finger
    public void up(int durationmlseconds, Gestures.HorizontalPosition horizontalPosition) {
        up(durationmlseconds, horizontalPosition, false);
    }

    //scroll up using duration, position of fnger, screen dimension
    public void up(int durationmlseconds, Gestures.HorizontalPosition horizontalPosition, boolean isScreenDifferent) {
        Dimension screenSize = Android.driverIos.manage().window().getSize();

        int height = screenSize.height;
        int width = screenSize.width;

        int startHorizontal = 0;
        int startVertical = 0;
        int stopVertical = 0;

        switch (horizontalPosition) {
            case CENTER:
                startHorizontal = width / 2;
                break;
            case LEFT:
                startHorizontal = 2;
                break;
            case RIGHT:
                startHorizontal = width - 2;
                break;
            default:
                break;
        }
        startVertical = 3 * (height / 4);
        stopVertical = 2;

        if (isScreenDifferent) {

        } else {
            Android.driverIos.performTouchAction(new IOSTouchAction(Android.driverIos)).press(startHorizontal, startVertical).waitAction(Duration.ofSeconds(1)).moveTo(startHorizontal, stopVertical).release().perform();
        }
    }

    public void scrollUp(Gestures.HorizontalPosition horizontalPosition) {
        int durationmlseconds = 1000;
        down(durationmlseconds, horizontalPosition);
    }

    public void down(int durationmlseconds, Gestures.HorizontalPosition horizontalPosition) {
        down(durationmlseconds, horizontalPosition, false);
    }

    public void down(int durationmlseconds, Gestures.HorizontalPosition horizontalPosition, boolean isScreenDifferent) {
        Dimension screenSize = Android.driverIos.manage().window().getSize();

        int height = screenSize.height;
        int width = screenSize.width;

        int startHorizontal = 0;
        int startVertical = 0;
        int stopVertical = 0;

        switch (horizontalPosition) {
            case CENTER:
                startHorizontal = width / 2;
                break;
            case LEFT:
                startHorizontal = 2;
                break;
            case RIGHT:
                startHorizontal = width - 2;
                break;
            default:
                break;
        }
        startVertical = 1 * (height / 6);
        stopVertical = width - 2;

        if (isScreenDifferent) {

        } else {
            Android.driverIos.performTouchAction(new IOSTouchAction(Android.driverIos)).press(startHorizontal, startVertical).waitAction(Duration.ofSeconds(1)).moveTo(startHorizontal, stopVertical).release().perform();
        }
    }

}
