package com.simon.lesson3.controller;

import com.simon.lesson3.annotation.Controller;
import com.simon.lesson3.annotation.Qualifier;
import com.simon.lesson3.annotation.RequestMapping;
import com.simon.lesson3.annotation.Service;
import com.simon.lesson3.common.RequestMethod;
import com.simon.lesson3.service.UserService;

/**
 * Created by sang on 2018/12/14.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Qualifier()
    private UserService userService;

    @RequestMapping(value="get",  method ={RequestMethod.GET})
    public String getUserName(){
        return userService.getName();
    }

}
