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

    @Test
    public void insertDataLogin() {
        inflightiOS.pinCode.clickSetupLaterPin();
        inflightiOS.pinCode.clickEnableTouchLater();
        inflightiOS.loginView.clickLoginBtn();
        inflightiOS.loginView.sendTextUsername("emma.cluj@gmx.com");
        inflightiOS.loginView.sendTextPassword("1234test");
        inflightiOS.loginView.clickLoginBtn();
    }

    @Test
    public void verifySettingsOrder() throws InterruptedException {
        inflightiOS.pinCode.clickSetupLaterPin();
        inflightiOS.pinCode.clickEnableTouchLater();
        inflightiOS.loginView.skipLogin();
        inflightiOS.firsttimeview.clickXBtn();
        inflightiOS.dashboard.clickHamburgerMenu();
        inflightiOS.mainMenu.checkSettingsOrder();
    }


}
