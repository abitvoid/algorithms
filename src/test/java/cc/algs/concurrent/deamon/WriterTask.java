
package cc.algs.concurrent.deamon;

import java.util.Date;
import java.util.Deque;

public class WriterTask implements Runnable {
    private final Deque<Event> deque;

    public WriterTask(Deque<Event> deque) {
        this.deque = deque;
    }

    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            Event event = new Event();
            event.setDate(new Date());
            event.setEvent(String.format("The thread %s has generated an   event", Thread.currentThread().getId()));
            deque.addFirst(event);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
