package api.apps.inflightiOS.loginview;

import api.apps.inflightiOS.pincode.PinCode;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class LoginView implements Activity{

    PinCode pinCode = new PinCode();
    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    public By skipLogin = By.id("Skip this! Use it without an account.");
    public By loginBtn = By.id("Login");
    public By registerBtn = By.id("Register");


    public LoginView skipLogin() {
        try {
            MyLogger.log.info("Unlock the app to start testing");
            gestures.clickOn(skipLogin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Unlock the app to start testing");
        }
    }

    public LoginView validateElementsFromBeforeLoginPage() {
        try {
            MyLogger.log.info("Unlock the app to start testing");
            waiters.waitForElementVIsibilityIOS(loginBtn);
            assertsUtils.isElementDisplayed(loginBtn);
            assertsUtils.isElementDisplayed(registerBtn);
            assertsUtils.isElementDisplayed(skipLogin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot Unlock the app to start testing");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
