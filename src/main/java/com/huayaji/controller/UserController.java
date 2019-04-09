package com.huayaji.controller;

import com.huayaji.entity.User;
import com.huayaji.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public ModelAndView hello(String name){

//        List<User> userAll = userService.findAll();
//        for (User user : userAll) {
//            System.out.println(user.toString());
//        }
        User user= userService.findById(1L);
        System.out.println(user.toString());
        return new ModelAndView("success");
    }
}
