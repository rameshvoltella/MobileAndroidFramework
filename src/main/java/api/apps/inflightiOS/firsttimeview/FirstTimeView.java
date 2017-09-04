package api.apps.inflightiOS.firsttimeview;

import api.android.Android;
import api.apps.mail.mailist.MailList;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.Gestures;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class FirstTimeView implements Activity {
    Gestures gestures = new Gestures();

    public By closeBtn = By.id("close");
    public By nextBtn = By.id("Next");
    public By pageIndicator = By.xpath("//XCUIElementTypePageIndicator[1][contains(@value,'page')]");
    public By backBtn = By.id("Back");
    public By dontShowGainBtn = By.id("Don't show me this again");


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
            throw new AssertionError("Cannot tap on allow to access contacts");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
