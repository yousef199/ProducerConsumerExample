import java.util.concurrent.BlockingQueue;

class Consumer extends Thread {
    private BlockingQueue<Integer> tasks;
    private boolean isStopped;

    public Consumer(BlockingQueue<Integer> tasks) {
        this.tasks = tasks;
        isStopped = false;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void execute() throws InterruptedException {
        int temp = tasks.take();
        System.out.println(Thread.currentThread().getName() + " Consumer Took Number: " + temp);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        isStopped = true;
        System.out.println("Consumer Stopped");
    }
}
