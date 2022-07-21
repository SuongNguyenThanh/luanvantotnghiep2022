package com.ntsuong.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Slf4j
@Controller
public class ErrorController {

    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public String error404(Model model){

        return "error/404";
    }

    @RequestMapping(value = "/error/403", method = RequestMethod.GET)
    public String error403(Model model){

        return "error/403";
    }

    @RequestMapping(value = "/error/500", method = RequestMethod.GET)
    public String error500(Model model){

        return "error/500";
    }
}
