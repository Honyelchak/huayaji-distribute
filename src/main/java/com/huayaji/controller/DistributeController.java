package com.huayaji.controller;

import com.huayaji.entity.Distribute;
import com.huayaji.entity.User;
import com.huayaji.services.DistributeService;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/distribute")
public class DistributeController {
    private static final Logger logger = Logger.getLogger(DistributeController.class);

    @Resource
    private DistributeService distributeService;
    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;
    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getall(Integer page,Integer limit, String search){
        Map map = new HashMap();
        List<Distribute> orderAll = distributeService.findByPage(page,limit,search);
        long total=distributeService.getCount(search);
        int pages= (int) (total%limit==0?total/limit:total/limit+1);
        map.put("count",total);
        map.put("curnum",page);
        map.put("limit",limit);
        map.put("data",orderAll);
        map.put("code", 0);
        map.put("msg", null);

        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update( String id,String distributeLastTime,String distributeTime,String distributeTimeType,String distributeCountPer,String distributeBalance,String comment){
        Map map = new HashMap();
        Distribute distribute=distributeService.findById(Long.parseLong(id));
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            distribute.setDistributeTime(new Timestamp(format.parse(distributeTime).getTime()));
            distribute.setDistributeLastTime(new Timestamp(format.parse(distributeLastTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        distribute.setComment(comment);
        distribute.setDistributeBalance(Double.parseDouble(distributeBalance));
        distribute.setDistributeCountPer(Integer.parseInt(distributeCountPer));
        distribute.setDistributeTimeType(distributeTimeType);

        distributeService.update(distribute);
        logger.info("更新成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    /**
     * 推迟天数和修改配送数量
     *      * @param days   推遲天數
     * @param userid    (phone)
     * @param distributeCountPer   每次配送数量
     * @return
     */
    @RequestMapping(value = "/delay", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delay(String days,String userid,String distributeCountPer){
        Map map = new HashMap();
        distributeService.update( days,userid,distributeCountPer);
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
    public ModelAndView save(String distributeTime,String distributeTimeType,String distributeCountPer,String distributeBalance,String comment,String userId,String productId){
        Distribute distribute=new Distribute();
        distribute.setUser(userService.findById(Long.parseLong(userId)));
        distribute.setProduct(productService.findById( Long.parseLong(productId)));
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            distribute.setDistributeTime(new Timestamp(format.parse(distributeTime).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        distribute.setComment(comment);
        distribute.setDistributeCountPer(Integer.parseInt(distributeCountPer));
        distribute.setDistributeBalance(Double.parseDouble(distributeBalance));
        distribute.setDistributeTimeType(distributeTimeType);
        Map map = new HashMap();
        distributeService.save(distribute);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
