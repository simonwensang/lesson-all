package com.simon.lesson7.queue;

/**
 * Created by sang on 2019/2/22.
 */
public interface IQueue<T> {

    T take() throws InterruptedException;

    void put(T t) throws InterruptedException;
}
