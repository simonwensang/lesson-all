package com.simon.lesson7.lock;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sang on 2020/2/29.
 */
public class MyQueue    {

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private final Object[] items = new Object[10];
    private int putpre,takepre,count;

    public boolean put(Object o) throws InterruptedException {

        lock.lock();
        try {
            while (count == items.length) {
               System.out.println("notFull.await()");
               notFull.await();
            }
            items[putpre] = o;

            if( ++putpre==items.length) {
                putpre = 0;
            }

            ++count;
            System.out.println("notEmpty.signal()");
            notEmpty.signal();
        }finally {
            lock.unlock();
        }

        return true;
    }


    public Object take()throws InterruptedException {
        lock.lock();
        Object object=null;
        try {
            while(count == 0){
                System.out.println("notEmpty.wait():");
                notEmpty.wait();
            }
            object = items[takepre];
            if(++takepre == items.length){
               takepre=0;
            }
            --count;
           System.out.println("notFull.signal()");
           notFull.signal();

        }finally {
            lock.unlock();
        }

        return object;
    }


    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        new Thread(()-> {
            try{
                for (int i = 0; i < 100; i++) {
                    myQueue.put(i);
                }
            }catch (InterruptedException e){

            }

        }).start();

        new Thread(()-> {
            try{
                for (int i = 0; i < 100; i++) {
                    System.out.println(myQueue.take());
                }
            }catch (InterruptedException e){

            }

        }).start();
    }

}
