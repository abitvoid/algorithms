package cc.algs.event;

import java.util.EventObject;

public class ButtonEvent extends EventObject {
    Object obj;

    /**
     * 事件，与按钮相关
     * 
     * @param source
     */
    public ButtonEvent(Object source) {
        super(source);
        obj = source;

    }

    public Object getSource() {
        return obj;
    }

    public void say()
    {  
        System.out.println("This is say method...");
    }

}
