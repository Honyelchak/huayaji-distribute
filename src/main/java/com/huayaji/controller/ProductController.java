package com.huayaji.controller;

import com.huayaji.entity.Product;
import com.huayaji.entity.User;
import com.huayaji.services.ProductService;

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
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Resource
    private ProductService productService;

    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView hello(){
        Map map = new HashMap();
        List<Product> productList = productService.findAll();

        map.put("data",productList);
        map.put("code", 0);
        map.put("count", productList.size());
        map.put("msg", null);
        //User user= userService.findById(1L);
        //System.out.println(user.toString());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView update(Product product){
        Map map = new HashMap();
        productService.update(product);
        System.out.println("修改成功！！！！--update");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView delete(Product product){
        Map map = new HashMap();
        productService.delete(product.getId());
        System.out.println("删除成功！！！！--delete");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", null);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView save(Product product){
        Map map = new HashMap();
        productService.save(product);
        logger.info("添加成功！");
        map.put("res", "ok");
        map.put("code", 0);
        map.put("msg", "添加成功");
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
