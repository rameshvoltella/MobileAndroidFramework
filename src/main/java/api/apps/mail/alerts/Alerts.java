package api.apps.mail.alerts;

import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

public class Alerts implements Activity {

    public AlertsUiObjects alertsUiObjects = new AlertsUiObjects();

    public Alerts tapAllowAccessContacts() {
        try {
            MyLogger.log.info("Tapping on allow to accept contacts");
            alertsUiObjects.allowAccessContacts().waitToAppear(30);
            alertsUiObjects.allowAccessContacts().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on allow to access contacts");
        }
    }

    public Alerts tapDennyAccessContacts() {
        try {
            MyLogger.log.info("Tapping on Denny to denny contacts");
            alertsUiObjects.dennyAccessContacts().waitToAppear(10);
            alertsUiObjects.dennyAccessContacts().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on denny to access contacts");
        }
    }

    public Alerts clickOkAdsDisclaimerInbox() {
        try {
            MyLogger.log.info("Tapping Ok on Ads disclaimer in Inbox");
            alertsUiObjects.okAdsDisclaimerInbox().waitToAppear(15);
            alertsUiObjects.okAdsDisclaimerInbox().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on ok to dismiss Ads disclaimer in Inbox");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
