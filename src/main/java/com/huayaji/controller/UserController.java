package com.huayaji.controller;

import com.huayaji.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.Table;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(String name){
        System.out.println(name);
        return "success";
    }
}
