package tests;

import core.managers.TestManager;
import org.junit.Test;

public class TestSet_03_Register_Flow extends TestManager {

    @Test
    public void insertPinInRegister() {
        inflightiOS.pinCode.clickSetupLaterPin();
        inflightiOS.pinCode.clickEnableTouchLater();
        inflightiOS.loginView.clickRegisterBtn();
        inflightiOS.registerView.sendTextPinCode(1);
    }


}
