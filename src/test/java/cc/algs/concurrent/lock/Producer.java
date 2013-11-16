
package cc.algs.concurrent.lock;


public class Producer implements Runnable {


    private FileMock mock;
    private Buffer buffer;

    public Producer(FileMock mock, Buffer buffer) {
        this.mock = mock;
        this.buffer = buffer;
    }

    /*
     * 向buffer插入数据，插入前将buffer标记设为真，结束时设为假
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        buffer.setPendingLines(true);
        while (mock.hasMoreLines()) {
            String line = mock.getLine();
            buffer.insert(line);
        }
        buffer.setPendingLines(false);
    }

}