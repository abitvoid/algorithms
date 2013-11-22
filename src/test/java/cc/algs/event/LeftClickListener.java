package cc.algs.event;

public class LeftClickListener implements ButtonListener {

    @Override
    public void doEvent(ButtonEvent de) {
        System.out.println("左击了按钮，显示详情...");
    }
}
