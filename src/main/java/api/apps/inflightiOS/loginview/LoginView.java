package api.apps.inflightiOS.loginview;

import api.apps.inflightiOS.pincode.PinCode;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class LoginView implements Activity {

    PinCode pinCode = new PinCode();
    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    //elements from Login/Register page
    public By skipLogin = By.id("Skip this! Use it without an account.");
    public By loginBtn = By.id("Login");
    public By registerBtn = By.id("Register");

    //element from actual Login Page - NAVIGATION BAR
    public By backBtn = By.id("Back");
    public By LoginPageTitle = By.id("Login");
    public By infoBtn = By.id("actionIcon");

    //elements related to username and password
    public By usernameLabel = By.id("Username");
    //TODO: we need an id for username input
    public By usernameInput = By.id("");
    public By passwordLabel = By.id("Password");
    //TODO: we need an id for username input
    public By passwordInput = By.id("");
    public By forgotPassBtn = By.id("Forgot password? Click here.");
    public By notRegisterBtn = By.id("Don‘t have an account yet? Register now for one!");


    //click on skip Login
    public LoginView skipLogin() {
        try {
            MyLogger.log.info("Click on Skip Login button");
            gestures.clickOn(skipLogin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Skip Login button");
        }
    }

    //click on Login button to go to Login screen
    public LoginView clickLoginBtn() {
        try {
            MyLogger.log.info("Click on Login button");
            gestures.clickOn(loginBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Login button");
        }
    }

    //click on Register button to go to Register screen
    public LoginView clickRegisterBtn() {
        try {
            MyLogger.log.info("Click on Register button");
            gestures.clickOn(registerBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Register button");
        }
    }

    //click on Back button to exit Login view
    public LoginView clickBackBtn() {
        try {
            MyLogger.log.info("Click on Back button");
            gestures.clickOn(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Back button");
        }
    }

    //send text to username field
    public LoginView sendTextUsername(String element) {
        try {
            MyLogger.log.info("Send text to username input : " + element);
            gestures.sendText(usernameInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to username input : " + element);
        }
    }

    //send text to password filed
    public LoginView sendTextPassword(String element) {
        try {
            MyLogger.log.info("Send text to password input input : " + element);
            gestures.sendText(passwordInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to password input : " + element);
        }
    }

    //validate elements from Login/Register page
    public LoginView validateElementsFromBeforeLoginPage() {
        try {
            MyLogger.log.info("Validate displayed elements from Login/Register page");
            waiters.waitForElementVIsibilityIOS(loginBtn);
            assertsUtils.isElementDisplayed(loginBtn);
            assertsUtils.isElementDisplayed(registerBtn);
            assertsUtils.isElementDisplayed(skipLogin);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate displayed elements from Login/Register page");
        }
    }

    //validate elements from actual Login Page
    public LoginView validateElemLoginPage() {
        try {
            MyLogger.log.info("Validate displayed elements from Login/Register page");
            waiters.waitForElementVIsibilityIOS(loginBtn);
            assertsUtils.isElementDisplayed(usernameLabel);
            assertsUtils.isElementDisplayed(usernameInput);
            assertsUtils.isElementDisplayed(passwordLabel);
            assertsUtils.isElementDisplayed(passwordInput);
            assertsUtils.isElementDisplayed(forgotPassBtn);
            assertsUtils.isElementDisplayed(notRegisterBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot validate displayed elements from Login/Register page");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
