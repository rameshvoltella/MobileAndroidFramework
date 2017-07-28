package tests;

import api.android.Android;
import core.helpers.EmailHelpers;
import core.managers.TestManager;
import org.junit.Test;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_ENTER;

public class ExperimentTest extends TestManager {


    @Test
    public void testNou() {
        testInfo.id("test1").name("Send an e-mail and verify if it appears in Inbox").suite("TestPrimer");
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
        mail.folderlist.findTrustedDialogFolder();
        mail.folderlist.findInboxFolder();
        mail.folderlist.enterInboxFolder();
        EmailHelpers.waitForEmail(EmailHelpers.Folder.POSTEINGANG, "test");
//        mail.loginview.tapGmail();
//        mail.loginview.tapOkBtn();
//        mail.loginview.waitForGmailLoginPage();
//        mail.loginview.selectExistingGmailAccount();
//        mail.loginview.clickOkBtnGmailLogin();
    }

}
