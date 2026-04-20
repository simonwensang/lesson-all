package com.simon.lesson7.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sang on 2019/2/25.
 */
public class SimonLock2 implements Lock {

    protected  SimonAQS aqs = new SimonAQS(){

        public Boolean tryAcquire(){
          return owner.compareAndSet(null,Thread.currentThread());
        }

        public Boolean tryRelease(){
           return owner.compareAndSet(Thread.currentThread(),null);
        }

    };

    @Override
    public void lock() {
        aqs.acquire();
    }

    @Override
    public boolean tryLock() {
        return aqs.tryAcquire();
    }

    @Override
    public void unlock() {
        aqs.release();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
