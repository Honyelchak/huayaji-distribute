package com.huayaji.controller;

import com.huayaji.entity.Admin;
import com.huayaji.entity.Distribute;
import com.huayaji.entity.Order;
import com.huayaji.entity.TemporarySing;
import com.huayaji.services.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;
    @Resource
    private OrderService orderService;
    @Resource
    private DistributeService distributeService;
    @Resource
    private SingService singService;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public ModelAndView login(Admin admin){
        Map map = new HashMap();
        if (adminService.login(admin.getUserName(), admin.getPassWord()) != null) {
            map.put("code", 0);
            map.put("msg", "登录成功");
            return new ModelAndView("index.html");
        } else {
            map.put("code", -1);
            map.put("msg", "登录失败");
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }


    @RequestMapping("/register")
    public ModelAndView register(Admin admin){
        Map map = new HashMap();
        if (adminService.findByUserName(admin.getUserName()) == null) {
            adminService.save(admin);
            map.put("code", 0);
            map.put("msg", "登录成功");
            return new ModelAndView("page/login/login");
        } else {
            map.put("code", -1);
            map.put("msg", "用户名重复");
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }
    @RequestMapping("/getcount")
    public ModelAndView getCount() {
        Map map = new HashMap();
        int orderNum = (int) orderService.getCount(null);
        int distributeNUm = (int) distributeService.getCount(null);
        int temSingNum=0;
        List<Distribute> distributes = distributeService.findAll();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        singService.deleteAllTem();
        for(Distribute d:distributes)
        {
            if(d.getDistributeBalance()>0)
            {
                Date d1=new Date();
                try {
                    d1=sdf.parse(sdf.format(d1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date d2=null;
                if(d.getDistributeLastTime()!=null) {
                    d2= d.getDistributeLastTime();
                }
                else
                    d2=d.getDistributeTime();
                long days= 0;
                try {
                    days=SingleController.getDaysBetweenTwoDates(d1,d2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(days%Double.parseDouble(d.getDistributeTimeType())==0)
                {
                    temSingNum++;

                }
            }

        }
        int singleNum= (int) singService.getCount(null);
        int productNum= (int) productService.getCount(null);
        int userNum= (int) userService.getCount(null);
        map.put("orderNum",orderNum);
        map.put("distributeNum",distributeNUm);
        map.put("TemsingleNum",temSingNum);
        map.put("singleNum",singleNum);
        map.put("productNum",productNum);
        map.put("userNum",userNum);
        map.put("code", 0);
        map.put("msg", "查找成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);

    }
}
