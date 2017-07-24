package tests;

import api.android.Android;
import api.apps.mail.Mail;
import core.managers.TestManager;
import org.junit.Test;

public class Experiment extends TestManager {

    private static Mail mail = Android.app.mail;

    @Test
    public void testNou() {
        mail.loginview.waitToLoad();
        mail.loginview.tapGmail();
        mail.loginview.tapOkBtn();
        mail.loginview.waitForGmailLoginPage();
        mail.loginview.selectExistingGmailAccount();
        mail.loginview.clickOkBtnGmailLogin();


    }
}
