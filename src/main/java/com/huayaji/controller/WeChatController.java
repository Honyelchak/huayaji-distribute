package com.huayaji.controller;

import com.huayaji.services.DistributeService;
import com.huayaji.services.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class WeChatController {

    @Resource
    private UserService userService;
    @Resource
    private DistributeService distributeService;

}
