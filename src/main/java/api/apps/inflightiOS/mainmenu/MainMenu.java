package api.apps.inflightiOS.mainmenu;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Swipe;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class MainMenu implements Activity {

    public By settingCategory = By.id("Settings");
    public By burgerMenu = By.id("burgerMenu");

    Gestures gestures = new Gestures();
    AssertsUtils assertsUtils = new AssertsUtils();
    Swipe swipe = new Swipe();
    Waiters waiters = new Waiters();


    public MainMenu clickBurgerMenu() {
        try {
            MyLogger.log.info("Click on Burger Menu from MainMenu");
            gestures.clickOn(burgerMenu);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Burger Menu from MainMenu");
        }
    }

    public MainMenu clickSettings() {
        try {
            MyLogger.log.info("Click on settings category from MainMenu");
            gestures.clickOn(settingCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on settings category from MainMenu");
        }
    }

    public MainMenu ValidateHamburgerMenuIsDisplayed() {
        try {
            MyLogger.log.info("Validate if hamburger menu is displayed in Main Menu");
            waiters.waitForElementVIsibilityIOS(burgerMenu);
            assertsUtils.isElementDisplayed(burgerMenu);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate if hamburger menu is displayed in Main Menu");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
