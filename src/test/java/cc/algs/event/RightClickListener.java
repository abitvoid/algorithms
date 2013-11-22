package cc.algs.event;

public class RightClickListener implements ButtonListener {

    @Override
    public void doEvent(ButtonEvent de) {
        System.out.println("右击了按钮，弹出选项...");
    }
}
