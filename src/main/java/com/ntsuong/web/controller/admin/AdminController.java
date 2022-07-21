package com.ntsuong.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('DASHBOARD_COMPONENT','OPEN_DASHBOARD')")
    public String home(Model model){
        return "redirect:/admin/index.do";
    }

    @RequestMapping(value = "/admin/index.do", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('DASHBOARD_COMPONENT','OPEN_DASHBOARD')")
    public String index(Model model){
        return "admin/index";
    }
}
