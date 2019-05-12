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
    public ModelAndView hello(Integer page,Integer limit, String search){
        Map map = new HashMap();
        List<User> userAll = userService.findByPage(page, limit, search);
        long total =userService.getCount(search);
        System.out.println(total);
        map.put("count",total);
        int pages= (int) (total%limit==0?total/limit:total/limit+1);
        map.put("curnum",page);
        map.put("limit",limit);
        map.put("data",userAll);
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(User user){
        Map map = new HashMap();
        userService.update(user);
        logger.info("更新成功！");
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
        logger.info("删除成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "删除成功！");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView save(User user){
        Map map = new HashMap();
        userService.save(user);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    @RequestMapping(value = "/getUserById", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView getUserById(Long id) {

        Map map = new HashMap();
        User user = userService.findById(id);
        if (user != null) {
            logger.info("查找成功！");
            map.put("res", "ok");
            map.put("code", 0);
            map.put("data", user);
            map.put("msg", "用户存在");
        }
        else{
            logger.info("查找失败！");
            map.put("res", "false");
            map.put("code", 0);
            map.put("data", null);
            map.put("msg", "用户不存在");
        }
            return new ModelAndView(new MappingJackson2JsonView(), map);

    }


}
