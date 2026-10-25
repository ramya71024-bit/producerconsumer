
import java.util.LinkedList;

class PC {
    private final LinkedList<Integer> list = new LinkedList<>();
    private final int capacity = 2;
    private int value = 0;

    public void produce() {
        try {
            while (true) {
                synchronized (this) {
                    while (list.size() == capacity) {
                        wait();
                    }

                    list.add(value);
                    System.out.println("Producer produced-" + value);
                    value++;

                    notify();
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void consume() {
        try {
            while (true) {
                synchronized (this) {
                    while (list.isEmpty()) {
                        wait();
                    }

                    int val = list.removeFirst();
                    System.out.println("Consumer consumed-" + val);

                    notify();
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args)
            throws InterruptedException {

        PC pc = new PC();

        Thread producer = new Thread(() -> pc.produce());
        Thread consumer = new Thread(() -> pc.consume());

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
