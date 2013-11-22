package cc.algs.event;

public class DoubleClickListener implements ButtonListener {

    @Override
    public void doEvent(ButtonEvent de) {
        System.out.println("双击了按钮，确定...");
    }
}
