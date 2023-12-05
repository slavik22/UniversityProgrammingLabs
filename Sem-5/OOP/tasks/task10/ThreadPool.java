package Tasks.Task_10;

//import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

//public class CustomThreadPool {
//    private final ConcurrentLinkedDeque<Runnable> tasks = new ConcurrentLinkedDeque<>();
//    private final AtomicBoolean poolShutDown = new AtomicBoolean(false);
//
//    public CustomThreadPool(int numThreads) {
//        for (int i = 0; i < numThreads; i++) {
//            ExecutorThread thread = new ExecutorThread();
//            thread.setName("Executor thread #" + i);
//            thread.start();
//        }
//    }
//
//    public void execute(Runnable task) throws InterruptedException {
//        if (poolIsShutDown()) {
//            throw new InterruptedException("Thread pool is shut down!");
//        }
//
//        tasks.add(task);
//        synchronized (this) {
//            this.notifyAll();
//        }
//    }
//
//    public void shutDownPool() {
//        this.poolShutDown.getAndSet(true);
//        synchronized (this) {
//            this.notifyAll();
//        }
//    }
//
//    public void shutDownImmediately() {
//        this.tasks.clear();
//        shutDownPool();
//    }
//
//    private boolean poolIsShutDown() {
//        return this.poolShutDown.get();
//    }
//
//    private class ExecutorThread extends Thread {
//
////        @SneakyThrows
//        @Override
//        public void run() {
//            while (!poolIsShutDown()) {
//                Runnable task = tasks.poll();
//                if (task == null) {
//                    synchronized (CustomThreadPool.this) {
//                        try {
//                            CustomThreadPool.this.wait();
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                } else {
//                    task.run();
//                }
//            }
//        }
//    }
//}
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class WorkerThread extends Thread {
    private BlockingQueue<Runnable> taskQueue;

    public WorkerThread(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

public class ThreadPool {
    private final int poolSize;
    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new LinkedList<>();

        for (int i = 0; i < poolSize; i++) {
            WorkerThread worker = new WorkerThread(taskQueue);
            worker.start();
            workers.add(worker);
        }
    }

    public void submit(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Task " + taskId + " is running by thread " + Thread.currentThread().getId());
            });
        }

        threadPool.shutdown();
    }
}
