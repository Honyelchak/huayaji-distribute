package com.huayaji.controller;

import com.huayaji.entity.Distribute;
import com.huayaji.entity.User;
import com.huayaji.services.DistributeService;
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
@RequestMapping("/Distribute")
public class DistributeController {
    private static final Logger logger = Logger.getLogger(DistributeController.class);

    @Resource
    private DistributeService distributeService;
    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall(){
        Map map = new HashMap();
        List<Distribute> orderAll = distributeService.findAll();
        System.out.println();
        map.put("data",orderAll);
        map.put("code", 0);
        map.put("count", orderAll.size());
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(Distribute order){
        Map map = new HashMap();
        distributeService.update(order);
        logger.info("更新成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delete(Distribute order){
        Map map = new HashMap();
        distributeService.delete(order.getId());
        logger.info("删除成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "删除成功！");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView save(Distribute distribute){
        Map map = new HashMap();
        distributeService.add(distribute);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
