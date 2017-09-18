package api.apps.inflightiOS.mainmenu;

import api.android.Android;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Swipe;
import core.classicmethods.Waiters;
import junit.framework.Assert;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Activity {

    //elements from main menu (left menu)
    public By closeBtn = By.id("close");
    public By accountCategory = By.id("Account");
    public By faqCategory = By.id("FAQ");
    public By aboutCategory = By.id("About");
    public By helpCategory = By.id("Help");
    public By settingCategory = By.id("Settings");
    public By logoutCategory = By.id("Logout");
    public By termsLegal = By.id("Terms of Use | Legal Notice");

    private List<String> SETTINGS_SCREEN_MENU_LIST = new ArrayList<String>();

    private final List<String> SETTINGS_GENERAL_MENU_VALUES = new ArrayList<String>();

    {
        SETTINGS_GENERAL_MENU_VALUES.add("Account");
        SETTINGS_GENERAL_MENU_VALUES.add("FAQ");
        SETTINGS_GENERAL_MENU_VALUES.add("About");
        SETTINGS_GENERAL_MENU_VALUES.add("Help");
        SETTINGS_GENERAL_MENU_VALUES.add("Settings");
        SETTINGS_GENERAL_MENU_VALUES.add("Logout");
    }


    public By burgerMenu = By.id("burgerMenu");

    Gestures gestures = new Gestures();
    AssertsUtils assertsUtils = new AssertsUtils();
    Swipe swipe = new Swipe();
    Waiters waiters = new Waiters();


    //close left menu using X button
    public MainMenu clickXBtn() {
        try {
            MyLogger.log.info("Click on X btn to close left menu");
            gestures.clickOn(closeBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on X btn to close left menu");
        }
    }

    //click Account left menu
    public MainMenu clickAccount() {
        try {
            MyLogger.log.info("Click on accountCategory from left menu");
            gestures.clickOn(accountCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on accountCategory from left menu");
        }
    }

    //click FAQ left menu
    public MainMenu clickFAQ() {
        try {
            MyLogger.log.info("Click on faqCategory from left menu");
            gestures.clickOn(faqCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on faqCategory from left menu");
        }
    }

    //click About left menu
    public MainMenu clickAbout() {
        try {
            MyLogger.log.info("Click on aboutCategory from left menu");
            gestures.clickOn(aboutCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on aboutCategory from left menu");
        }
    }

    //click Help left menu
    public MainMenu clickHelp() {
        try {
            MyLogger.log.info("Click on helpCategory from left menu");
            gestures.clickOn(helpCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on helpCategory from left menu");
        }
    }

    //click on Settings left menu
    public MainMenu clickSettings() {
        try {
            MyLogger.log.info("Click on settings category from MainMenu");
            gestures.clickOn(settingCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on settings category from MainMenu");
        }
    }

    //click Logout left menu
    public MainMenu clickLogout() {
        try {
            MyLogger.log.info("Click on logoutCategory from left menu");
            gestures.clickOn(logoutCategory);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on logoutCategory from left menu");
        }
    }

    //click LegalTerms left menu
    public MainMenu clickLegalTerms() {
        try {
            MyLogger.log.info("Click on termsLegal from left menu");
            gestures.clickOn(termsLegal);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on termsLegal from left menu");
        }
    }

    //validate if X button is displayed in left menu
    public MainMenu validateXBtnDisplayed() {
        try {
            MyLogger.log.info("Validate if Close X Btn is displayed in Main Menu");
            waiters.waitForElementVIsibilityIOS(closeBtn);
            assertsUtils.isElementDisplayed(closeBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate if Close X Btn is displayed in Main Menu");
        }
    }

    //validate Left Menu elements
    public MainMenu validateLeftMenuelementsDisplayed() {
        try {
            MyLogger.log.info("Validate if Close X Btn is displayed in Main Menu");
            waiters.waitForElementVIsibilityIOS(accountCategory);
            assertsUtils.isElementDisplayed(accountCategory);
            assertsUtils.isElementDisplayed(faqCategory);
            assertsUtils.isElementDisplayed(aboutCategory);
            assertsUtils.isElementDisplayed(helpCategory);
            assertsUtils.isElementDisplayed(settingCategory);
            try {
                WebElement alert = Android.driverIos.findElement(logoutCategory);
                if (alert.isDisplayed()) {
                    MyLogger.log.info(alert + " Was found and now we are validating it");
                    assertsUtils.isElementDisplayed(logoutCategory);
                }
            } catch (WebDriverException we) {
                //do nothing
            }
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate if Close X Btn is displayed in Main Menu");
        }
    }

    //validate if X button is displayed in left menu
    public MainMenu validatelegalTermsDisplayed() {
        try {
            MyLogger.log.info("Validate if LegalTerms is displayed in Main Menu");
            waiters.waitForElementVIsibilityIOS(termsLegal);
            assertsUtils.isElementDisplayed(termsLegal);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate if LegalTerms is displayed in Main Menu");
        }
    }

    //check settings order
    public MainMenu checkSettingsOrder() throws InterruptedException {
        SETTINGS_SCREEN_MENU_LIST = getAllElementsNames();

        Assert.assertEquals("Menu order not equal for Einstellungen", SETTINGS_GENERAL_MENU_VALUES,
                SETTINGS_SCREEN_MENU_LIST);

        for (String name : SETTINGS_GENERAL_MENU_VALUES) {
            MyLogger.log.info(name);
            int count = 0;
            while (waiters.waitForElement(By.name(name), Waiters.WaitCondition.CLICKABLE) == null && count < 10) {
                swipe.swipeUp();
                count++;
            }
        }

        return this;
    }

    public List<String> getAllElementsNames() throws InterruptedException {
        List<String> values = new ArrayList<String>();
        List<WebElement> settingsMenuLIst = getAllElements();

        for (WebElement element : settingsMenuLIst) {
            values.add(element.getAttribute(AssertsUtils.Attribute.NAME.toString()));
        }
        return values;
    }

    public List<WebElement> getAllElements() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> settingsMenuList = Android.driverIos.findElementsByXPath(
                "//XCUIElementTypeTable[1]/XCUIElementTypeCell/XCUIElementTypeStaticText");
        return settingsMenuList;
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
