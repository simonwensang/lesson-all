package com.simon.lesson7.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;

/**
 * Created by sang on 2020/3/8.
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {


        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();


        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,new Runnable(){
            @Override
            public void run() {
                System.out.println("4个线程执行了，开始批量插入："+Thread.currentThread());
                for (int i=0;i<4;i++){
                    System.out.println(linkedBlockingQueue.poll());
                }
            }
        });


        for (int i =0 ;i<20 ;i++){

            new Thread(()->{
            try {
                linkedBlockingQueue.add("data:" + Thread.currentThread());
                Thread.sleep(1000L);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"插入完毕");
            }catch (Exception e){
            }

            }).start();
        }


    }




}
