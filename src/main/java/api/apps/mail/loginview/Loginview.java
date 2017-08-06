package api.apps.mail.loginview;

import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

import static core.managers.TestManager.mail;

public class Loginview implements Activity {

    public LoginviewUiObjects loginviewUiObjects = new LoginviewUiObjects();


    public Object generalLogin(String username, String password, int index) {
        try {
            loginviewUiObjects.accountpickertitle().waitToAppear(15);
            if (username.contains("t-online")) {
                loginTelekomAccount(username, password);
            } else if (username.contains("gmail")) {
                selectGmailInAccountPicker();
                try {
                    loginviewUiObjects.chooseGmailAccount().waitToAppear(20);
                    if (loginviewUiObjects.selectAlreadyLoggedInGmailAccount(index).getText().equals(username)) {
                        selectLoggedinGmailAccout(index);
                    } else {
                        addOtherGmailAccount(username, password);
                    }
                } catch (AssertionError e) {
                    addFirstGmailAccount(username, password);
                }
            } else {
                login3rdAccount(username, password);

            }
            return this;
        } catch (AssertionError e) {
            throw new AssertionError("Cannot login with give credentials" + username + password + e.getMessage());
        }
    }

    public Loginview addOtherGmailAccount(String username, String password) {
        try {
            MyLogger.log.info("Add another Gmail account from login screen");
            loginviewUiObjects.addNewGmailAccount().waitToAppear(10).tap();
            loginviewUiObjects.okBtnSelectGmail().waitToAppear(15).tap();
            loginviewUiObjects.signInGmailHeader().waitToAppear(25);
            MyLogger.log.info("Insert username new Gmail account: " + username);
            loginviewUiObjects.gmailUsernameInput().waitToAppear(20).typeText(username);
            loginviewUiObjects.gmailLoginUsernameNextButton().tap();
            loginviewUiObjects.gmailAccountProfileIdentifier().waitToAppear(20);
            MyLogger.log.info("Insert password for new Gmail account: " + password);
            loginviewUiObjects.gmailPasswordInput().waitToAppear(20).typeText(password);
            loginviewUiObjects.gmailLoginPasswordNextButton().tap();
            loginviewUiObjects.singInAgreeGmail().waitToAppear(20).tap();
            try {
                //this is displayed only when you`re login for the 1st time with a Gmail...
                loginviewUiObjects.allowGmailnBackup().waitToAppear(20).tap();
            } catch (AssertionError e) {
                //do nothing
            }
            loginviewUiObjects.allowGmailNotification().waitToAppear(20).tap();
            MyLogger.log.info("New Gmail account was added and we are in Inbox: " + username);
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.clickOkAdsDisclaimerInbox();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot add another Gmail Account from login page" + username);
        }
    }

    public Loginview addFirstGmailAccount(String username, String password) {
        try {
            MyLogger.log.info("Add first Gmail account from login screen");
            loginviewUiObjects.signInGmailHeader().waitToAppear(10);
            MyLogger.log.info("Add username for first Gmail account from login screen: " + username);
            loginviewUiObjects.gmailUsernameInput().waitToAppear(20).typeText(username);
            loginviewUiObjects.gmailLoginUsernameNextButton().tap();
            loginviewUiObjects.gmailAccountProfileIdentifier().waitToAppear(10);
            MyLogger.log.info("Add password for first Gmail account from login screen: " + password);
            loginviewUiObjects.gmailPasswordInput().waitToAppear(20).typeText(password);
            loginviewUiObjects.gmailLoginPasswordNextButton().tap();
            loginviewUiObjects.singInAgreeGmail().waitToAppear(20).tap();
            loginviewUiObjects.allowGmailnBackup().waitToAppear(20).tap();
            loginviewUiObjects.allowGmailNotification().waitToAppear(20).tap();
            MyLogger.log.info("New Gmail account was added and we are in Inbox: " + username);
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.clickOkAdsDisclaimerInbox();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    public Loginview loginTelekomAccount(String username, String password) {
        try {
            MyLogger.log.info("Add Telekom account from login screen");
            loginviewUiObjects.tonlineLogin().waitToAppear(3).tap();
            loginviewUiObjects.okBtnAccountPicker().waitToAppear(3).tap();
            MyLogger.log.info("Add username for Telekom account from login screen: " + username);
            loginviewUiObjects.userNameField().waitToAppear(20).typeText(username);
            MyLogger.log.info("Add password for Telekom account from login screen: " + password);
            loginviewUiObjects.loginPasswordField().waitToAppear(20).typeText(password);
            loginviewUiObjects.loginButton().waitToAppear(20).tap();
            MyLogger.log.info("New Telekom account was added and we are in Inbox: " + username);
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.clickOkAdsDisclaimerInbox();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    public Loginview login3rdAccount(String username, String password) {
        try {
            MyLogger.log.info("Add 3rd Party account from login screen");
            loginviewUiObjects.thirdPartyAccount().waitToAppear(10).tap();
            loginviewUiObjects.okBtnAccountPicker().tap();
            MyLogger.log.info("Add username for 3rd Party account from login screen: " + username);
            loginviewUiObjects.userNameField3rdParty().waitToAppear(3).tap().typeText(username);
            MyLogger.log.info("Add password for 3rd Party account from login screen: " + password);
            loginviewUiObjects.loginPasswordField3rdParty().waitToAppear(3).tap().typeText(password);
            loginviewUiObjects.loginButton3rdParty().waitToAppear(3).tap();
            MyLogger.log.info("New 3rd Party account was added and we are in Inbox: " + username);
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.clickOkAdsDisclaimerInbox();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    public Loginview selectGmailInAccountPicker() {
        try {
            MyLogger.log.info("Select Gmail option from account picker");
            loginviewUiObjects.gmailLogin().waitToAppear(2).tap();
            loginviewUiObjects.okBtnAccountPicker().waitToAppear(2).tap();
            try {
                mail.alerts.tapAllowAccessContacts();
            } catch (AssertionError e1) {
                //do nothing
            }
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    public Loginview selectLoggedinGmailAccout(int index) {
        try {
            MyLogger.log.info("Select already loggedin Gmail account from account picker");
            loginviewUiObjects.selectAlreadyLoggedInGmailAccount(index).waitToAppear(10).tap();
            loginviewUiObjects.okBtnSelectGmail().waitToAppear(10).tap();
            MyLogger.log.info("Selecting of already logged in account worked and we are in Inbox");
            mail.alerts.tapAllowAccessContacts();
            mail.alerts.clickOkAdsDisclaimerInbox();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }

}
