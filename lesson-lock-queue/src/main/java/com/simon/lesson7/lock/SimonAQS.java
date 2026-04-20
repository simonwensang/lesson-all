package com.simon.lesson7.lock;

import javax.annotation.processing.SupportedOptions;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sang on 2019/2/27.
 */
public class SimonAQS {

    public volatile AtomicReference<Thread> owner = new AtomicReference<>();

    public volatile LinkedBlockingQueue<Thread> waiter = new LinkedBlockingQueue<>();

    public volatile AtomicInteger status = new AtomicInteger(0);

    public void releaseShared(){
        if(tryReleaseShared()){
            Iterator<Thread> it = waiter.iterator();
            while(it.hasNext()){
                LockSupport.unpark(it.next());
            }
        }
    }

    public void acquireShared(){
        boolean addQue = true;
        while(tryAcquireShared()<0){
            if(addQue){
                waiter.offer(Thread.currentThread());
                addQue = false;
            }else{
                LockSupport.park();
            }
        }
        waiter.remove(Thread.currentThread());
    }

    public boolean tryReleaseShared(){
        throw new UnsupportedOperationException();
    }

    public int tryAcquireShared(){
        throw new UnsupportedOperationException();
    }


    public void acquire(){
        boolean addQue = true;
        while(!tryAcquire()){
            if(addQue){
                waiter.offer(Thread.currentThread());
                addQue = false;
            }else{
                LockSupport.park();
            }
        }
        waiter.remove(Thread.currentThread());
    }

    public void release(){
        if(tryRelease()){
            Iterator<Thread> it = waiter.iterator();
            while(it.hasNext()){
                LockSupport.unpark(it.next());
            }
        }
    }

    public Boolean tryAcquire(){
        throw new UnsupportedOperationException();
    }

    public Boolean tryRelease(){
        throw  new UnsupportedOperationException();
    }

}
