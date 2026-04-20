package com.simon.lesson7.thread;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

/**
 * Created by sang on 2019/12/4.
 */
public class ThreadPoolTest {

    private ExecutorService executor ;

    public ThreadPoolTest(){
        executor = new ThreadPoolExecutor(5,10, 0L,
                TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(3),new RejectedExecutionHandler(){
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("task reject");
            }
        });

    }

    public void eecutors(){
       // executor = Executors.newFixedThreadPool(3);
        //executor = Executors.newSingleThreadExecutor();
        //executor = Executors.newCachedThreadPool();
        ScheduledExecutorService  schedukedExecutor = Executors.newScheduledThreadPool(3);
        Runnable cammond = new  Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                    System.out.println("task start");
                }catch (InterruptedException e){

                }
            }
        };
        schedukedExecutor.scheduleAtFixedRate(cammond,1,2,TimeUnit.SECONDS);
        schedukedExecutor.scheduleWithFixedDelay(cammond,1,2,TimeUnit.SECONDS);
    }

    public void submit(){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                    System.out.println("task start");
                }catch (InterruptedException e){

                }
            }
        });

    }

    public static void main(String[] args) {
       ThreadPoolTest threadPoolTest = new ThreadPoolTest();
       /* threadPoolTest.executor.execute(()->{
           System.out.println(1/0);
           // System.out.println(1);
        });*/
       threadPoolTest.executor.submit(()->{
             System.out.println(1/0);
            //System.out.println(0);
        });
      /*  try {
            threadPoolTest.executor.awaitTermination(1000,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
