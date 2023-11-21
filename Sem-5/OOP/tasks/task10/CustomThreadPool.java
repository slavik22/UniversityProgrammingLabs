package Tasks.Task_10;

import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {
    private final ConcurrentLinkedDeque<Runnable> tasks = new ConcurrentLinkedDeque<>();
    private final AtomicBoolean poolShutDown = new AtomicBoolean(false);

    public CustomThreadPool(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            ExecutorThread thread = new ExecutorThread();
            thread.setName("Executor thread #" + i);
            thread.start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (poolIsShutDown()) {
            throw new InterruptedException("Thread pool is shut down!");
        }

        tasks.add(task);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void shutDownPool() {
        this.poolShutDown.getAndSet(true);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void shutDownImmediately() {
        this.tasks.clear();
        shutDownPool();
    }

    private boolean poolIsShutDown() {
        return this.poolShutDown.get();
    }

    private class ExecutorThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            while (!poolIsShutDown()) {
                Runnable task = tasks.poll();
                if (task == null) {
                    synchronized (CustomThreadPool.this) {
                        CustomThreadPool.this.wait();
                    }
                } else {
                    task.run();
                }
            }
        }
    }
}
