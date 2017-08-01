package tests;

import api.android.Android;
import core.helpers.EmailHelpers;
import core.managers.TestManager;
import org.junit.Before;
import org.junit.Test;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_ENTER;

public class Experiment2Test extends TestManager {

    @Before
    public void login() {
//        mail.loginview.loginWitCorrectCredentials("emma.test1", "1234test");
        mail.loginview.generalLogin("emma.test1@t-online.de", "1234test", 1);
    }

    @Test
    public void testNou2() {
        testInfo.id("test2").name("Send an e-mail and verify if it appears in Inbox").suite("TestPrimer");
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
        mail.folderlist.enterInFolder(mail.folderlistUiObjects.inboxFolder());
        EmailHelpers.waitForEmail(EmailHelpers.Folder.POSTEINGANG, "test");

    }

}
