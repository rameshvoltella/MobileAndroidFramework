package api.apps.inflightiOS.dashboard;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class Dashboard implements Activity {

    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    //elements from Top Bar Dashboard
    public By hamburgerMenu = By.id("burgerMenu");
    public By dashboardPageTitle = By.id("telekomLogo");
    public By accountBtn = By.id("user");

    //elements related to time and flight
    public By timeUntilflight = By.xpath("//XCUIElementTypeStaticText[1][contains(@name,'min')]");
    public By timeLeftDescription = By.id("Time Remainig to Your Flight");

    //elements from Bottom Bar
    public By homeBtn = By.id("Home");
    public By mediaBtn = By.id("Media");
    public By connectBtn = By.id("Connect");
    public By newsBtn = By.id("News");
    public By awardsBtn = By.id("Awards");

    //click on hamburger menu button
    public Dashboard clickHamburgerMenu() {
        try {
            MyLogger.log.info("Click on hamburgerMenu button");
            gestures.clickOn(hamburgerMenu);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on hamburgerMenu button");
        }
    }

    //click on account menu button menu button
    public Dashboard clickAccountMenuBtn() {
        try {
            MyLogger.log.info("Click on accountBtn button");
            gestures.clickOn(accountBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on accountBtn button");
        }
    }

    //click on home button menu button
    public Dashboard clickHomeBtn() {
        try {
            MyLogger.log.info("Click on homeBtn button");
            gestures.clickOn(homeBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on homeBtn button");
        }
    }

    //click on Media button menu button
    public Dashboard clickMediaBtn() {
        try {
            MyLogger.log.info("Click on mediaBtn button");
            gestures.clickOn(mediaBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on mediaBtn button");
        }
    }

    //click on connectBtn button menu button
    public Dashboard clickConnectBtn() {
        try {
            MyLogger.log.info("Click on connectBtn button");
            gestures.clickOn(connectBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on connectBtn button");
        }
    }

    //click on newsBtn button menu button
    public Dashboard clickNewsBtn() {
        try {
            MyLogger.log.info("Click on newsBtn button");
            gestures.clickOn(newsBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on newsBtn button");
        }
    }

    //click on awardsBtn button menu button
    public Dashboard clickAwardsBtn() {
        try {
            MyLogger.log.info("Click on awardsBtn button");
            gestures.clickOn(awardsBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on awardsBtn button");
        }
    }

    //validate elements from Dashboard top bar
    public Dashboard validateTopBarElem() {
        try {
            MyLogger.log.info("Validate displayed elements from Dashboard top bar");
            waiters.waitForElementVIsibilityIOS(hamburgerMenu);
            assertsUtils.isElementDisplayed(hamburgerMenu);
            assertsUtils.isElementDisplayed(dashboardPageTitle);
            assertsUtils.isElementDisplayed(accountBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate displayed elements from Dashboard top bar");
        }
    }

    //validate elements from Dashboard bottom bar
    public Dashboard validateBottomBarElem() {
        try {
            MyLogger.log.info("Validate displayed elements from Dashboard bottom bar");
            waiters.waitForElementVIsibilityIOS(homeBtn);
            assertsUtils.isElementDisplayed(homeBtn);
            assertsUtils.isElementDisplayed(mediaBtn);
            assertsUtils.isElementDisplayed(connectBtn);
            assertsUtils.isElementDisplayed(newsBtn);
            assertsUtils.isElementDisplayed(awardsBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate displayed elements from Dashboard bottom bar");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
