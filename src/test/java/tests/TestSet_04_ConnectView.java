package tests;

import core.managers.TestManager;
import org.junit.Before;
import org.junit.Test;

public class TestSet_04_ConnectView extends TestManager {

    @Before
    public void goToMainMenu() {
        inflightiOS.workarounds.goDirectlyToConnect();
    }

    @Test
    public void verifyElementsFromConnect() {
        inflightiOS.connectView.validateConnectPageTileAndBar();
        inflightiOS.connectView.validateElementsFromDescriptionArea();
        inflightiOS.connectView.validateSocialPackage();
        inflightiOS.connectView.validateSurfPackage();
        inflightiOS.connectView.validateStreamPackage();
        inflightiOS.connectView.validateTelekomPartnerLoginDisplayedInConnectPage();
    }

    @Test
    public void buySocialPackage() {
        inflightiOS.connectView.clickBuyNowSocial();
        inflightiOS.connectView.validateSocialPurchaseConfirmation();
        inflightiOS.connectView.clickBuyNow();
        inflightiOS.dashboard.validateUpgradeInBottomBar();
    }


}
