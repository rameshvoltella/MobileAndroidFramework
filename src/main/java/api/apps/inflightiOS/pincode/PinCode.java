package api.apps.inflightiOS.pincode;

import api.android.Android;
import api.apps.inflightiOS.firsttimeview.FirstTimeView;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Swipe;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class PinCode implements Activity {

    Gestures gestures = new Gestures();
    AssertsUtils assertsUtils = new AssertsUtils();
    Swipe swipe = new Swipe();
    Waiters waithelper = new Waiters();

    //Elements from SETTINGS PIN CODE
    public By pinCodeLabel = By.id("PIN code");
    public By pinCodeBtn = By.xpath("//XCUIElementTypeSwitch[1][@name='PIN code']");
    public By changePin = By.id("Change PIN code");
    public By touchIdLabel = By.id("Use Touch ID");
    public By touchIdBtn = By.xpath("//XCUIElementTypeSwitch[1][@name='Use Touch ID']");


    //Elements from PIN Page which appears on 1st start, logging, etc
    public By createAPinCode = By.id("Create a PIN code");
    public By getCreateAPinCodeDescription = By.id("A PIN code will protect your data and will allow no one to access the app without your consent");
    public By setUpLaterPin = By.id("Setup PIN code later");
    public By touchIdAlert = By.id("Authenticate to login");
    public By cancelTouchIdAlert = By.id("Cancel");

    //elements from enable touch id Page
    public By enableTouchIdLabel = By.id("Enable Touch ID");
    public By touchIdDescription = By.id("In order to use your fingerprint instead of your PIN, please press the  Home Button multiple times");
    public By fingerPrintIcon = By.id("fingerprint");
    public By enableTouchIdLater = By.id("Enable Touch ID later");
    public By enableDisableTouch = By.id("");

    public By enterPinLabel = By.id("Enter PIN");
    public By verifyPinLabel = By.id("Verify your new Pin");
    public By enterOldPin = By.id("Enter old PIN");
    //    public By enterOldPin = By.xpath("//XCUIElementTypeStaticText[1][@name='Enter old PIN']");
    public By enterNewPin = By.id("Enter new PIN");
    public By infoBtn = By.id("Info");
    public By cancelBtn = By.id("Cancel");
    public By digitOne = By.id("1");


    public PinCode click_digit(int i) {
        try {
            String pinNumber = Integer.toString(i);
//            String pinNumber = "" + i;
            MyLogger.log.info("Tap on pincode number: " + pinNumber + " button from FTW");
            gestures.clickOn(By.id(pinNumber));
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on pincode number: " + i + "to access contacts");
        }
    }

    public PinCode unlockWithPinCode(int digit1, int digit2, int digit3, int digit4, int digit5, int digit6) {
        try {
            MyLogger.log.info("dismiss touch id alert");
            try {
                WebElement alert = Android.driverIos.findElement(touchIdAlert);
                if (alert.isDisplayed()) {
                    MyLogger.log.info(alert + " Was found and now we are dismissing it");
                    gestures.clickOn(cancelTouchIdAlert);
                }
            } catch (WebDriverException e) {
                //do nothing
            }
            MyLogger.log.info("Entering pin digits");
            click_digit(digit1);
            click_digit(digit2);
            click_digit(digit3);
            click_digit(digit4);
            click_digit(digit5);
            click_digit(digit6);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on pincode number to access contacts");
        }
    }

    public PinCode unlockWithPinCode2() {
        try {
            MyLogger.log.info("dismiss touch id alert");
            try {
                WebElement alert = Android.driverIos.findElement(touchIdAlert);
                if (alert.isDisplayed()) {
                    MyLogger.log.info(alert + " Was found and now we are dismissing it");
                    gestures.clickOn(cancelTouchIdAlert);
                }
            } catch (Throwable e) {
                //do nothing
            }
            MyLogger.log.info("Entering pin digits");
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);
            gestures.click("1", "XCUIElementTypeButton", true);

            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on pincode number to access contacts");
        }
    }

    public PinCode enableDisablePinCode() {
        try {
            MyLogger.log.info("Click on Pin slide to enable or disable it");
            gestures.clickOn(changePin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Pin slide to enable or disable it");
        }
    }

    public PinCode enableDisableTouchId() {
        try {
            MyLogger.log.info("Click on Touch slide to enable or disable it");
            gestures.clickOn(touchIdBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Touch slide to enable or disable it");
        }
    }

    public PinCode clickChangePin() {
        try {
            MyLogger.log.info("Click on Pin Change option from settings");
            gestures.clickOn(changePin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on change pin button");
        }
    }

    public PinCode clickSetupLaterPin() {
        try {
            MyLogger.log.info("Click on Setup Pin later from create a pin code page");
            gestures.clickOn(setUpLaterPin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Setup Pin later from create a pin code page");
        }
    }

    public PinCode clickTouchSlideInEnableTouchIdPage() {
        try {
            MyLogger.log.info("Click on Enable/Disable touch slide in Enable Touch Id page from fresh install");
            gestures.clickOn(enableDisableTouch);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Enable/Disable touch slide in Enable Touch Id page from fresh install");
        }
    }

    public PinCode clickEnableTouchLater() {
        try {
            MyLogger.log.info("Click on Enable/Disable touch ID later in Enable Touch Id page from fresh install");
            gestures.clickOn(enableTouchIdLater);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Enable/Disable touch ID later in Enable Touch Id page from fresh install");
        }
    }


    //validate elements from Pin Code options from application settings
    public PinCode validatePinCodeOptions() {
        try {
            MyLogger.log.info("validate elements from Pin Code options from application settings");
            waithelper.waitForElementVIsibilityIOS(pinCodeLabel);
            assertsUtils.isElementDisplayed(pinCodeLabel);
            try {
                WebElement element = Android.driverIos.findElement(touchIdLabel);
                if (element.isDisplayed()) {
                    assertsUtils.isElementDisplayed(touchIdLabel);
                }
            } catch (WebDriverException e) {
                //do nothing
            }
            assertsUtils.isElementDisplayed(changePin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate elements from Pin Code options from application settings");
        }
    }


    //validate elements from Create Pin Code page which appears on fresh install
    public PinCode validateCreatePinCodePageElements() {
        try {
            MyLogger.log.info("Validate elements from Create Pin Code page which appears on fresh install");
            waithelper.waitForElementVIsibilityIOS(createAPinCode);
            assertsUtils.isElementDisplayed(createAPinCode);
            assertsUtils.isElementDisplayed(getCreateAPinCodeDescription);
            assertsUtils.isElementDisplayed(setUpLaterPin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate elements from Create Pin Code page which appears on fresh install");
        }
    }

    //validate elements from Create Pin Code page (Verify PIN) which appears on fresh install
    public PinCode validateCreatePinCodeVerifyPageElements() {
        try {
            MyLogger.log.info("Validate elements from Create Pin Code page (Verify PIN) which appears on fresh install");
            waithelper.waitForElementVIsibilityIOS(createAPinCode);
            assertsUtils.isElementDisplayed(createAPinCode);
            assertsUtils.isElementDisplayed(getCreateAPinCodeDescription);
            assertsUtils.isElementDisplayed(setUpLaterPin);
            assertsUtils.isElementDisplayed(verifyPinLabel);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate elements from Create Pin Code page (Verify PIN) which appears on fresh install");
        }
    }

    //validate elements from Enable Touch Id page which appears on fresh install
    public PinCode validateEnableTouchIdPageElements() {
        try {
            MyLogger.log.info("Validate elements from Enable Touch Id page which appears on fresh install");
            waithelper.waitForElementVIsibilityIOS(enableTouchIdLabel);
            assertsUtils.isElementDisplayed(enableTouchIdLabel);
            assertsUtils.isElementDisplayed(touchIdDescription);
            assertsUtils.isElementDisplayed(fingerPrintIcon);
            assertsUtils.isElementDisplayed(enableTouchIdLater);
//            assertsUtils.isElementDisplayed(enableDisableTouch);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Validate elements from Enable Touch Id page which appears on fresh install");
        }
    }

    //validate elements from Change Pin page
    public PinCode validateElementsFromChangePinPage() {
        try {
            MyLogger.log.info("validate elements from Change Pin page");
            waithelper.waitForElementVIsibilityIOS(enterOldPin);
            assertsUtils.isElementDisplayed(enterOldPin);
            assertsUtils.isElementDisplayed(infoBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate elements from Change Pin page");
        }
    }

    //validate elements from Change Pin page - Enter New Pin
    public PinCode validateElementsFromChangePinPageNewPin() {
        try {
            MyLogger.log.info("validate elements from Change Pin page");
            waithelper.waitForElementVIsibilityIOS(enterNewPin);
            assertsUtils.isElementDisplayed(enterNewPin);
            assertsUtils.isElementDisplayed(infoBtn);
            assertsUtils.isElementDisplayed(cancelBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate elements from Change Pin page");
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
