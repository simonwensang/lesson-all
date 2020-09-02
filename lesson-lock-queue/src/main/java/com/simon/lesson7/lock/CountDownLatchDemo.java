package com.simon.lesson7.lock;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sang on 2020/3/4.
 */
public class CountDownLatchDemo {

    //1 并发
   public static void main(String[] args) throws  Exception {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i =0 ; i<4;i++){
            new Thread(()->{
                System.out.println("我是"+Thread.currentThread());
                countDownLatch.countDown();

                try {
                    countDownLatch.await();
                }catch (InterruptedException e){

                }
                System.out.println("我执行了"+Thread.currentThread());

            }).start();
        }

        Thread.sleep(2000L);

        new Thread(()->{
            System.out.println("我是"+Thread.currentThread());
            countDownLatch.countDown();

            try {
                countDownLatch.await();
            }catch (InterruptedException e){

            }
            System.out.println("全部执行完毕，我来召唤神龙"+Thread.currentThread());

        }).start();
    }

    //2 等待结果
    /*public static void main(String[] args) throws  Exception {

        //CountDownLatch countDownLatch = new CountDownLatch(5);
        SimonCountDownLatch countDownLatch = new SimonCountDownLatch(5);
        for (int i =0 ; i<5;i++){
            new Thread(()->{
                try {
                    Thread.sleep(2000L);
                    System.out.println("我执行了"+Thread.currentThread());
                }catch (Exception e ){

                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        System.out.println("全部执行完毕，我来召唤神龙");

    }*/
}
