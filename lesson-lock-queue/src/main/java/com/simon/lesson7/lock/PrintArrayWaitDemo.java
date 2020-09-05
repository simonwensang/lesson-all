package com.simon.lesson7.lock;

/**
 * Created by sang on 2020/9/4.
 */
public class PrintArrayWaitDemo {

    private int index ;
    private boolean flag ;

    public PrintArrayWaitDemo(){
        index = 1 ;
        flag= false;
    }

    public void APrint()  {
        synchronized(this){
            //等待
            while (flag){
                try {
                    wait();
                }catch (InterruptedException e){}
            }
            //被唤醒--》打印
            System.out.println("p1--"+index);
            index ++ ;
            flag = true;
            //唤醒
            notifyAll();
        }

    }
    public void BPrint()  {
        synchronized (this) {
            //等待
            while (!flag){
                try {
                    wait();
                }catch (InterruptedException e){}
            }
            //被唤醒--》打印
            System.out.println("p2--"+index);
            index ++ ;
            flag = false;
            //唤醒
            notifyAll();
        }
    }
    public static void main(String[] args) {
        PrintArrayWaitDemo print = new PrintArrayWaitDemo();
        new Thread(()-> {
            for (int i = 0; i < 50; i++) {
                print.APrint();
            }
        }).start();

        new Thread(()-> {
            for (int i = 0; i < 50; i++) {
                print.BPrint();
            }
        }).start();

    }
}
