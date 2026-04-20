package com.simon.lesson7.queue;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sang on 2019/2/22.
 */
public class QueueDemo<E> implements  IQueue<E> {

    final Lock lock = new ReentrantLock();

    final Condition notFull = lock.newCondition();

    final Condition notEmpty= lock.newCondition();

    final Object[] items  = new Object[10];

    int putptr,takepre,count;

    public void put(E e) throws InterruptedException{

        lock.lock();
        try{
            //满了
            while(count == items.length){
                notFull.await();//阻塞线程
            }
            items[putptr]=e;
            if(++putptr==items.length)putptr=0;
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }

    }

    public E take() throws InterruptedException{
        lock.lock();
        try{
            //空了
            while(count == 0){
                notEmpty.await();//阻塞线程
            }
            E x= (E)items[takepre];
            items[takepre]=null;
            if(++takepre==items.length)takepre=0;
            count--;
            notFull.signal();
            return x;
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException{
        final IQueue<Integer> queueDemo = new QueueDemo<Integer>();

        Thread t1 =  new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    queueDemo.put(i);
                    System.out.println("put="+i); ;
                }
            }catch (InterruptedException ex){

            }
        });

        Thread t2 =  new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    System.out.println("take="+queueDemo.take()); ;
                }
            }catch (InterruptedException ex){

            }
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}
