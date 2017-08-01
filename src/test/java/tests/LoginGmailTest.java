package tests;

import core.managers.TestManager;
import org.junit.Before;
import org.junit.Test;

public class LoginGmailTest extends TestManager {

    @Before
    public void login() {
//        mail.loginview.generalLogin("testingbriefcase@gmail.com", "testingbriefcase");
        mail.loginview.generalLogin("clujtestingcommunity@gmail.com", "oscar200", 1);
    }

    @Test
    public void sendGmailTest() {
        mail.alerts.tapAllowAccessContacts();
        mail.alerts.clickOkAdsDisclaimerInbox();
//        mail.mailList.tapComposeBtnMailList();
//        mail.composer.sendKeysToAn("testingbriefcase@gmail.com");
//        mail.composer.dimissemig();
//        mail.composer.sendKeysToSubject("test");
//        mail.composer.sendKeysToBody("this is a test body");
//        mail.composer.clickSendButton();
//        mail.mailList.tapBackBtnMailList();
//        mail.folderlist.findFolderUp(mail.folderlistUiObjects.inboxFolder());
//        mail.folderlist.enterInFolder(mail.folderlistUiObjects.inboxFolder());
//        waitForEmail(EmailHelpers.Folder.POSTEINGANG, "test");
    }


}
