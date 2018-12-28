package com.simon.lesson5.service;

/**
 * Created by sang on 2018/12/28.
 */
public class DemoServiceImpl implements   DemoService {

    @Override
    public String test(String param) {
        return param+">>>response";
    }
}
