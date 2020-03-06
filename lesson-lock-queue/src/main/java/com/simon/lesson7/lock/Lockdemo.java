package com.simon.lesson7.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sang on 2019/2/26.
 */
public class Lockdemo {

    private int i = 0;

    //Lock lock = new ReentrantLock();
    Lock lock = new SimonLock2();
    public void add(){
        lock.lock();
        try{
            i++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws  InterruptedException {
        Lockdemo lockdemo = new Lockdemo();

        for(int i=0; i<2;i++ ){
            new Thread(()->{
                for(int j=0; j<10000;j++ ){
                    lockdemo.add();
                }
            }).start();
        }

        Thread.sleep(1000L);
        System.out.println(lockdemo.i);

    }

}
