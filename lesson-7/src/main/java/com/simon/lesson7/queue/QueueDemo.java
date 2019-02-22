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

        public E take()  {


            return null;
        }

        public void put(E e) {

            lock.lock();
            try{
                //满了
                while(count == items.length){
                    notFull.await();//阻塞线程
                }
                items[takepre]=e;

            } catch (InterruptedException ex)
            {}
            finally {
                lock.unlock();
            }

        }

}
