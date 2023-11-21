package Tasks.Task_7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger waitTime = new AtomicInteger(500);

    public static void main(String[] args) {
        final int N = 3;

        CustomCyclicBarrier barrier = new CustomCyclicBarrier(N);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            threads.add(new Thread(() -> {
                try {
                    testMethod(barrier);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        threads.forEach(Thread::start);
    }

    public static void testMethod(CustomCyclicBarrier barrier) throws InterruptedException {
        long before = System.currentTimeMillis();
        System.out.println("Before barrier: " + Thread.currentThread().getName());
        Thread.sleep(waitTime.getAndAccumulate(1000, Integer::sum));

        barrier.await();

        long after = System.currentTimeMillis();
        System.out.println("After barrier: " + Thread.currentThread().getName() + ", waited: " + (after - before));
    }
}
