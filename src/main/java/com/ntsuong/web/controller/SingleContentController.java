package com.ntsuong.web.controller;

import com.ntsuong.web.service.ContentService;
import com.ntsuong.web.model.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
public class SingleContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping({"/single-content.do", "/bai-viet.do"})
    public String singleContent(@RequestParam(name = "id")UUID id, Model model){
        return getSingleContent(id, model);
    }

    @GetMapping({"/single-content/{slug}_{id}.do", "/bai-viet/{slug}_{id}.do"})
    public String singleContentVariable(@PathVariable(value = "slug") String slug,
                                @PathVariable(value = "id") UUID id,
                                        Model model){
        return getSingleContent(id, model);

    }

    private String getSingleContent(UUID id, Model model) {
        Optional<Content> contentOptional = contentService.findById(id);
        if(contentOptional.isPresent()){
            Content content = contentOptional.get();
            model.addAttribute("content", content);
            return "single-content";
        }else{
            return "redirect:/error/404";
        }
    }
}
