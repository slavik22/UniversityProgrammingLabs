package org.example;

import java.util.ArrayList;
import java.util.Random;

class RWLock {
    private Integer readSemaphore;
    private Integer writeSemaphore;

    public RWLock() {
        readSemaphore = 1;
        writeSemaphore = 1;
    }

    public void ReadLock() {
        while (readSemaphore == 0) {}
        synchronized (readSemaphore) {
            readSemaphore = 0;
        }
    }
    public void ReadUnlock() {
        readSemaphore = 1;
    }
    public void WriteLock() {
        while (readSemaphore == 0) {}
        synchronized (readSemaphore) {
            readSemaphore = 0;
        }
        while (writeSemaphore == 0) {}
        synchronized (writeSemaphore) {
            writeSemaphore = 0;
        }
    }
    public void WriteUnlock() {
        readSemaphore = 1;
        writeSemaphore = 1;
    }
}
class Matrix {
    private final ArrayList<ArrayList<Integer>> integers;
    private final ArrayList<ArrayList<RWLock>> locks;

    public Matrix(int num) {
        locks = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            ArrayList<RWLock> tmp = new ArrayList<>(num);
            for (int j = 0; j < num; j++)
                tmp.add(new RWLock());
            locks.add(tmp);
        }


        Random random = new Random();
        integers = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            ArrayList<Integer> tmp = new ArrayList<>(num);
            for (int j = 0; j < num; j++)
                tmp.add(Math.abs(random.nextInt()) % 100);
            integers.add(tmp);
        }

    }
    public int getSize() {return integers.size();}

    public int getPrice(int i, int j) throws Exception {
        if (i < 0 || j < 0) throw new Exception();
        return integers.get(i).get(j);
    }

    public void setPrice(int i, int j, int value) throws Exception {
        if (i < 0 || j < 0) throw new Exception();
        integers.get(i).set(j, value);
    }


    public void LockRead(int i, int j) {locks.get(i).get(j).ReadLock();}
    public void UnlockRead(int i, int j) {locks.get(i).get(j).ReadUnlock();}
    public void LockWrite(int i, int j) {locks.get(i).get(j).WriteLock();}

    public void LockWrite(int i) {
        for (int j = 0; j < locks.get(i).size(); j++) {
            LockWrite(i, j);
        }
    }
    public void UnLockWrite(int i) {
        for (int j = 0; j < locks.get(i).size(); j++) {
            UnlockWrite(i, j);
        }
    }
    public void UnlockWrite(int i, int j) {locks.get(i).get(j).WriteUnlock();}

    public void LockRead() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j< locks.get(0).size(); j++)
                LockRead(i ,j);
    }
    public void UnlockRead() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j< locks.get(0).size(); j++)
                UnlockRead(i ,j);
    }

    public void matrixOutput() {
        try {
            for (int i = 0; i < getSize(); i++){
                for (int j = 0; j < getSize(); j++) {
                    System.out.print(getPrice(i, j) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}

class ThreadA extends Thread{
    private Matrix matrix;

    public ThreadA(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int i = Math.abs(random.nextInt()) % matrix.getSize();
            int j = Math.abs(random.nextInt()) % matrix.getSize();
            int priceToChange = Math.abs(random.nextInt()) % 100;

            try {
                while (matrix.getPrice(i, j) == -1) {
                    i = Math.abs(random.nextInt()) % matrix.getSize();
                    j = Math.abs(random.nextInt()) % matrix.getSize();
                    priceToChange = Math.abs(random.nextInt()) % 100;
                }
            } catch (Exception e) {e.printStackTrace();}

            matrix.LockWrite(i, j);
            System.out.println("Changing price.");
            try {
                matrix.setPrice(i, j, priceToChange);
                System.out.println("Changing price from " + i + " to " + j + " is " + priceToChange);
            } catch (Exception e) {e.printStackTrace();}
            System.out.println("Finished to change price");
            matrix.UnlockWrite(i, j);

            try {Thread.sleep(8000);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}

class ThreadB extends Thread{
    private Matrix matrix;

    public ThreadB(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            int i = Math.abs(rand.nextInt()) % matrix.getSize();
            int j = Math.abs(rand.nextInt()) % matrix.getSize();
            boolean addBool = rand.nextBoolean();

            if (addBool) {
                try {
                    while (matrix.getPrice(i, j) == -1) {
                        i = Math.abs(rand.nextInt()) % matrix.getSize();
                        j = Math.abs(rand.nextInt()) % matrix.getSize();
                    }
                } catch (Exception e) {e.printStackTrace();}
            } else {
                try {
                    while (matrix.getPrice(i, j) != -1) {
                        i = Math.abs(rand.nextInt()) % matrix.getSize();
                        j = Math.abs(rand.nextInt()) % matrix.getSize();
                    }
                } catch (Exception e) {e.printStackTrace();}
            }

            matrix.LockWrite(i, j);
            System.out.println("Add/Delete way: Start");
            try {
                if (addBool) {
                    int new_price = Math.abs(rand.nextInt()) % 100;
                    try {
                        matrix.setPrice(i, j, new_price);
                        System.out.println("Changing price: Added from " + i + " to " + j + " is " + new_price);
                    } catch (Exception e) {e.printStackTrace();}
                } else {
                    try {
                        matrix.setPrice(i, j, -1);
                        System.out.println("Changing price: Deleted from " + i + " to " + j);
                    } catch (Exception e) {e.printStackTrace();}
                }
                matrix.setPrice(i, j, -1);
            } catch (Exception e) {e.printStackTrace();}
            System.out.println("Add/Delete way: Finish");
            matrix.UnlockWrite(i, j);

            try {Thread.sleep(6000);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}

class ThreadC extends Thread{
    private Matrix matrix;

    public ThreadC(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (true) {
            int i = Math.abs(rand.nextInt()) % matrix.getSize();
            int j = Math.abs(rand.nextInt()) % matrix.getSize();

            try {
                while (matrix.getPrice(i, j) == -1) {
                    i = Math.abs(rand.nextInt()) % matrix.getSize();
                    j = Math.abs(rand.nextInt()) % matrix.getSize();
                }
            } catch (Exception e) {e.printStackTrace();}

            int price;
            try {
                price = matrix.getPrice(i,j);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println("Found way from " + i + " to " + j + ". Price = " + price);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Matrix matrix = new Matrix(10);

        ThreadA a = new ThreadA(matrix);
        ThreadB b = new ThreadB(matrix);
        ThreadC c = new ThreadC(matrix);

        a.start();b.start();c.start();
        a.join(); b.join(); c.join();

        System.out.println("Hello world!");
    }
}