package tests;

import api.android.Android;
import api.apps.mail.Mail;
import core.helpers.EmailHelpers;
import core.managers.TestManager;
import org.junit.Test;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_ENTER;

public class Experiment2 extends TestManager {

    private static Mail mail = Android.app.mail;

    @Test
    public void testNou2() {
        mail.loginview.waitForAccountPicker();
        mail.loginview.tapOkBtn();
        mail.loginview.sendTextToUsername("emma.test1");
        mail.loginview.sendTextToPassword("1234test");
        mail.loginview.clickLoginButton();
        mail.alerts.tapAllowAccessContacts();
        mail.alerts.tapAllowAccessContacts();
        mail.alerts.clickOkAdsDisclaimerInbox();
        mail.mailList.tapComposeBtnMailList();
        mail.composer.sendKeysToAn("emma.test1@t-online.de");
        Android.driver.pressKeyCode(KEYCODE_ENTER);
        mail.composer.dimissemig();
        mail.composer.sendKeysToSubject("test");
        mail.composer.sendKeysToBody("this is a test body");
        mail.composer.clickSendButton();
        mail.mailList.tapBackBtnMailList();
        mail.folderlist.enterInboxFolder();
        EmailHelpers.waitForEmail(EmailHelpers.Folder.POSTEINGANG, "test");

    }

}
