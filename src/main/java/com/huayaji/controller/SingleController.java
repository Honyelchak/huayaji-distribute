package com.huayaji.controller;

import com.huayaji.dao.UserDaoImpl;
import com.huayaji.entity.*;
import com.huayaji.services.DistributeService;
import com.huayaji.services.OrderService;
import com.huayaji.services.SingService;
import com.huayaji.util.TExcel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
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
    public ModelAndView getall(Integer page,Integer limit, String search) throws ParseException {
        Map map = new HashMap();
        List<Distribute> distributes = distributeService.findAll();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        singService.deleteAllTem();
        for(Distribute d:distributes)
        {
            if(d.getDistributeBalance()>0)
            {
                Date d1=new Date();
                d1=sdf.parse(sdf.format(d1));
                Date d2=null;
                if(d.getDistributeLastTime()!=null) {
                    d2= d.getDistributeLastTime();
                }
                else
                    d2=d.getDistributeTime();
                long days= 0;
                try {
                     days=getDaysBetweenTwoDates(d1,d2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(days%Double.parseDouble(d.getDistributeTimeType())==0)
                {
                    Order o=orderService.findByUseridAndProductid(d.getUser().getId(),d.getProduct().getId());
                    TemporarySing t=new TemporarySing();
                    t.setDistribute_data(d.getDistributeCountPer());
                    if(o!=null) {
                        t.setDistribute_operation(o.getDistributeType() == 1 ? "提货点自提" : "送货上门");
                        t.setReceive_operation(o.getDistributeType()==1?"提货点自提":"送货上门");
                    }
                    else
                    {
                        t.setDistribute_operation("送货上门");
                        t.setReceive_operation("送货上门");
                    }
                    t.setDistribute_time(new java.sql.Date(d.getDistributeTime().getTime()));
                    t.setUser(d.getUser());
                    t.setProduct(d.getProduct());

                    if(singService.findByUseridAndProductidAnddate(t)==null)
                    {
                        singService.saveTemporary(t);
                    }

                }
            }

        }
        List<Sing> sings=new ArrayList<Sing>();
        List<TemporarySing> temsings=singService.findTemporaryByPage(page,limit,search);
        if(temsings!=null&&temsings.size()>0) {
            for (TemporarySing t : temsings
                    ) {
                sings.add(new Sing(t));
            }
        }
        sings.addAll(singService.findByPage(page,limit,search));
        long total =singService.getCount(search);
        int pages= (int) (total%limit==0?total/limit:total/limit+1);
        map.put("count",total);
        map.put("curnum",page);
        map.put("data",sings);
        map.put("code", 0);
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
    public ModelAndView save(String  id,String distributeTime,String userid,String productid){
        Map map = new HashMap();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Distribute distribute=distributeService.findByUseridAndProduct(userid,productid);
        try {
            distribute.setDistributeTime(new Timestamp(sdf.parse(distributeTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        distributeService.update(distribute);
        Sing sing =new Sing(singService.findTemporaryById(Long.parseLong(id)));
        sing.setDistribute_status(1);
       singService.save(sing);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @ResponseBody
    @RequestMapping(value = "/export",consumes = "application/json;charset=utf-8")
    public void export (HttpServletResponse response){
        OutputStream out = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String name = "产品待配送表" + sdf.format(new Date());
        Map map = new HashMap();
        List<TemporarySing> list = singService.findDilivering();
        logger.info(list);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("data", list);
        map.put("msg", "添加成功");
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(name, "UTF-8")+".xlsx");
            out = response.getOutputStream();
            /*TExcel.exportExcelMutilEntity(name,,{},out);*/
            TExcel.exportExcel(name,TemporarySing.class,list,out);
            System.out.println("下载ok");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }

}
