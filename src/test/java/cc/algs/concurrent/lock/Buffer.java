
package cc.algs.concurrent.lock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private LinkedList<String> buffer; // 共享数据
    private int maxSize; // 缓冲区长度
    private ReentrantLock lock; // 修改缓冲区锁
    private Condition getMonitor; // get方法的监视器
    private Condition insertMonitor;// insert方法的监视器
    private boolean pendingLines; // true，缓冲区有行，不一定是buffer中有数据(有行待插入)

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
        lock = new ReentrantLock();
        getMonitor = lock.newCondition();
        insertMonitor = lock.newCondition();
        pendingLines = true;
    }

    public void insert(String line) {
        lock.lock();
        try {
            while (buffer.size() == maxSize) {
                // buffer满了，insert线程该休息了(挂起，并释放锁)
                insertMonitor.await();
            }
            buffer.offer(line);
            System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread().getName(), buffer.size());
            // 刚向buffer插入了一条数据，get线程该工作了
            getMonitor.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        String line = null;
        lock.lock();
        try {
            while ((buffer.size() == 0) && (hasPendingLines())) {
                // buffer空了(且还有数据处理，仅有前者也可能所有事务都处理完)，get线程该休息了
                getMonitor.await();
            }
            if (hasPendingLines()) {
                line = buffer.poll();
                System.out.printf("%s: Line Readed: %d\n", Thread.currentThread().getName(), buffer.size());
                // 刚取走了一条数据，insert线程该工作了
                insertMonitor.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return line;
    }

    public void setPendingLines(boolean pendingLines) {
        this.pendingLines = pendingLines;
    }

    public boolean hasPendingLines() {
        return pendingLines || buffer.size() > 0;
    }


}
