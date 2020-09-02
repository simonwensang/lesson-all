package com.simon.lesson7.lock;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by sang on 2019/2/27.
 */
public class SemaphoreDemo {

    //private Semaphore semaphore = new Semaphore(5);
    private SimonSemaphore semaphore = new SimonSemaphore(5);

    public void service(String vid) throws InterruptedException{

        System.out.println("楼上接待贵宾一位， 手牌："+vid);
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("送出贵宾一位，手牌："+vid);

    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i =1 ; i <= 20;i++) {
            String vip = "VIP00" + i;
            new Thread(() -> {
                try {
                    semaphoreDemo.semaphore.acquire();
                    semaphoreDemo.service(vip);
                    semaphoreDemo.semaphore.release();
                } catch (InterruptedException e) {

                }
            }).start();
        }

        Thread.sleep(10000);
    }

}
