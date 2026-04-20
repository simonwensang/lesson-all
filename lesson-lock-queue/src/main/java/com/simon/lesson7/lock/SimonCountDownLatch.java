package com.simon.lesson7.lock;

/**
 * Created by sang on 2020/3/5.
 */
public class SimonCountDownLatch {

    protected SimonAQS aqs = new SimonAQS(){

        @Override
        public boolean tryReleaseShared() {
            return this.status.decrementAndGet()==0;
        }

        @Override
        public int tryAcquireShared() {
            return  this.status.get()==0?1:-1;
        }
    };

    public SimonCountDownLatch(int i){
        aqs.status.set(i);
    }

    public void countDown(){
        this.aqs.releaseShared();
    }

    public void await(){
        this.aqs.acquireShared();
    }
}
