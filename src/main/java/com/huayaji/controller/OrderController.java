package com.huayaji.controller;

import com.huayaji.entity.Order;

import com.huayaji.services.OrderService;
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
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall(){
        Map map = new HashMap();
        List<Order> orderAll = orderService.findAll();
        System.out.println();
        map.put("data",orderAll);
        map.put("code", 0);
        map.put("count", orderAll.size());
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(Order order){
        Map map = new HashMap();
        orderService.update(order);
        System.out.println("修改成功！！！！--update");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
