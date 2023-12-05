package Tasks.Task_7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.BrokenBarrierException;

class CyclicBarrier {
    private int parties;
    private int count = 0;

    public CyclicBarrier(int parties) {
        this.parties = parties;
    }

    public synchronized void await() throws InterruptedException, BrokenBarrierException {
        count++;

        while (count < parties) {
            try {
                wait();
                break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        notifyAll();
        count = 0;
    }
}

class BarrierThread extends Thread {
    private CyclicBarrier barrier;

    public BarrierThread(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting at the barrier.");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " has passed the barrier.");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        final int numThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        for (int i = 0; i < numThreads; i++) {
            new BarrierThread(barrier).start();
        }
    }
}
