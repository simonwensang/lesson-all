package com.simon.lesson7.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by sang on 2020/9/3.
 */
public class FactoryDemo {

    private Object items   ;

    public void product()  {
        synchronized(this){
            while(items!=null){
                try {
                    wait();
                }catch (InterruptedException e){}
            }
            items = new Object();
            System.out.println("put");
            notifyAll();
        }

    }
    public void consumer()  {
        synchronized (this) {
            while (items == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            items = null;
            System.out.println("get");
            notifyAll();
        }
    }

    public void testPark(){
        Thread cousumer = new Thread(()->{
            while(items ==null){
                LockSupport.park();
            }
            items = null;
            System.out.println("consumer");
        });
        cousumer.start();
        try {
            Thread.sleep(1000L) ;
        } catch (InterruptedException e) {
        }
        items = new Object();
        System.out.println("product");
        LockSupport.unpark(cousumer);

    }

    public static void main(String[] args) {
        FactoryDemo factory = new FactoryDemo();

       /* new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                factory.product();
            }
        }).start();

        new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                factory.consumer();
            }
        }).start();*/

        factory.testPark();
    }
}
