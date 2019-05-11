package com.huayaji.controller;

import com.huayaji.entity.Distribute;
import com.huayaji.entity.Order;
import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
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
import java.util.*;

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
            if(d.getDistributeBalance()>0)
            {
                Date d1=new Date();
                Date d2=d.getDistributeTime();
                long days= 0;
                try {
                     days=getDaysBetweenTwoDates(d1,d2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(days%Double.parseDouble(d.getDistributeTimeType())==0)
                {
                    Order o=orderService.findByUseridAndProductid(d.getUser().getId().toString(),d.getProduct().getId().toString());
                    TemporarySing t=new TemporarySing();
                    t.setDistribute_data(d.getDistributeCountPer());
                    t.setDistribute_operation(o.getDistributeType()==1?"提货点自提":"送货上门");
                    t.setDistribute_time(new java.sql.Date(d.getDistributeTime().getTime()));
                    t.setUser(d.getUser());
                    t.setProduct(d.getProduct());
                    t.setReceive_operation(o.getDistributeType()==1?"提货点自提":"送货上门");
                    singService.saveTemporary(t);
                }
            }

        }
        List<TemporarySing> sings=singService.findTemporaryAll();

        map.put("data",sings);
        map.put("code", 0);
        map.put("count", sings.size());
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    public static long getDaysBetweenTwoDates(Date a, Date b) throws Exception {
        //判断这两个时间的大小
        if(a.equals(b)) return 0;
        if(!a.before(b)){//保证返回的值为正数
            Date temp;
            temp=a;
            a=b;
            b=temp;
        }
        Calendar c = Calendar.getInstance();//获取calendar对像
        c.setTime(a);//设置时间 date  转 calendar 类型
        long t1 = c.getTimeInMillis();//获取时间戳
        c.setTime(b);
        long t2 = c.getTimeInMillis();
        //计算天数
        long days = (t2 - t1) / (24 * 60 * 60 * 1000);
        return days;
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
