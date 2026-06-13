package com.simon.lesson7.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程顺序打印优化版
 * 优化点：使用单个 Condition，状态变量更清晰
 * 
 * Created by sang on 2020/9/4.
 */
public class PrintArrayConditionDemoOptimized {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    
    private int index = 1;
    // 0=轮到A打印, 1=轮到B打印
    private volatile int turn = 0;

    /**
     * 线程A打印
     */
    public void printA() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (turn != 0) {
                condition.await();
            }
            System.out.println("p1--" + index);
            index++;
            turn = 1;
            condition.signal();
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
            while (turn != 1) {
                condition.await();
            }
            System.out.println("p2--" + index);
            index++;
            turn = 0;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintArrayConditionDemoOptimized print = new PrintArrayConditionDemoOptimized();
        int count = 50;

        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    print.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-A").start();

        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    print.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-B").start();
    }

}
