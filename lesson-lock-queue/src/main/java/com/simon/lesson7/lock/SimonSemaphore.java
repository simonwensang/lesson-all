package com.simon.lesson7.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sang on 2019/2/25.
 */
public class SimonSemaphore  {

    protected  SimonAQS aqs = new SimonAQS(){

        @Override
        public int tryAcquireShared(){
            for(;;){
                int count = status.get();
                int n = count-1;
                if(n<0 || count<=0){
                    return -1;
                }
                if(status.compareAndSet(count,n)){
                    return 1;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(){
            return  status.incrementAndGet()>=0;
        }

    };

    public SimonSemaphore(int n){
        this.aqs.status.set(n);
    }

    public void acquire(){
        aqs.acquireShared();
    }

    public void release(){
        aqs.releaseShared();
    }
}
