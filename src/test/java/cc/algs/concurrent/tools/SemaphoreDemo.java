
package cc.algs.concurrent.tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//8.   创建一个名为Job的类并一定实现Runnable 接口。这个类实现把文档传送到打印机的任务。
class Job implements Runnable {

    // 9. 声明一个对象为PrintQueue，名为printQueue。
    private PrintQueue printQueue;

    // 10. 实现类的构造函数，初始化这个类里的PrintQueue对象。
    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    // 11. 实现方法run()。
    @Override
    public void run() {
        // 12. 首先， 此方法写信息到操控台表明任务已经开始执行了。
        System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
        // 13. 然后，调用PrintQueue 对象的printJob()方法。
        printQueue.printJob(new Object());
        // 14. 最后， 此方法写信息到操控台表明它已经结束运行了。
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }
}

class PrintQueue {
    // 记录3台打印机的状态，true 表示空闲
    private boolean freePrinters[];
    private Lock lockPrinters;

    private final Semaphore semaphore;

    public PrintQueue() {
        semaphore = new Semaphore(3);
        freePrinters = new boolean[3];

        for (int i = 0; i < 3; i++) {
            freePrinters[i] = true;
        }
        lockPrinters = new ReentrantLock();
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            int assignedPrinter = getPrinter();

            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer%d during %d seconds\n", Thread.currentThread().getName(),
                assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration);
            Thread.sleep(duration);
            freePrinters[assignedPrinter] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int getPrinter() {
        int ret = -1;

        // 11. 然后， 获得lockPrinters对象 object的访问。
        try {
            lockPrinters.lock();

            // 12. 然后，在freePrinters array内找到第一个真值并在一个变量中保存这个引索值。修改值为false，因为等会这个打印机就会被使用。
            for (int i = 0; i < freePrinters.length; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }

}

//15. 实现例子的main类，创建名为 Main的类并实现main()方法。
public class SemaphoreDemo {
    public static void main(String args[]) {

        // 16. 创建PrintQueue对象名为printQueue。
        PrintQueue printQueue = new PrintQueue();

        // 17. 创建10个threads。每个线程会执行一个发送文档到print queue的Job对象。
        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread" + i);
        }

        // 18. 最后，开始这10个线程们。
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}
