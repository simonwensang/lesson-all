package com.simon.zookeeper.distributeLock;

import com.simon.zookeeper.config.StringSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by sang on 2019/5/23.
 */
public class ZkDistributeImproveLock implements Lock{

    private String  lockPath;

    private ZkClient zkClient;

    private ThreadLocal<String> currentPath = new ThreadLocal();

    private ThreadLocal<String> beforePath = new ThreadLocal();
    //锁重入计数器
    private ThreadLocal<Integer> reentrantCount = new ThreadLocal();

    public ZkDistributeImproveLock(ZkClient zkClient,String  lockPath){

        this.zkClient = zkClient;
        this.lockPath = lockPath;
        if(!this.zkClient.exists(this.lockPath)){
            try {
                this.zkClient.createPersistent(this.lockPath);
            }catch (ZkNodeExistsException e){}
        }

    }

    @Override
    public void lock() {

        if (!tryLock()) {
            //阻塞等待
            waitLock();

            //再次尝试得到锁
            lock();
        }

    }

    private void waitLock(){
        CountDownLatch countDownLatch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("------监听节点被删除:"+s);
                //唤醒 抢锁
                countDownLatch.countDown();
            }
        };
        //监听前一个节点
        zkClient.subscribeDataChanges(beforePath.get(),listener);

        try {
            if(this.zkClient.exists(beforePath.get())){
                //等待
                countDownLatch.await();
            }
        }catch (InterruptedException e){}
        //醒来后取消watch
        zkClient.unsubscribeDataChanges(beforePath.get(),listener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {

        if(this.reentrantCount.get()!=null){
           int count =  this.reentrantCount.get();
           if(count>0){
               this.reentrantCount.set(++count);
           }
        }

        //创建临时顺序节点
        if(StringUtils.isBlank(currentPath.get())){
            currentPath.set(zkClient.createEphemeralSequential(lockPath+"/","lock"));
            System.out.println("------创建节点:"+currentPath.get());
        }
        //得到lock目录下面所有子节点
        List<String> children = zkClient.getChildren(lockPath);
        Collections.sort(children);
        //如果是最小的
        if(currentPath.get().equals(lockPath+"/"+children.get(0))){
            reentrantCount.set(1);
            System.out.println("------获得锁，节点:"+currentPath.get());
            return true;
        }else{
            int before  = children.indexOf(currentPath.get().substring(lockPath.length()+1));
            beforePath.set(lockPath+"/"+children.get(before-1));
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        //去掉重入次数
        if(this.reentrantCount.get()!=null){
            int count =  this.reentrantCount.get();
            if(count>1){
                this.reentrantCount.set(--count);
                return ;
            }else{
                this.reentrantCount.set(null);
            }

        }

        //删除子节点

        System.out.println("------释放锁，节点:"+currentPath.get());
        this.zkClient.delete(this.currentPath.get());


    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        zkClient.setZkSerializer(new StringSerializer());

        int current = 50;
        CyclicBarrier barrier = new CyclicBarrier(current);

        for (int i =0 ; i< current ;i++){
            new Thread(

                ()->{
                    System.out.println(Thread.currentThread().getName()+"-------我准备好了------");
                    try{
                        //一起抢锁
                        barrier.await();
                    }catch (Exception e){}

                    ZkDistributeImproveLock lock = new ZkDistributeImproveLock(zkClient,"/distlock");
                    try{

                        lock.lock();
                        System.out.println(Thread.currentThread().getName()+"-------我抢到锁了------");

                    }catch (Exception e){

                    }finally {
                        lock.unlock();
                    }

                }
            ).start();

        }
    }

}
