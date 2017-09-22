package api.apps.inflightiOS.upgradeview;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class UpgradeView implements Activity {

    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    //elements from UpgradeView top bar
    public By upgradePageTitle = By.id("Upgrade");
    public By burgerBtn = By.id("burgerMenu");
    public By accountBtn = By.id("user");

    //elements from Upgrade Page description
    public By packagesDescription = By.id("Your WIFI package");
    public By poweredBy = By.id("Powered by");

    //elements for Social package
    public By socialPackageName = By.id("Social");
    public By socialSpeed = By.id("Up to 400kb/s");
    public By socialPrice = By.id("3,00 €");

    //upgrade options disclaimer and description
    public By upgradeOptions = By.id("Upgrade Options");
    public By infoIconForUpgrade = By.id("actionIcon");

    //elements from Surf package
    public By surfUpgradeNowBtn = By.xpath("//XCUIElementTypeCell[2]/XCUIElementTypeButton[1][@name='Upgrade Now']");
    public By surfPackageName = By.id("Surf");
    public By surfSpeed = By.id("Up to 1Mb/s");
    public By surfPrice = By.id("Only + 4€");

    //elements from Stream package
    public By streamUpgradeNowBtn = By.xpath("//XCUIElementTypeCell[3]/XCUIElementTypeButton[1][@name='Upgrade Now']");
    public By streamPackageNamne = By.id("Stream");
    public By streammSpeed = By.id("Up to 15Mb/s");
    public By streamPrice = By.id("Only + 9€");

    //Upgrade Purchase confirmation notification for all packages
    public By cancelBtn = By.id("Cancel");
    public By buyNowBtn = By.id("Buy Now");
    public By uopgradeNotificationTitle = By.id("Upgrade");
    public By upgradeSurfDescriptionNot = By.xpath("//XCUIElementTypeStaticText[2][contains(@name,'Surf')]");
    public By upgradeStreamDescriptionNot = By.xpath("//XCUIElementTypeStaticText[2][contains(@name,'Stream')]");

    //click on burger menu button
    public UpgradeView clickBurgerMenu() {
        try {
            MyLogger.log.info("Click on burger menu button");
            gestures.clickOn(burgerBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on on burger menu button");
        }
    }

    //click on account button
    public UpgradeView clickAccountBtn() {
        try {
            MyLogger.log.info("Click on account button");
            gestures.clickOn(accountBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on account button");
        }
    }

    //click Upgrade Now Surf
    public UpgradeView clickUpgradeSurf() {
        try {
            MyLogger.log.info("Click on Upgrade Surf button");
            gestures.clickOn(surfUpgradeNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Upgrade Surf button");
        }
    }

    //click Upgrade Now Stream
    public UpgradeView clickUpgradeStream() {
        try {
            MyLogger.log.info("Click on Upgrade Stream button");
            gestures.clickOn(streamUpgradeNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Upgrade Stream button");
        }
    }

    //validate upgrade page title and button from top bar
    public UpgradeView validateUpgradePageTitleAndButtons() {
        try {
            MyLogger.log.info("Validate upgrade page title and button from top bar");
            waiters.waitForElementVIsibilityIOS(burgerBtn);
            assertsUtils.AssertExists(burgerBtn, "Burger Button is not displayed in Top Bar for Upgrade view");
            assertsUtils.AssertExists(accountBtn, "Account button is not displayed in Top Bar for upgrade view");
            assertsUtils.AssertEquals(upgradePageTitle, "Upgrade", AssertsUtils.Attribute.NAME, "Page title is not Upgrade or element could not be found");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on account button");
        }
    }

    //Validate upgrade page title brief description
    public UpgradeView validateUpgradePageBriefDescription() {
        try {
            MyLogger.log.info("Validate upgrade page title brief description");
            assertsUtils.AssertExists(packagesDescription, "Your WIFI package is not displayed in Upgarde View");
            assertsUtils.AssertExists(poweredBy, "Powered by is not displayed in Upgrade View");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate upgrade page title brief description");
        }
    }

    //Validate upgrade options brief description
    public UpgradeView validateUpgradeOptionsBriefDescription() {
        try {
            MyLogger.log.info("Validate upgrade options brief description");
            assertsUtils.AssertExists(upgradeOptions, "Upgarde option brief description is not displayed");
            assertsUtils.AssertExists(infoIconForUpgrade, "Info button is not displayed in Upgrade View");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate upgrade options brief description");
        }
    }

    //Validate Social package elements
    public UpgradeView validateSocialPackage() {
        try {
            MyLogger.log.info("Validate Social package elements");
            waiters.waitForElementVIsibilityIOS(socialPackageName);
            assertsUtils.isElementDisplayed(socialPackageName);
            assertsUtils.isElementDisplayed(socialPrice);
            assertsUtils.isElementDisplayed(socialSpeed);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Social package elements");
        }
    }

    //Validate Surf package elements
    public UpgradeView validateSurfPackage() {
        try {
            MyLogger.log.info("Validate Surf package elements");
            waiters.waitForElementVIsibilityIOS(surfPackageName);
            assertsUtils.isElementDisplayed(surfPackageName);
            assertsUtils.isElementDisplayed(surfPrice);
            assertsUtils.isElementDisplayed(surfSpeed);
            assertsUtils.isElementDisplayed(surfUpgradeNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Surf package elements");
        }
    }

    //Validate Stream package elements
    public UpgradeView validateStreamPackage() {
        try {
            MyLogger.log.info("Validate Stream package elements");
            waiters.waitForElementVIsibilityIOS(streamPackageNamne);
            assertsUtils.isElementDisplayed(streamPackageNamne);
            assertsUtils.isElementDisplayed(streamPrice);
            assertsUtils.isElementDisplayed(streammSpeed);
            assertsUtils.isElementDisplayed(streamUpgradeNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Stream package elements");
        }
    }

    //Validate Upgrade to Surf package elements
    public UpgradeView validateSurfUpgradeConfirmation() {
        try {
            MyLogger.log.info("Validate Surf Upgrade screen confirmation");
            waiters.waitForElementVIsibilityIOS(uopgradeNotificationTitle);
            assertsUtils.AssertExists(upgradeSurfDescriptionNot, "Social Upgrade description is not displayed");
            assertsUtils.isElementDisplayed(buyNowBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Surf Upgrade screen confirmation");
        }
    }

    //Validate Upgrade to Stream package elements
    public UpgradeView validateStreamfUpgradeConfirmation() {
        try {
            MyLogger.log.info("Validate Stream Upgrade screen confirmation");
            waiters.waitForElementVIsibilityIOS(uopgradeNotificationTitle);
            assertsUtils.AssertExists(upgradeStreamDescriptionNot, "Stream upgrade description is not displayed");
            assertsUtils.isElementDisplayed(buyNowBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Stream Upgrade screen confirmation");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
