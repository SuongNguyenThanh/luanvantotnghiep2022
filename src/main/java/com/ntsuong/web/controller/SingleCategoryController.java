package com.ntsuong.web.controller;

import com.ntsuong.web.model.Category;
import com.ntsuong.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Slf4j
@Controller
public class SingleCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/single-category.do", "/danh-muc.do"})
    public String singleCategory(@RequestParam(name = "id") UUID id, Model model){
        return getSingleCategory(id, model);
    }

    @GetMapping({"/single-category/{slug}_{id}.do", "/danh-muc/{slug}_{id}.do"})
    public String singleCategoryVariable(@PathVariable(value = "slug") String slug,
                                         @PathVariable(value = "id") UUID id,
                                         Model model){
        return getSingleCategory(id, model);
    }

    /**
     * Get Single Category By Id
     * @param id UUID category
     * @param model adding attributes to the model
     * @return page name
     */
    private String getSingleCategory(UUID id, Model model) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            model.addAttribute("category", category);
            return "single-category";
        }else{
            return "redirect:/error/404";
        }
    }

}
