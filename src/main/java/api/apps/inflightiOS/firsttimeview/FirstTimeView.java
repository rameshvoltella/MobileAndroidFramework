package api.apps.inflightiOS.firsttimeview;

import api.android.Android;
import api.apps.mail.mailist.MailList;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Swipe;
import core.classicmethods.Waiters;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class FirstTimeView implements Activity {
    Gestures gestures = new Gestures();
    AssertsUtils assertsUtils = new AssertsUtils();
    Swipe swipe = new Swipe();
    Waiters waiters = new Waiters();

    public By closeBtn = By.id("close");
    public By nextBtn = By.id("Next");
    public By pageIndicator = By.xpath("//XCUIElementTypePageIndicator[1][contains(@value,'page')]");
    public By backBtn = By.id("Back");
    public By finishBtn = By.id("Finish");
    public By dontShowGainBtn = By.id("Don't show me this again");
    public By dontShowGainBtn2 = By.xpath("//XCUIElementTypeButton[1][contains(@name,'again')]");



    public FirstTimeView clickNextPageBtn() {
        try {
            MyLogger.log.info("Tap on Next button from FTW");
            gestures.clickOn(nextBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on allow to access contacts");
        }
    }

    public FirstTimeView clickBackPageBtn() {
        try {
            MyLogger.log.info("Tap on Back button from FTW");
            gestures.clickOn(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on allow to access contacts");
        }
    }

    public FirstTimeView clickDontShowAgainBtn() {
        try {
            MyLogger.log.info("Tap on do not show again button from FTW");
            gestures.click("Don't show me this again", "XCUIElementTypeButton", true);

            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Do not show again button from FTW is not available or do not exist");
        }
    }

    public FirstTimeView clickXBtn() {
        try {
            MyLogger.log.info("Tap on X button to close FTW");
            gestures.clickOn(closeBtn);

            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("X Btn from FTW is not available or do not exist and cannot be clicked");
        }
    }

    public FirstTimeView validatePageIndicatorIsVisible() {
        try {
            MyLogger.log.info("Validate Page Indicator from First Time View");
            assertsUtils.isElementDisplayed(pageIndicator);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Page Indicator from First Time View is not displayed");
        }
    }

    public FirstTimeView validatePageIndicatorNotExists() {
        try {
            MyLogger.log.info("Validate Page Indicator from First Time View does not exist");
            assertsUtils.isElementNotExist(pageIndicator);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Page Indicator from First Time View is still displayed");
        }
    }

    public FirstTimeView validatePageIndicatorValue(String value) {
        try {
            MyLogger.log.info("Validate Page Indicator Value from First Time View");
            waiters.waitForElementVIsibilityIOS(pageIndicator);
            assertsUtils.AssertEquals(pageIndicator, value, AssertsUtils.Attribute.VALUE, "Value" + value + "is not the same with the one from current page in FTW");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Page Indicator Value from First Time View is not displayed");
        }
    }

    public FirstTimeView validateDontShowState(String value) {
        try {
            MyLogger.log.info("Validate state of do not show again button in FTW");
            waiters.waitForElementVIsibilityIOS(dontShowGainBtn2);
            assertsUtils.AssertEquals(dontShowGainBtn2, value, AssertsUtils.Attribute.VALUE, "Value" + value + "is not the same with the one from current page in FTW");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("State of do not show again button cannot be verified");
        }
    }

    public FirstTimeView validateBackBtnIsVisible() {
        try {
            MyLogger.log.info("Validate Back Button from First Time View is visible");
            assertsUtils.isElementDisplayed(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Back Button from First Time View is not displayed");
        }
    }

    public FirstTimeView validateFinishBtnIsVisible() {
        try {
            MyLogger.log.info("Validate Finish Button from First Time View is visible");
            assertsUtils.isElementDisplayed(finishBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Finish Button from First Time View is not displayed");
        }
    }

    public FirstTimeView validateDontShowBtnIsVisible() {
        try {
            MyLogger.log.info("Validate Don't show again button from First Time View is displayed");
            assertsUtils.isElementDisplayed(dontShowGainBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Don't show again button from First Time View is not displayed");
        }
    }

    public FirstTimeView validateCloseBtnIsVisible() {
        try {
            MyLogger.log.info("Validate X button from First Time View is visible");
            waiters.waitForElementVIsibilityIOS(closeBtn);
            assertsUtils.isElementDisplayed(closeBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("X button from First Time View is not displayed");
        }
    }

    public FirstTimeView validateBacktnIsNotVisible() {
        try {
            MyLogger.log.info("Validate Back button from First Time View is not visible");
            assertsUtils.isElementNotExist(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Back button from First Time View is still displayed");
        }
    }

    public FirstTimeView swipeRightFTW() {
        try {
            MyLogger.log.info("Validate Back button from First Time View is not visible");
            swipe.swipeRightWithTouchiOS();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Back button from First Time View is still displayed");
        }
    }
    public FirstTimeView swipeLeftFTW() {
        try {
            MyLogger.log.info("Validate Back button from First Time View is not visible");
            swipe.swipeLeftWithTouchiOS();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Back button from First Time View is still displayed");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
