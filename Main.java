import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.InterruptedIOException;
import java.security.InvalidParameterException;
import java.util.*;

/*
 * Please copy and paste the code into an ide of your choice for better look at the code and for 
 * execution , the code consists of a class main and two inner classes Producer and Consumer
 */

class Main {
    private Scanner s1 = new Scanner(System.in);
    private int option, value;
    private static Boolean exit = false;
    private Queue<Integer> nums = new LinkedList<>();
    BlockingQueue<Integer> tasks = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        Main m = new Main();
        Producer p = new Producer(m.tasks);
        Consumer c = new Consumer(m.tasks);

        p.setDaemon(true);
        c.setDaemon(true);

        p.start();
        c.start();

        m.printAvailableOptionsFirstTime();
        m.executeOption(p, c);

        while (!exit) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (p.status()) {
                m.printAvailableOptions();
                m.executeOption(p, c);
            }
        }
        System.out.println("Program Finished :)");
    }

    public void printAvailableOptions() {
        System.out.println("Task Finished!");
        System.out.println("1)Add amount of numbers");
        System.out.println("2)Add a single number");
        System.out.println("3)Add an array of numbers");
        System.out.println("4)Exit");
    }

    public void printAvailableOptionsFirstTime() {
        System.out.println("Welcome!");
        System.out.println("1)Add amount of numbers");
        System.out.println("2)Add a single number");
        System.out.println("3)Add an array of numbers");
        System.out.println("4)Exit");
    }

    public void exitExecution(Producer p, Consumer c) {
        exit = true;
        p.Stop();
        c.Stop();
    }

    public void executeOption(Producer p, Consumer c) {
        askForOption();
        if (option == 4) {
            exitExecution(p, c);
            return;
        }
        passOption(p);
        askForValue();
        passValues(p);
        if (option == 2) {
            p.wakeUpSync();
        }
        p.wakeUp();
    }

    public void askForOption() {
        System.out.print("Enter Option Number: ");
        option = s1.nextInt();
    }

    public void askForValue() {
        if (option == 3) {
            int temp;
            System.out.print("Enter values seperated by spaces(type -1 to stop): ");
            while (true) {
                temp = s1.nextInt();
                if (temp == -1) {
                    break;
                }
                nums.add(temp);
            }
            return;
        }
        System.out.print("Enter Value: ");
        value = s1.nextInt();
    }

    public void passValues(Producer p) {
        if (option == 3) {
            p.setValue(nums);

        } else
            p.setValue(value);
    }

    public void passOption(Producer p) {
        p.setOption(option);
    }

}
