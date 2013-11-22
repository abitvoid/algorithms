
package cc.algs.event;

import org.junit.Test;

public class ClickButtonTest {
    ButtonSource button;

    @Test
    public void testClickButton() {
        button = new ButtonSource();
        ButtonListener lc = new LeftClickListener();
        ButtonListener rc = new RightClickListener();
        ButtonListener dc = new DoubleClickListener();

        button.addListener(lc);
        button.addListener(rc);
        button.addListener(dc);

        button.click("l");
        button.click("Right");
    }

}
