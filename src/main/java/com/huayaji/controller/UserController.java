package com.huayaji.controller;

import com.huayaji.entity.User;
import com.huayaji.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView hello(){
        Map map = new HashMap();
        List<User> userAll = userService.findAll();
        System.out.println();
        map.put("data",userAll);
        map.put("code", 0);
        map.put("count", userAll.size());
        map.put("msg", null);
        //User user= userService.findById(1L);
        //System.out.println(user.toString());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(User user){
        Map map = new HashMap();
        userService.update(user);
        System.out.println("修改成功！！！！--update");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delete(User user){
        Map map = new HashMap();
        userService.delete(user.getId());
        System.out.println("删除成功！！！！--delete");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
