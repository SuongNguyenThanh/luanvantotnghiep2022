package com.ntsuong.web.controller;

import com.ntsuong.web.model.Menu;
import com.ntsuong.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
public class SingleMenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping({"/single-menu.do", "/the-loai.do"})
    public String singleMenu(@RequestParam(name = "id")UUID id, Model model){
        Optional<Menu> menuOptional = menuService.findById(id);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            model.addAttribute("menu", menu);
        }else{
            return "redirect:/error/404";
        }
        return "single-menu";
    }

    @GetMapping({"/single-menu/{slug}_{id}.do","/the-loai/{slug}_{id}.do"})
    public String singleMenuVariable(@PathVariable(value = "slug") String slug,
                                     @PathVariable(value = "id") UUID id,
                                     Model model){

        Optional<Menu> menuOptional = menuService.findById(id);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            model.addAttribute("menu", menu);
            return "single-menu";
        }else {
            return "redirect:/error/404";
        }
    }
}
