package com.simon.lesson3.controller;

import com.simon.lesson3.annotation.Controller;
import com.simon.lesson3.annotation.Qualifier;
import com.simon.lesson3.annotation.Service;
import com.simon.lesson3.service.UserService;

/**
 * Created by sang on 2018/12/14.
 */
@Controller
public class UserController {

    @Qualifier("")
    private UserService userService;

    public String getUserName(){
        return userService.getName();
    }

}
