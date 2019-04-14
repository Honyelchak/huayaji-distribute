package com.huayaji.controller;


import com.huayaji.services.AddressService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/Address")
public class AddressController {
    private static final Logger logger = Logger.getLogger(AddressController.class);

    @Resource
    private AddressService addressService;
}
