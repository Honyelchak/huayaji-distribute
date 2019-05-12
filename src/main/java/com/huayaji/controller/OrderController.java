package com.huayaji.controller;

import com.huayaji.entity.Order;

import com.huayaji.entity.Product;
import com.huayaji.services.OrderService;
import com.huayaji.services.ProductService;
import com.huayaji.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;
    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall(Integer page,Integer limit, String search){
        Map map = new HashMap();
        List<Order> orderAll = orderService.findByPage(page,limit,search);
        long total =orderService.getCount(search);
        int pages= (int) (total%limit==0?total/limit:total/limit+1);
        map.put("count",total);
        map.put("curnum",page);
        map.put("limit",limit);
        map.put("data",orderAll);
        map.put("code", 0);
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView update(String id ,String totalMoney,String distributeTime,String distributeType,String count){
        Map map = new HashMap();
       orderService.update(id,totalMoney,distributeTime,distributeType,count);
        logger.info("更新成功！");
        map.put("res", "ok");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delete(Order order){
        Map map = new HashMap();
        orderService.delete(order.getId());
        logger.info("删除成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "删除成功！");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView save(String userId,String productId,String totalMoney,String count,String orderTime,String distributeTime,String distributeType){
       Order order =new Order();
        order.setUser(userService.findById(Long.parseLong(userId)));
        order.setProduct(productService.findById(Long.parseLong(productId)));
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            order.setOrderTime(new Timestamp( format.parse(orderTime).getTime()));
            order.setDistributeTime(new Timestamp( format.parse(distributeTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.setCount(Integer.parseInt(count));
        order.setTotalMoney(Double.parseDouble(totalMoney));
        order.setDistributeType(Integer.parseInt(distributeType));
        Map map = new HashMap();
        orderService.save(order);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
