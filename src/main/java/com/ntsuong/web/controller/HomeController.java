package com.ntsuong.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Slf4j
@Controller
public class HomeController {

    @RequestMapping({"/","/home", "/trang-chu"})
    public  String index(Model model){

        return "index";
    }


}
