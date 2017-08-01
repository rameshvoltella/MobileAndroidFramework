package tests;

import core.helpers.EmailHelpers;
import core.managers.TestManager;
import org.junit.Before;
import org.junit.Test;

import static core.helpers.EmailHelpers.waitForEmail;

public class ExperimentTest extends TestManager {


    @Before
    public void login() {
        mail.loginview.generalLogin("emma.test1@t-online.de", "1234test", 1);
    }

    @Test
    public void testNou() {
        mail.alerts.tapAllowAccessContacts();
        mail.alerts.tapAllowAccessContacts();
        mail.alerts.clickOkAdsDisclaimerInbox();
        mail.mailList.tapComposeBtnMailList();
        mail.composer.sendKeysToAn("emma.test1@t-online.de");
        mail.composer.dimissemig();
        mail.composer.sendKeysToSubject("test");
        mail.composer.sendKeysToBody("this is a test body");
        mail.composer.clickSendButton();
        mail.mailList.tapBackBtnMailList();
        mail.folderlist.findFolderDown(mail.folderlistUiObjects.trustedDialogFolder());
        mail.folderlist.enterInFolder(mail.folderlistUiObjects.trustedDialogFolder());
        mail.mailList.tapBackBtnMailList();
        mail.folderlist.findFolderUp(mail.folderlistUiObjects.inboxFolder());
        mail.folderlist.enterInFolder(mail.folderlistUiObjects.inboxFolder());
        waitForEmail(EmailHelpers.Folder.POSTEINGANG, "test");
//        mail.loginview.tapGmail();
//        mail.loginview.tapOkBtn();
//        mail.loginview.waitForGmailLoginPage();
//        mail.loginview.selectExistingGmailAccount();
//        mail.loginview.clickOkBtnGmailLogin();
    }

}
