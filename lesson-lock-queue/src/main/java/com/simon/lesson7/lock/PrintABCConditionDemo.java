package com.simon.lesson7.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程顺序打印 ABCABC
 * 使用 ReentrantLock 和 Condition 实现
 * 
 * Created by sang on 2020/9/4.
 */
public class PrintABCConditionDemo {

    private final ReentrantLock lock;
    private final Condition conditionA;
    private final Condition conditionB;
    private final Condition conditionC;
    
    // 当前应该打印的字符编号：0=A, 1=B, 2=C
    private volatile int current = 0;

    public PrintABCConditionDemo() {
        lock = new ReentrantLock();
        conditionA = lock.newCondition();
        conditionB = lock.newCondition();
        conditionC = lock.newCondition();
    }

    /**
     * 线程A打印
     */
    public void printA() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 等待，直到轮到A打印
            while (current != 0) {
                conditionA.await();
            }
            // 打印A
            System.out.println("A");
            // 更新状态，轮到B打印
            current = 1;
            // 唤醒B线程
            conditionB.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 线程B打印
     */
    public void printB() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 等待，直到轮到B打印
            while (current != 1) {
                conditionB.await();
            }
            // 打印B
            System.out.println("B");
            // 更新状态，轮到C打印
            current = 2;
            // 唤醒C线程
            conditionC.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 线程C打印
     */
    public void printC() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 等待，直到轮到C打印
            while (current != 2) {
                conditionC.await();
            }
            // 打印C
            System.out.println("C");
            // 更新状态，轮到A打印
            current = 0;
            // 唤醒A线程
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintABCConditionDemo print = new PrintABCConditionDemo();
        int count = 5; // 打印5轮 ABC

        // 线程A
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    print.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-A").start();

        // 线程B
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    print.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-B").start();

        // 线程C
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    print.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-C").start();
    }

}
