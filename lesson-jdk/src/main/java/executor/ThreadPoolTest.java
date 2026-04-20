package executor;

import java.util.concurrent.*;

/**
 * Created by sang on 2018/11/9.
 */
public class ThreadPoolTest {

    public void executor(){
        // Executors.newSingleThreadExecutor();
        // Executors.newFixedThreadPool(4);
      //  Executors.newCachedThreadPool();
      /*  ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("schedule");
            }
        },3, TimeUnit.SECONDS);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("atFixedRate");
            }
        },1,3,TimeUnit.SECONDS);
*/
    }

    public static void main(String[] args) {
    /*   ExecutorService executor =  Executors.newCachedThreadPool();
        executor.submit(()->{
            System.out.println(1);
        });*/

      /*  ExecutorService executor1 =  Executors.newFixedThreadPool(10);
        executor1.submit(()->{
            System.out.println(1);
        });*/

        ExecutorService executor2 =  Executors.newSingleThreadExecutor();
        executor2.execute(()->{
            System.out.println(2);
        });

        System.out.println("finish");

    }
}


