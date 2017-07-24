package api.apps.mail.loginview;

import api.android.Android;
import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

public class Loginview implements Activity {

    public LoginviewUiObjects loginviewUiObjects = new LoginviewUiObjects();

    public Loginview tapTonline() {
        try {
            MyLogger.log.info("Tapping tonline option");
            loginviewUiObjects.tonlineLogin().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on Tonline Button, element absent or blocked");
        }
    }

    public Loginview tapGmail() {
        try {
            MyLogger.log.info("Tapping gmail option");
            loginviewUiObjects.gmailLogin().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on Gmail Button, element absent or blocked");
        }
    }

    public Loginview tapOkBtn() {
        try {
            MyLogger.log.info("Tapping Ok Btn");
            loginviewUiObjects.okBtnAccountPicker().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on OK Button, element absent or blocked");
        }
    }

    public Loginview selectExistingGmailAccount() {
        try {
            MyLogger.log.info("Tapping Ok Btn");
            loginviewUiObjects.exitingaccount().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on OK Button, element absent or blocked");
        }
    }

    public Loginview waitForGmailLoginPage() {
        try {
            MyLogger.log.info("wait for Gmail Login page");
            loginviewUiObjects.gmailloginheaderpage().waitToAppear(20);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on OK Button, element absent or blocked");
        }
    }

    public Loginview clickOkBtnGmailLogin() {
        try {
            MyLogger.log.info("wait for Gmail Login page");
            loginviewUiObjects.okBtnSelectGmail().waitToAppear(20);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on OK Button, element absent or blocked");
        }
    }

    @Override
    public Object waitToLoad() {
        try {
            MyLogger.log.info("Waiting for account picker activity");
            loginviewUiObjects.accountpickertitle().waitToAppear(20);
            return Android.app.mail.loginview;
        } catch (AssertionError e) {
            throw new AssertionError("About activity failed to load/open");
        }
    }

}
