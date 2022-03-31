import java.security.InvalidParameterException;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

class Producer extends Thread {
    private boolean isStopped, taskFinished;
    private BlockingQueue<Integer> tasks;
    private int randNum, option, value;
    private Queue<Integer> values;

    public Producer(BlockingQueue<Integer> tasks) {
        this.tasks = tasks;
        isStopped = false;
        taskFinished = true;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!taskFinished) {
                System.out.println("Executing inside Producer run method");
                executeOption();
            }
        }
    }

    public void Stop() {
        isStopped = true;
        System.out.println("Producer Stopped");
    }

    private void addToQueue(int num) {
        if (this.isStopped)
            throw new IllegalStateException("Thread is stopped");
        tasks.offer(num);
        System.out.println(Thread.currentThread().getName() + " Producer Added: " + num);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addArrayOfValues() {
        while (!values.isEmpty()) {
            addToQueue(values.poll());
        }
        taskFinished = true;
    }

    public void addAmountOfNumbers() {
        Random r = new Random();
        for (int i = 0; i < value; i++) {
            randNum = r.nextInt(100);
            addToQueue(randNum);
        }
        taskFinished = true;
    }

    public void addSingleValue() {
        addToQueue(value);
        taskFinished = true;
    }

    public boolean status() {
        return taskFinished;
    }

    public void wakeUp() {
        if (taskFinished == true)
            taskFinished = false;
        else
            throw new IllegalStateException("thread already executing a task");
    }

    public void sleepThread() {
        synchronized (this) {
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void wakeUpSync() {
        synchronized (this) {
            notify();
        }
    }

    public void setOption(int option) {
        if (option >= 1 && option <= 4)
            this.option = option;
        else {
            try {
                throw new InvalidParameterException("Option should be 1 or 2 or 3 or 4");
            } catch (InvalidParameterException e) {
                e.getMessage();
            }
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValue(Queue<Integer> values) {
        this.values = values;
    }

    private void executeOption() {
        switch (this.option) {
            case 1:
                addAmountOfNumbers();
                break;
            case 2:
                addSingleValue();
                break;
            case 3:
                addArrayOfValues();
                break;
        }
    }
}
