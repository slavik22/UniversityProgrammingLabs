package Tasks.Task_8;


class CustomReentrantLock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;


    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockedBy != currentThread) {
            wait();
        }
        isLocked = true;
        lockedBy = currentThread;
        lockCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;

            if (lockCount == 0) {
                isLocked = false;
                lockedBy = null;
                notify();
            }
        }
    }
}

public class Main {
    private static CustomReentrantLock lock = new CustomReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("Thread 1: First lock acquired.");
                lock.lock();
                System.out.println("Thread 1: Second lock acquired.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("Thread 2: First lock acquired.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
    }
}

