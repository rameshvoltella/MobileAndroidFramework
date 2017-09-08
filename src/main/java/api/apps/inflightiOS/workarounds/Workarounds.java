package api.apps.inflightiOS.workarounds;

import api.apps.inflightiOS.pincode.PinCode;
import api.interfaces.Activity;
import core.MyLogger;
import core.classicmethods.Gestures;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

public class Workarounds implements Activity{

    PinCode pinCode = new PinCode();
    Gestures gestures = new Gestures();

    public By skipLogin = By.id("Skip this! Use it without an account.");


    public Workarounds skipLoginmethod() {
        try {
            MyLogger.log.info("Unlock the app to start testing");
            pinCode.unlockWithPinCode(1,1,1,1,1,1);
            pinCode.clickSetupLaterPin();
            pinCode.clickEnableTouchLater();
            gestures.clickOn(skipLogin);
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
