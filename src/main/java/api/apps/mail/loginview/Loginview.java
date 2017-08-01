package api.apps.mail.loginview;

import api.android.Android;
import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import static core.managers.TestManager.mail;

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

    public Loginview sendTextToUsername(String text) {
        try {
            MyLogger.log.info("wait for username filed");
            loginviewUiObjects.userNameField().waitToAppear(20);
            loginviewUiObjects.userNameField().tap().typeText(text);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to username");
        }
    }

    public Loginview sendTextToUsernameGmail(String username) {
        try {
            MyLogger.log.info("wait for username filed");
            loginviewUiObjects.gmailUsernameInput().waitToAppear(20);
            loginviewUiObjects.gmailUsernameInput().tap().typeText(username);
            loginviewUiObjects.gmailLoginUsernameNextButton().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to username");
        }
    }

    public Loginview sendTextToPassword(String text) {
        try {
            MyLogger.log.info("wait for password field");
            loginviewUiObjects.loginPasswordField().waitToAppear(20);
            loginviewUiObjects.loginPasswordField().tap().typeText(text);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to password");
        }
    }

    public Loginview sendTextToPasswordGmail(String text) {
        try {
            MyLogger.log.info("wait for password field");
            loginviewUiObjects.gmailPasswordInput().waitToAppear(20);
            loginviewUiObjects.gmailPasswordInput().tap().typeText(text);
            loginviewUiObjects.gmailLoginPasswordNextButton().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot send text to password");
        }
    }


    public Loginview clickLoginButton() {
        try {
            MyLogger.log.info("wait for Login button to appear");
            loginviewUiObjects.loginButton().waitToAppear(20);
            loginviewUiObjects.loginButton().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click login button");
        }
    }

    public Object waitForAccountPicker() {
        try {
            MyLogger.log.info("Waiting for account picker activity");
            loginviewUiObjects.accountpickertitle().waitToAppear(10);
            return Android.app.mail.loginview;
        } catch (AssertionError e) {
            throw new AssertionError("About activity failed to load/open");
        }
    }


    public Object generalLogin(String username, String password, int index) {
        try {
            waitForAccountPicker();

            if (username.contains("t-online")) {
                tapOkBtn();
                sendTextToUsername(username);
                sendTextToPassword(password);
                clickLoginButton();
            } else if (username.contains("gmail")) {
                tapGmail();
                tapOkBtn();
                try {
                    mail.alerts.tapAllowAccessContacts();
                } catch (AssertionError e1) {
                    //do nothing
                }

                try {
                    loginviewUiObjects.chooseGmailAccount().waitToAppear(20);
                    if (loginviewUiObjects.selectAlreadyLoggedInGmailAccount(index).getText().equals(username)) {
                        loginviewUiObjects.selectAlreadyLoggedInGmailAccount(index).waitToAppear(10).tap();
                        loginviewUiObjects.okBtnSelectGmail().waitToAppear(10).tap();
                    } else {
                        loginviewUiObjects.addNewGmailAccount().waitToAppear(10).tap();
                        loginviewUiObjects.okBtnSelectGmail().waitToAppear(10).tap();
                        loginviewUiObjects.signInGmailHeader().waitToAppear(20);
                        sendTextToUsernameGmail(username);
                        loginviewUiObjects.gmailAccountProfileIdentifier().waitToAppear(20);
                        sendTextToPasswordGmail(password);
                        loginviewUiObjects.singInAgreeGmail().waitToAppear(20).tap();
                        try {
                            //this is displayed only when you`re login for the 1st time with a Gmail...
                            loginviewUiObjects.allowGmailnBackup().waitToAppear(20).tap();
                        } catch (AssertionError e) {
                            //do nothing
                        }
                        loginviewUiObjects.allowGmailNotification().waitToAppear(10).tap();
                    }
                } catch (AssertionError e) {
                    loginviewUiObjects.signInGmailHeader().waitToAppear(20);
                    sendTextToUsernameGmail(username);
                    loginviewUiObjects.gmailAccountProfileIdentifier().waitToAppear(20);
                    sendTextToPasswordGmail(password);
                    loginviewUiObjects.singInAgreeGmail().waitToAppear(20).tap();
                    loginviewUiObjects.allowGmailnBackup().waitToAppear(20).tap();
                    loginviewUiObjects.allowGmailNotification().waitToAppear(20).tap();
                }
            } else {
//            click(accountTypePickerView.THIRD_PARTY_LOGIN);
//            click(accountTypePickerView.OK_BTN);
//            login3rdPartyAccount(cred);
            }
            return this;
        } catch (WebDriverException e) {
            throw new AssertionError("Cannot login with give credentials" + username + password + e.getMessage());
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }

}
