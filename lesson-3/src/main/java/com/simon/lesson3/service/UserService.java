package com.simon.lesson3.service;

import com.simon.lesson3.annotation.Service;

/**
 * Created by sang on 2018/12/14.
 */
@Service("useService")
public class UserService {

    public String getName(){

        return "test";
    }

}
