package api.apps.inflightiOS.registerview;

import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.AssertsUtils;
import core.classicmethods.Gestures;
import core.classicmethods.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class RegisterView implements Activity {

    Gestures gestures = new Gestures();
    Waiters waiters = new Waiters();
    AssertsUtils assertsUtils = new AssertsUtils();

    //elements from navigation bar Register page
    public By backBtn = By.id("Back");
    public By registerPageTitle = By.id("Register");
    public By infoBtn = By.id("actionIcon");

    //elements related to user data
    public By firstNameLabel = By.id("First Name");
    //TODO: we need an id for First Name input
    public By firstNameInput = By.id("");
    public By lastNameLabel = By.id("Last Name");
    //TODO: we need an id for Last Name input
    public By lastNameInput = By.id("");
    public By emailLabel = By.id("Email");
    //TODO: id for email input
    public By emailInput = By.id("");
    public By setPinCodeLabel = By.id("PIN Code");
    //TODO: id for pin code setup
    public By setPinCode = By.id("");
    public By alreadyHaveAccountBtn = By.id("Already have an account? Login now!");
    public By registerBtn = By.id("Register");

    //click on Register button
    public RegisterView clickRegisterBtn() {
        try {
            MyLogger.log.info("Click on Register button");
            gestures.clickOn(registerBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Register button");
        }
    }

    //click on Back button to exit Register view
    public RegisterView clickBackBtn() {
        try {
            MyLogger.log.info("Click on Back button");
            gestures.clickOn(backBtn);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Back button");
        }
    }

    //send text to first name field
    public RegisterView sendTextFirstName(String element) {
        try {
            MyLogger.log.info("Send text to first name input : " + element);
            gestures.sendText(firstNameInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to first name input : " + element);
        }
    }

    //send text to first name field
    public RegisterView sendTextLastName(String element) {
        try {
            MyLogger.log.info("Send text to last name input : " + element);
            gestures.sendText(lastNameInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to last name input : " + element);
        }
    }

    //send text to first email field
    public RegisterView sendTextEmail(String element) {
        try {
            MyLogger.log.info("Send text to email input : " + element);
            gestures.sendText(emailInput, element);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to email input : " + element);
        }
    }

    //send text to Pin Code field
    public RegisterView sendTextPinCode(int number) {
        try {
            int i = 0;
            while (i <= 6) {
                String pinNumber = Integer.toString(number);
                MyLogger.log.info("Send text to email input : " + pinNumber);
                gestures.sendText(setPinCode, pinNumber);
                i++;
            }
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to email input : " + number);
        }
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
