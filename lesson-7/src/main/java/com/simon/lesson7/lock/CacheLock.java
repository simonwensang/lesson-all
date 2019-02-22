package com.simon.lesson7.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by sang on 2019/2/21.
 */
public class CacheLock {

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    private Map<String,Object> cache =  new HashMap();

    public Object get(String id){
        Object obj = null;
        //开启读锁
        lock.readLock().lock();
        try {
            if (cache.get(id) == null) {
                // 必须先释放读锁
                lock.readLock().unlock();
                //加写锁 ，不加写锁 ，全部查询数据库，缓存雪崩
                lock.writeLock().lock();
                try {
                    //双重检查 防止已有线程修改当前值
                    if (cache.get(id) == null) {
                        cache.put(id, "test");
                        obj = "test";
                    }else{
                        obj = cache.get(id);
                    }
                    lock.readLock().lock(); //加读锁 锁降级 不会有其他线程修改这个值，保证数据一致性
                }finally{
                    lock.writeLock().unlock(); //释放写锁
                }
            }else{
                obj = cache.get(id);
            }

        }finally {
            lock.readLock().unlock();
        }

        return obj;
    }

}
