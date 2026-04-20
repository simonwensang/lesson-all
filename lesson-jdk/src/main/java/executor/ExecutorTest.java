package executor;

import bean.Student;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiugong
 * @Date: 2023/7/20
 */
public class ExecutorTest {

    public void execute() {
        Semaphore semaphore = new Semaphore(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1000L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),new ThreadPoolExecutionHandler());
        //Student student = new Student("小米",1,true);
        for (int i =0 ; i<100 ; i++){
            Student student = new Student("小米",i,true);
            int a = i ;
            try {
                semaphore.acquire();
                executor.submit(() -> {
                    try {
                        task(student);
                    }finally {
                        semaphore.release();
                    }
                });

            } catch (InterruptedException e) {
                semaphore.release();
            }
        }
        executor.shutdown();
    }

    public void task(Student student){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
         //   throw new RuntimeException(e);
        }
        System.out.println(student.getName()+student.getAge());
    }

    public void task2(Student student,int i){
        System.out.println(student.getName());
        student.setAge(i);
        student.setName("test"+i);
    }

    public static void main(String[] args) {
        ExecutorTest executor = new ExecutorTest();
        executor.execute();
    }
}
