package com.huayaji.controller;

import com.huayaji.entity.Distribute;
import com.huayaji.services.DistributeService;
import com.huayaji.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WeChatController {

    @Resource
    private UserService userService;
    @Resource
    private DistributeService distributeService;

    @RequestMapping(value = "/findByUserid", method = RequestMethod.GET)
    public ModelAndView findByUserid(Long id) {
        Map map = new HashMap();

        String stringid = String.valueOf(id);
        List<Distribute> list = distributeService.findByUserid(stringid);
        map.put("list", list);
//        System.out.println("数据++++------"+list);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
