
package cc.algs.event;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 事件源，好比一个按钮
 * 
 * @author lau
 */
public class ButtonSource {
    private Vector repository = new Vector();
    ButtonListener listener;

    public ButtonSource() {

    }

    public void addListener(ButtonListener dl) {
        repository.addElement(dl);
    }

    public void click(String type) {
        Enumeration elements = repository.elements();
        type = type.trim().toLowerCase();
        Class clz = null;
        if (type.equals("l") || type.equals("left")) {
            clz = LeftClickListener.class;
        } else if (type.equals("r") || type.equals("right")) {
            clz = RightClickListener.class;
        } else if (type.equals("d") || type.equals("double")) {
            clz = DoubleClickListener.class;
        }
        
        if (clz == null)
            return;
        
        while (elements.hasMoreElements()) {
            listener = (ButtonListener) elements.nextElement();
            if (clz == listener.getClass()) {
                listener.doEvent(new ButtonEvent(this));
                break;
            }
        }
    }

}
