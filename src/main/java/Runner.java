import core.UiSelector;

/**
 * Created by lumihai on 6/29/2017.
 */
public class Runner {

    public static void main(String[] args) {
        new UiSelector().resourceId("hello").text("item1").makeUiObject();
    }
}
