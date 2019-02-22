package com.simon.lesson7.queue;

/**
 * Created by sang on 2019/2/22.
 */
public interface IQueue<T> {

    T take();

    void put(T t)  ;
}
