package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sang on 2018/11/9.
 */
public class ThreadPoolTest {

    public void executor(){
        Executors.newFixedThreadPool(4);
        Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
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
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

}


