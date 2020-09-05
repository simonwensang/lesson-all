package com.simon.lesson7.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sang on 2020/9/4.
 */
public class PrintArrayConditionDemo {

    private int index;
    private final ReentrantLock lock;

    private final Condition conditionA;

    private final Condition conditionB;

    private boolean flag ;

    public PrintArrayConditionDemo(){
        index = 1;
        lock= new ReentrantLock();
        conditionA =lock.newCondition();
        conditionB = lock.newCondition();
        flag = false;
    }

    public void APrint() throws InterruptedException {

        lock.lockInterruptibly();
        try {
            while (flag) {
                conditionA.await();
            }
            System.out.println("p1--"+index);
            index++;
            flag=true;
            conditionB.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void BPrint() throws InterruptedException {

        lock.lockInterruptibly();
        try {
            while (!flag){
                conditionB.await();
            }
            System.out.println("p2--"+index);
            index++;
            flag=false;
            conditionA.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintArrayConditionDemo print = new PrintArrayConditionDemo();

        new Thread(()-> {
            for (int i = 0; i < 50; i++) {
                try {
                    print.APrint();
                }catch (InterruptedException e){}

            }
        }).start();

        new Thread(()-> {
            for (int i = 0; i < 50; i++) {
                try {
                    print.BPrint();
                }catch (InterruptedException e){}
            }
        }).start();

    }

}
