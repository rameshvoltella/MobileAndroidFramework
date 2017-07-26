package api.apps.mail.composer;

import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

public class Composer implements Activity {

    public ComposerUiObjects composerUiObjects = new ComposerUiObjects();

    public Composer sendKeysToAn(String address) {
        try {
            MyLogger.log.info("Sending keys to An field");
            composerUiObjects.anField().waitToAppear(10);
            composerUiObjects.anField().tap().typeText(address);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot keys to AN field");
        }
    }

    public Composer sendKeysToSubject(String subject) {
        try {
            MyLogger.log.info("Sending keys to Subject field");
            composerUiObjects.subjectField().waitToAppear(10);
            composerUiObjects.subjectField().tap().typeText(subject);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot keys to Subject field");
        }
    }

    public Composer sendKeysToBody(String text) {
        try {
            MyLogger.log.info("Sending keys to Body field");
            composerUiObjects.bodyField().waitToAppear(10);
            composerUiObjects.bodyField().tap().typeText(text);
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot keys to Body field");
        }
    }

    public Composer clickSendButton() {
        try {
            MyLogger.log.info("Click Send button from composer");
            composerUiObjects.sendButton().waitToAppear(2);
            composerUiObjects.sendButton().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot click on Send button in Composer");
        }
    }

    public Composer dimissemig() {
        try {
            composerUiObjects.emigPopUpPageTile().waitToAppear(3);
            if (composerUiObjects.emigPopUpPageTile().exists()) {
                MyLogger.log.info("Dismiss emig popup");
                composerUiObjects.okBtnEmigPopUp().tap();
                return this;
            }
        } catch (NoSuchElementException e) {
            //do nothing
            MyLogger.log.info("Emig was already dismissed");
        }
        return this;
    }


    @Override
    public Object waitToLoad() {
        return null;
    }
}
