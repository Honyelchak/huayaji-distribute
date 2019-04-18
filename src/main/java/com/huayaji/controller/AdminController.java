package com.huayaji.controller;

import com.huayaji.entity.Admin;
import com.huayaji.services.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @RequestMapping("/login")
    public ModelAndView login(Admin admin){
        Map map = new HashMap();
        if (adminService.login(admin.getUserName(), admin.getPassWord()) != null) {
            map.put("code", 0);
            map.put("msg", "登录成功");
            return new ModelAndView("index.html");
        } else {
            map.put("code", -1);
            map.put("msg", "登录失败");
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }


    @RequestMapping("/register")
    public ModelAndView register(Admin admin){
        Map map = new HashMap();
        if (adminService.findByUserName(admin.getUserName()) == null) {
            adminService.save(admin);
            map.put("code", 0);
            map.put("msg", "登录成功");
            return new ModelAndView("page/login/login");
        } else {
            map.put("code", -1);
            map.put("msg", "用户名重复");
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }

}
