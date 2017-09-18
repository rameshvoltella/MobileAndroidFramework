package tests;

import core.managers.TestManager;
import org.junit.Before;
import org.junit.Test;

public class TestSet_03_MainMenu extends TestManager {

    @Before
    public void goToMainMenu() {
        inflightiOS.workarounds.goDirectlyToMainMenu();
    }


    @Test
    public void validateMainMenuListAndOrder() throws InterruptedException {
        inflightiOS.mainMenu.validateXBtnDisplayed();
        inflightiOS.mainMenu.validateLeftMenuelementsDisplayed();
        inflightiOS.mainMenu.validatelegalTermsDisplayed();
        inflightiOS.mainMenu.checkSettingsOrder();
    }

    @Test
    public void closeOpenMainMenuList() {
        inflightiOS.mainMenu.validateXBtnDisplayed();
        inflightiOS.mainMenu.clickXBtn();
        inflightiOS.dashboard.clickHamburgerMenu();
        inflightiOS.mainMenu.validateLeftMenuelementsDisplayed();
    }

    @Test
    public void enterSubcategoryAndReturnToMainMenu() {
        inflightiOS.mainMenu.clickHelp();
        inflightiOS.helpView.validateFTWfromHelp("page 1 of 4");
        inflightiOS.helpView.clickBackToExitHelp();
        inflightiOS.mainMenu.validateLeftMenuelementsDisplayed();
    }

    @Test
    public void openMainMenuWithSwipeToRight() {
        inflightiOS.mainMenu.clickXBtn();
        inflightiOS.mainMenu.openLeftMenuWithSwipe();
        inflightiOS.mainMenu.validateLeftMenuelementsDisplayed();
        inflightiOS.mainMenu.clickXBtn();
        inflightiOS.mainMenu.openLeftMenuWithSwipe();
    }

    @Test
    public void enterAndVerifyTermsLegal() {
        inflightiOS.mainMenu.clickLegalTerms();
        inflightiOS.mainMenu.validatelegalTermsPageElements();
        inflightiOS.mainMenu.clickXBtn();
        inflightiOS.mainMenu.clickLegalTerms();
        inflightiOS.mainMenu.validatelegalTermsPageElements();

    }
}
