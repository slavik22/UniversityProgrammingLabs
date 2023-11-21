package Tasks.Task_8;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CustomReentrantLock customReentrantLock = new CustomReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                test(customReentrantLock);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        Thread thread2 = new Thread(() -> {
            try {
                test(customReentrantLock);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void test(CustomReentrantLock reentrantLock) throws InterruptedException {
        reentrantLock.lock();

        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Time before sleep: " + LocalTime.now());
        Thread.sleep(2000);

        System.out.println("Time after sleep: " + LocalTime.now());

        reentrantLock.unlock();
    }
}
