package tests;

import api.android.Android;
import core.managers.DriverManagerIOS;
import core.managers.TestManager;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

public class TestSet_02_Pin_TouchID extends TestManager {

    @Test
    public void unlockAppWithPin() {
//        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.unlockWithPinCode2();
    }

    @Test
    public void createNewPin() {
        testInfo.id("test1 from TestSet_02_Pin_TouchID").name("createNewPin").suite("GeneralTests");

        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validateCreatePinCodePageElements();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validateCreatePinCodeVerifyPageElements();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validateEnableTouchIdPageElements();
        inflightiOS.pinCode.clickEnableTouchLater();
    }

    @Test
    public void changePin() {
        testInfo.id("test2 from TestSet_02_Pin_TouchID").name("changePin").suite("GeneralTests");

        inflightiOS.workarounds.skipLoginmethod();
        inflightiOS.firsttimeview.clickXBtn();
        inflightiOS.mainMenu.clickBurgerMenu();
        inflightiOS.mainMenu.clickSettings();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validatePinCodeOptions();
        inflightiOS.pinCode.clickChangePin();
        inflightiOS.pinCode.validateElementsFromChangePinPage();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validateElementsFromChangePinPageNewPin();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.pinCode.validatePinCodeOptions();
    }

    @Test
    public void pinDisplayedAfterAppRestart() throws IOException, ParseException {
        testInfo.id("test3 from TestSet_02_Pin_TouchID").name("pinDisplayedAfterAppRestart").suite("GeneralTests");

        inflightiOS.workarounds.skipLoginmethod();
        inflightiOS.firsttimeview.clickXBtn();

        DriverManagerIOS.createiOSDriverNoReset();
        Android.driverIos.closeApp();
        Android.driverIos.launchApp();
        inflightiOS.pinCode.unlockWithPinCode(1, 1, 1, 1, 1, 1);
        inflightiOS.mainMenu.ValidateHamburgerMenuIsDisplayed();
        inflightiOS.mainMenu.clickBurgerMenu();
    }
}
