package com.simon.lesson3.service;

import com.simon.lesson3.annotation.Qualifier;
import com.simon.lesson3.annotation.Service;
import com.simon.lesson3.dao.UserDao;

/**
 * Created by sang on 2018/12/14.
 */
@Service("useService")
public class UserService {

    @Qualifier
    private UserDao userDao;

    public String getName(){
        return userDao.getUserName();
    }

}
