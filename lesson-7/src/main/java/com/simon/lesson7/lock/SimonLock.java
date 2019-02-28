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
public class SimonLock implements Lock {

    volatile AtomicReference<Thread> owner = new AtomicReference();

    volatile LinkedBlockingQueue<Thread> waitlist = new LinkedBlockingQueue();

    @Override
    public void lock() {
        boolean putFlag = true;
        while(!tryLock()){
            if (putFlag){
                waitlist.offer(Thread.currentThread());
                putFlag = false;
            }else{
                LockSupport.park();
            }
        }
        waitlist.remove(Thread.currentThread());
    }

    @Override
    public boolean tryLock() {
        return owner.compareAndSet(null,Thread.currentThread());
    }

    @Override
    public void unlock() {
        if(owner.compareAndSet(Thread.currentThread(),null)){
            Iterator<Thread> iterator = waitlist.iterator();
            while(iterator.hasNext()){
                Thread thread = iterator.next();
                LockSupport.unpark(thread);
            }
        }
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
