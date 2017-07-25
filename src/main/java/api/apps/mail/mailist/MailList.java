package api.apps.mail.mailist;

import api.interfaces.Activity;
import core.MyLogger;
import org.openqa.selenium.NoSuchElementException;

public class MailList implements Activity {

    public MailListUiObjects mailListUiObjects = new MailListUiObjects();

    public MailList tapComposeBtnMailList() {
        try {
            MyLogger.log.info("Tapping on allow to accept contacts");
            mailListUiObjects.composeBtn().waitToAppear(10);
            mailListUiObjects.composeBtn().tap();
            return this;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Cannot tap on allow to access contacts");
        }
    }

    @Override
    public Object waitToLoad() {
        return null;
    }
}
