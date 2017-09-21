package api.apps.inflightiOS.connectview;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class ConnectView implements Activity {

    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    public String PURCHASE_SOCIAL_DESCRIPTION = "You are about to purchase a Social* Voucher from T-Mobile.  Are you sure you want to do this?  *If 30 MB are consumed within 5 minutes, then the bandwidth is throttled down to 400 kbps";

    //elements from top bar Connectivity page
    public By burgerMenu = By.id("burgerMenu");
    public By connectPageTitle = By.id("Connect");
    public By accountBtn = By.id("user");

    //elements from description area (Area above packages)
    public By wifiPackages = By.id("WIFI packages");
    public By infopackagesButton = By.id("actionIcon");
    public By poweredBy = By.id("Powered by");
    public By yellowIconForBuy = By.id("exclamationIcon");

    //Social package
    public By socialBuyNowBtn = By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeButton[1][@name='Buy Now']");
    public By socialPackageName = By.id("Social");
    public By socialSpeed = By.id("Up to 400kb/s");
    public By socialPrice = By.id("3€");

    //Purchase confirmation notification for all packages
    public By cancelBtn = By.id("Cancel");
    public By buyNowBtn = By.id("Buy Now");
    public By purchaseSocialNotificationTitle = By.id("Purchase");
    public By purchaseSocialDescriptionNot = By.xpath("//XCUIElementTypeStaticText[2][contains(@name,'Social')]");
    public By purchaseSurfDescriptionNot = By.id("You are about to purchase a Surf* Voucher from T-Mobile.  Are you sure you want to do this?  *If 30 MB are consumed within 5 minutes, then the bandwidth is throttled down to 400 kbps");
    public By purchaseStreamDescriptionNot = By.id("You are about to purchase a Stream* Voucher from T-Mobile.  Are you sure you want to do this?  *If 30 MB are consumed within 5 minutes, then the bandwidth is throttled down to 400 kbps");


    //Surf package
    public By surfBuyNowBtn = By.xpath("//XCUIElementTypeCell[2]/XCUIElementTypeButton[1][@name='Buy Now']");
    public By surfPackageName = By.id("Surf");
    public By surfSpeed = By.id("Up to 1Mb/s");
    public By surfPrice = By.id("7€");

    //Stream package
    public By streamBuyNowBtn = By.xpath("//XCUIElementTypeCell[3]/XCUIElementTypeButton[1][@name='Buy Now']");
    public By streamPackageNamne = By.id("Stream");
    public By streammSpeed = By.id("Up to 15Mb/s");
    public By streamPrice = By.id("12€");

    //partner login category
    public By partnetLoginCategory = By.id("Partner Login");

    //Telekom Partner Login
    public By telekomLogin = By.id("telekomLogo2");
    public By telekomPrice = By.id("1,49€/10min");
    public By telekomnPartnerDescription = By.id("In order to start using data from T-Mobile you have to login with your credentials using the form bellow.");
    public By emailLabel = By.id("Email");
    //TODO: missing identification for e-mail field in telekom Partner Login
    public By telekomMailInput = By.id("");
    public By passwordLabel = By.id("Password");
    //TODO: missing identification for password field in telekom Partner Login
    public By telekomPasswordInput = By.id("");
    public By buyNowTelekomPartnerBtn = By.xpath("//XCUIElementTypeSecureTextField[1]/following-sibling::XCUIElementTypeButton[1]");


    //click buy now Social package
    public ConnectView clickBuyNowSocial() {
        try {
            MyLogger.log.info("Click on buy now Social package");
            gestures.clickOn(socialBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on buy now Social package");
        }
    }

    //click on buy now Surf package
    public ConnectView clickBuyNowSurf() {
        try {
            MyLogger.log.info("Click on buy now Surf package");
            gestures.clickOn(surfBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on buy now Surf package");
        }
    }

    //click on buy now Stream package
    public ConnectView clickBuyNowStream() {
        try {
            MyLogger.log.info("Click on buy now Stream package");
            gestures.clickOn(streamBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on buy now Stream package");
        }
    }

    //click buy now - confirm purchase
    public ConnectView clickBuyNow() {
        try {
            MyLogger.log.info("Click on buy now");
            gestures.clickOn(buyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on buy now");
        }
    }

    //click Cancel - confirm purchase
    public ConnectView clickCancelBuy() {
        try {
            MyLogger.log.info("Click on cancel buy");
            gestures.clickOn(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on cancel buy");
        }
    }

    //click on Telekom Partner Login
    public ConnectView clickTelekomPartnerLogin() {
        try {
            MyLogger.log.info("Click on Telekom Partner Login");
            gestures.clickOn(telekomLogin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Telekom Partner Login");
        }
    }

    //insert e-mail address for Telekom Partner Login
    public ConnectView insertEmailTelekomLogin(String element) {
        try {
            MyLogger.log.info("Send text to e-mail input for Telekom Partner Login");
            gestures.sendText(telekomMailInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to e-mail input for Telekom Partner Login");
        }
    }

    //insert password for e-mail address for Telekom Partner Login
    public ConnectView insertPasswordTelekomLogin(String element) {
        try {
            MyLogger.log.info("Send text to password input for Telekom Partner Login");
            gestures.sendText(telekomPasswordInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to password input for Telekom Partner Login");
        }
    }

    //Click buy now for Telekom Partner Login
    public ConnectView clickBuyNowTelekomLogin() {
        try {
            MyLogger.log.info("Click buy now Telekom Partner Login");
            gestures.clickOn(buyNowTelekomPartnerBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on buy now Telekom Partner Login");
        }
    }

    //Validate top bar elements
    public ConnectView validateConnectPageTileAndBar() {
        try {
            MyLogger.log.info("Validate page title and top bar elements");
            waiters.waitForElementVIsibilityIOS(burgerMenu);
            assertsUtils.isElementDisplayed(burgerMenu);
            assertsUtils.isElementDisplayed(connectPageTitle);
            assertsUtils.isElementDisplayed(accountBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate page title and top bar elements");
        }
    }


    //Validate elements from description area
    public ConnectView validateElementsFromDescriptionArea() {
        try {
            MyLogger.log.info("Validate elements from description above the packages");
            waiters.waitForElementVIsibilityIOS(wifiPackages);
            assertsUtils.isElementDisplayed(wifiPackages);
            assertsUtils.isElementDisplayed(infopackagesButton);
            assertsUtils.isElementDisplayed(poweredBy);
//            assertsUtils.isElementDisplayed(yellowIconForBuy);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate elements from description above the packages");
        }
    }

    //Validate Social package elements
    public ConnectView validateSocialPackage() {
        try {
            MyLogger.log.info("Validate Social package elements");
            waiters.waitForElementVIsibilityIOS(socialPackageName);
            assertsUtils.isElementDisplayed(socialPackageName);
            assertsUtils.isElementDisplayed(socialPrice);
            assertsUtils.isElementDisplayed(socialSpeed);
            assertsUtils.isElementDisplayed(socialBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Social package elements");
        }
    }

    //Validate Surf package elements
    public ConnectView validateSurfPackage() {
        try {
            MyLogger.log.info("Validate Surf package elements");
            waiters.waitForElementVIsibilityIOS(surfPackageName);
            assertsUtils.isElementDisplayed(surfPackageName);
            assertsUtils.isElementDisplayed(surfPrice);
            assertsUtils.isElementDisplayed(surfSpeed);
            assertsUtils.isElementDisplayed(surfBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Surf package elements");
        }
    }

    //Validate Stream package elements
    public ConnectView validateStreamPackage() {
        try {
            MyLogger.log.info("Validate Stream package elements");
            waiters.waitForElementVIsibilityIOS(streamPackageNamne);
            assertsUtils.isElementDisplayed(streamPackageNamne);
            assertsUtils.isElementDisplayed(streamPrice);
            assertsUtils.isElementDisplayed(streammSpeed);
            assertsUtils.isElementDisplayed(streamBuyNowBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Stream package elements");
        }
    }

    //Validate Telekom Partner Login package elements
    public ConnectView validateTelekomPartnerPageElem() {
        try {
            MyLogger.log.info("Validate Telekom Partner Login package elements");
            waiters.waitForElementVIsibilityIOS(telekomPrice);
            assertsUtils.isElementDisplayed(telekomPrice);
            assertsUtils.isElementDisplayed(telekomnPartnerDescription);
            assertsUtils.isElementDisplayed(emailLabel);
            assertsUtils.isElementDisplayed(telekomMailInput);
            assertsUtils.isElementDisplayed(passwordLabel);
            assertsUtils.isElementDisplayed(telekomPasswordInput);
            assertsUtils.isElementDisplayed(buyNowTelekomPartnerBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Telekom Partner Login package elements");
        }
    }

    //Validate Stream package elements
    public ConnectView validateSocialPurchaseConfirmation() {
        try {
            MyLogger.log.info("Validate social Purchase screen confirmation");
            waiters.waitForElementVIsibilityIOS(buyNowBtn);
//            assertsUtils.AssertEquals(purchaseSocialDescriptionNot, PURCHASE_SOCIAL_DESCRIPTION, AssertsUtils.Attribute.NAME, "Purchase description for Social package is not correct");
            assertsUtils.AssertExists(purchaseSocialDescriptionNot, "Social purchase description is not displayed");
            assertsUtils.isElementDisplayed(buyNowBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate social Purchase screen confirmation");
        }
    }

    //Validate Surf package elements
    public ConnectView validateSurfPurchaseConfirmation() {
        try {
            MyLogger.log.info("Validate Surf Purchase screen confirmation");
            waiters.waitForElementVIsibilityIOS(purchaseSocialNotificationTitle);
            assertsUtils.isElementDisplayed(purchaseSurfDescriptionNot);
            assertsUtils.isElementDisplayed(buyNowBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Surf Purchase screen confirmation");
        }
    }

    //Validate Stream package elements
    public ConnectView validateStreamfPurchaseConfirmation() {
        try {
            MyLogger.log.info("Validate Stream Purchase screen confirmation");
            waiters.waitForElementVIsibilityIOS(purchaseSocialNotificationTitle);
            assertsUtils.isElementDisplayed(purchaseStreamDescriptionNot);
            assertsUtils.isElementDisplayed(buyNowBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate Stream Purchase screen confirmation");
        }
    }

    //Validate that Telekom Partner Login category is displayed in Connect Page
    public ConnectView validateTelekomPartnerLoginDisplayedInConnectPage() {
        try {
            MyLogger.log.info("Validate that Telekom Partner Login category is displayed in Connect Page");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertsUtils.AssertEquals(telekomLogin, "telekomLogo2", AssertsUtils.Attribute.NAME, "Telekom Partner Login button was not displayed");
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate that Telekom Partner Login category is displayed in Connect Page");
        }
    }


    @Override

    public Object waitToLoad() {
        return null;
    }
}
