package api.apps.inflightiOS.help;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class HelpView implements Activity {

    Gestures gestures = new Gestures();
    AssertsUtils assertsUtils = new AssertsUtils();
    Waiters waiters = new Waiters();

    //navigation bar elements
    public By backBtn = By.xpath("//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[1]");
    public By helpPageTitle = By.id("Help");

    //elements from help FTW
    public By nextSlide = By.id("Next");
    public By backSlide = By.id("Back");
    public By finishSlide = By.id("Finish");
    public By pageIndicator = By.xpath("//XCUIElementTypePageIndicator[1][contains(@value,'page')]");


    //click back button to exit Help page
    public HelpView clickBackToExitHelp() {
        try {
            MyLogger.log.info("Tap on back button to exit Help Page");
            gestures.clickOn(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on back button to exit Help Page");
        }
    }

    //validate that we are in 1st page from help FTW
    public HelpView validateFTWfromHelp(String value) {
        try {
            MyLogger.log.info("Validate elements from help FTW page 1");
            waiters.waitForElementVIsibilityIOS(helpPageTitle);
            assertsUtils.isElementDisplayed(helpPageTitle);
            assertsUtils.AssertEquals(pageIndicator, value, AssertsUtils.Attribute.VALUE, "Value" + value + "is not the same with the one from current page in FTW");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate elements from help FTW page 1");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
