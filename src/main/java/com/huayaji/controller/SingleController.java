package com.huayaji.controller;

import com.huayaji.entity.Distribute;
import com.huayaji.entity.Sing;
import com.huayaji.services.DistributeService;
import com.huayaji.services.OrderService;
import com.huayaji.services.SingService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/single")
public class SingleController {
    private static final Logger logger = Logger.getLogger(SingleController.class);

    @Resource
    private SingService singService;
    @Resource
    private OrderService orderService;
    @Resource
    private DistributeService distributeService;
    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall() throws ParseException {
        Map map = new HashMap();
        List<Distribute> distributes = distributeService.findAll();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(Distribute d:distributes)
        {
            String begin=d.getDistributeTime().getYear()+"-"+d.getDistributeTime().getMonth()+"-"+d.getDistributeTime().getDay();
            Date d1=new Date();
            String now=d1.getYear()+"-"+d1.getMonth()+"-"+d1.getMonth();
            Date fDate=sdf.parse(begin);
            Date oDate=sdf.parse(now);
            if(oDate.getTime()-fDate.getTime()%(double)(d.getDistributeTimeType()*24 * 60 * 60 * 1000)<=(24 * 60 * 60 * 1000))
            {
                Sing s=new Sing();
                s.setUser(d.getUser());
                s.setProduct(d.getProduct());
                s.setDistribute_data(d.getDistributeCountPer());
                s.setDistribute_operation("1");
                s.setDistribute_status(0);
                s.setDistribute_time((java.sql.Date) new Date(d.getDistributeTime().getTime()));
                s.setReceive_operation("1");
                singService.save(s);
            }
        }
        List<Sing> sings=singService.findAll();

        map.put("data",sings);
        map.put("code", 0);
        map.put("count", sings.size());
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
