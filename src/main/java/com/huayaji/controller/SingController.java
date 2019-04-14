package com.huayaji.controller;

import com.huayaji.entity.Sing;
import com.huayaji.entity.User;
import com.huayaji.services.SingService;
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
@RequestMapping("/Sing")
public class SingController {
    private static final Logger logger = Logger.getLogger(SingController.class);

    @Resource
    private SingService singService;

    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView hello(){
        Map map = new HashMap();
        List<Sing> singAll = singService.findAll();

        map.put("data",singAll);
        map.put("code", 0);
        map.put("count", singAll.size());
        map.put("msg", null);
        //User user= userService.findById(1L);
        //System.out.println(user.toString());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
