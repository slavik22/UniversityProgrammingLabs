package org.example;

import java.lang.Thread;
import java.lang.ThreadGroup;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadHierarchyMonitor {
    public static void monitorThreadHierarchy(ThreadGroup threadGroup, long intervalMillis) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                printThreadHierarchy(threadGroup, "");
            }
        }, 0, intervalMillis);
    }

    private static void printThreadHierarchy(ThreadGroup group, String indent) {
        System.out.println(indent + "Group: " + group.getName() + ", Max Priority: " + group.getMaxPriority());
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads, false);
        for (Thread thread : threads) {
            if (thread != null) {
                System.out.println(indent + "  Thread: " + thread.getName() + ", Priority: " + thread.getPriority());
            }
        }

        ThreadGroup[] subGroups = new ThreadGroup[group.activeGroupCount()];
        group.enumerate(subGroups, false);
        for (ThreadGroup subGroup : subGroups) {
            printThreadHierarchy(subGroup, indent + "  ");
        }
    }

    public static void main(String[] args) {
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup group1 = new ThreadGroup(mainGroup, "Group 1");
        ThreadGroup group2 = new ThreadGroup(mainGroup, "Group 2");

        Thread thread1 = new Thread(group1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.setName("Thread 1");
        thread1.start();

        Thread thread2 = new Thread(group2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread2.setName("Thread 2");
        thread2.start();

        monitorThreadHierarchy(mainGroup, 2000); // Every 2 seconds

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

