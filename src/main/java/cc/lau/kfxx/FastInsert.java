
package cc.lau.kfxx;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Phaser;

public class FastInsert {
    public static void main(String[] args) {
        final Map<Integer, Long> clock = new ConcurrentHashMap<>();
        int threadCount = Constants.THREAD_COUNT;

        Phaser phaser = new Phaser(threadCount) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                clock.put(phase + 1, System.currentTimeMillis());
                int duration = (int) (clock.get(phase + 1) - clock.get(phase)) / 1000;
                System.out.println("第 " + phase + " 轮插入结束，耗时 " + duration + " 秒");
                return registeredParties == 1;
            }
        };

        CSVWrapper cw = CSVWrapper.newInstance(Constants.Linux.totalDir);

        System.out.println("程序即将开始，" + threadCount + " 线程并行");

        clock.put(0, System.currentTimeMillis());
        for (int i = 0; i < threadCount; i++) {
            new InsertThread(phaser, cw).start();
        }

        phaser.register(); // 将主线程动态增加到phaser中，此句执行后phaser共管理4个线程
        while (!phaser.isTerminated()) {// 只要phaser不终结，主线程就循环等待
            int n = phaser.arriveAndAwaitAdvance();
        }

        System.out.println("read: " + cw.getCount());
        System.out.println("error: " + cw.getErrorCount());
        System.out.println("总用时：" + (System.currentTimeMillis() - clock.get(0)) / (1000 * 60) + " 分钟");
        // 跳出上面循环后，意味着phaser终结，即3个工作线程已经结束
        System.out.println("程序结束");
    }
}

class InsertThread extends Thread {

    private static MongoClient client = MongoWrapper.getInstance().getClient();
    private static DB db = client.getDB("kfxx");
    private static DBCollection users = db.getCollection("users");

    private Phaser phaser;
    private CSVWrapper cw;

    public InsertThread(Phaser phaser, CSVWrapper cw) {
        this.phaser = phaser;
        this.cw = cw;
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            List<DBObject> mobjs;
            if ((mobjs = cw.getFixNumberMobjs(Constants.DEFAULT_INSERT_LIMIT)).size() > 0) {
                insert(mobjs);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
                break;
            }
        }
    }

    private synchronized long insert(List<DBObject> mobjs) {
        return users.insert(mobjs).getN();
    }

    // private void doSleep() {
    // try {
    // long duration = (long) (Math.random());
    // TimeUnit.SECONDS.sleep(duration);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }

}
