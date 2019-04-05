package com.huayaji.controller;

import com.huayaji.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public ModelAndView hello(String name){
        System.out.println(name);
        return new ModelAndView("success");
    }
}
