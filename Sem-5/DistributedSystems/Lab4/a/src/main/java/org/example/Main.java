package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Account {
    private String name;
    private String surname;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) && Objects.equals(surname, account.surname) && Objects.equals(phoneNumber, account.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, phoneNumber);
    }

    public Account(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = number;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    @Override
    public String toString(){
        return "Name: " + name + " " + surname + ", Phone number: " + phoneNumber  + "\n";
    }
}

class Accounts {
    public ArrayList<Account> accounts;
    private final ReadWriteLock readWriteLock;

    public Accounts(){
        accounts = startAccountsInit();
        readWriteLock = new ReentrantReadWriteLock();
    }

    private ArrayList<Account> startAccountsInit(){
        ArrayList<Account> acc = new ArrayList<>();
        acc.add(new Account("Yaroslav", "Fedyna","+380504679032"));
        acc.add(new Account("Oleksandr", "Shevchenko","+380509069212"));
        acc.add(new Account("Petro", "Bondarenko","+380509768943"));
        acc.add(new Account("Ivan", "Shvets","+380503542167"));
        acc.add(new Account("Stepan", "Franko","+380953275423"));
        return acc;
    }
    public void lockRead() {readWriteLock.readLock().lock();}
    public void lockWrite() {readWriteLock.writeLock().lock();}

    public void unlockRead() {readWriteLock.readLock().unlock();}
    public void unlockWrite() {readWriteLock.writeLock().unlock();}
}

class ThreadA extends Thread {
    private final String surname;
    private final Accounts data;

    public ThreadA(Accounts data, String surname){
        this.surname = surname;
        this.data = data;
    }

    @Override
    public void run(){
        data.lockRead();
        System.out.println("Searching phone by surname");
        data.accounts.stream().filter(x -> x.getSurname().equals(surname)).forEach(x -> System.out.println("Thread A : " + x));
        try {Thread.sleep(5000);}
        catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("Searching phone by surname is finished");
        data.unlockRead();
    }
}

class ThreadB extends Thread {
    private final String phone;
    private final Accounts data;

    public ThreadB(Accounts data, String phone){
        this.phone = phone;
        this.data = data;
    }

    @Override
    public void run(){
        data.lockRead();
        System.out.println("Searching name by phone.");
        data.accounts.stream().filter(x -> x.getPhoneNumber().equals(phone)).forEach(x -> System.out.println("Thread B : " + x));
        try {Thread.sleep(5000);}
        catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("Searching name by phone is finished.");
        data.unlockRead();
    }
}


class ThreadC extends Thread {
    private final Accounts data;
    private final Account accountToAdd;
    private final Account accountToDelete;

    public ThreadC(Accounts data, Account add, Account del){
        this.data = data;
        this.accountToAdd = add;
        this.accountToDelete = del;
    }

    @Override
    public void run(){
        data.lockWrite();
        System.out.println("Writing/Deleting accounts.");
        if (!data.accounts.contains(accountToAdd)){
            data.accounts.add(accountToAdd);
        }
        data.accounts.remove(accountToDelete);

        try {Thread.sleep(5000);}
        catch(InterruptedException e){e.printStackTrace();}
        System.out.println("Writing/Deleting accounts is finished.");
        data.unlockWrite();
    }
}
public class Main {
    public static void main(String[] args) {
        Accounts data = new Accounts();
        ThreadA threadA = new ThreadA(data, "Shevchenko");
        ThreadB threadB = new ThreadB(data, "+380509768943");
        ThreadC threadC = new ThreadC(data, new Account("Dmytro", "Kovalov", "+380956734892"),new Account("Stepan", "Franko","+380953275423") );

        try {
            threadA.start();threadB.start();threadC.start();
            threadA.join();threadB.join();threadC.join();

           data.accounts.forEach(System.out::print);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}