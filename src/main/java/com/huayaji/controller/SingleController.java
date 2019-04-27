package com.huayaji.controller;

import com.huayaji.entity.Sing;
import com.huayaji.services.SingService;
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
@RequestMapping("/single")
public class SingleController {
    private static final Logger logger = Logger.getLogger(SingleController.class);

    @Resource
    private SingService singService;
    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall(){
        Map map = new HashMap();
        List<Sing> singAll = singService.findAll();
        System.out.println();
        map.put("data",singAll);
        map.put("code", 0);
        map.put("count", singAll.size());
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(Sing sing){
        Map map = new HashMap();
        singService.update(sing);
        logger.info("更新成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delete(Sing sing){
        Map map = new HashMap();
        singService.delete((long) sing.getId());
        logger.info("删除成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "删除成功！");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView save(Sing sing){
        Map map = new HashMap();
        singService.save(sing);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
