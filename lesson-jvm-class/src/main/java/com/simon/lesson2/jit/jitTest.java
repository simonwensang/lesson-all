package com.simon.lesson2.jit;

/**
 * Created by sang on 2019/12/5.
 */
public class jitTest {

    private static boolean stop =false;

   // private static volatile int i = 0;

    //-server -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=jit001.log
    public static void main(String[] args) throws  Exception{

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("jit test start");
                while (!stop){

                }
            }
        });


        thread.start();
        Thread.sleep(1000);
        stop=true;

        thread.join();
        System.out.println("jit test stop");
    }

}
