package com.simon.lesson7.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sang on 2020/2/29.
 */
public class MyArrayBlockingQueue<E> {

    private final ReentrantLock lock ;
    private final Condition notEmpty  ;
    private final Condition notFull  ;

    private final Object[] items  ;
    private int putIndex, takeIndex, count;

    public MyArrayBlockingQueue(int capacity){
        this(capacity,false);
    }

    public MyArrayBlockingQueue(int capacity, boolean fair){
        if(capacity<=0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock =  new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public void put(E o) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
               System.out.println("----- Full");
               notFull.await();
            }
           enqueue(o);

        }finally {
            lock.unlock();
        }
    }

    private void enqueue(E x) {
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();
        System.out.println("===== notEmpty");
    }

    public E take()throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while(count == 0){
               System.out.println("----- Empty");
               notEmpty.await();
            }
            return dequeue();
        }finally {
            lock.unlock();
        }
    }
    private E dequeue() {
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        notFull.signal();
        System.out.println("===== notFull");
        return x;
    }
    public static void main(String[] args) {
        MyArrayBlockingQueue myArrayBlockingQueue = new MyArrayBlockingQueue(10);
        // ArrayBlockingQueue myArrayBlockingQueue = new ArrayBlockingQueue(10);
        new Thread(()-> {
            try{
                for (int i = 0; i < 100; i++) {
                    myArrayBlockingQueue.put(i);
                }
            }catch (InterruptedException e){

            }

        }).start();

        new Thread(()-> {
            try{
                for (int i = 0; i < 100; i++) {
                    System.out.println(myArrayBlockingQueue.take());
                }
            }catch (InterruptedException e){

            }

        }).start();
    }

}
