package com.simon.lesson7.thread;

public class ThreadLocalDemo {

    ThreadLocal<Integer> count = new ThreadLocal();

    public void setCount(Integer count) {
        this.count.set(count);
    }
    public Integer getCount() {
        return this.count.get();
    }

}
