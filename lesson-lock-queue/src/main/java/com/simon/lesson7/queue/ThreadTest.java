package com.simon.lesson7.queue;

public class ThreadTest {

    public void lock(){
        synchronized (this){
            try{
                Thread.sleep(1000);
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        notify();
    }

}
