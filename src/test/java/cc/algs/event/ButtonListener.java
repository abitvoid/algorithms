package cc.algs.event;

import java.util.EventListener;

public interface ButtonListener extends EventListener {

    /**
     * 监视事件，比如按钮的事件 (左键/右键/双击)
     * 
     * @param dm
     */
    public void doEvent(ButtonEvent dm);

}
