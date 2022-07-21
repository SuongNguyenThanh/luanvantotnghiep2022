package com.ntsuong.web.controller;

import com.ntsuong.web.dto.request.dynatable.DynaTableRequest;
import com.ntsuong.web.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Slf4j
@Controller
public class SearchContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping({"/search-content.do", "/tim-kiem.do"})
    public String searchContent(@RequestParam(name = "search", defaultValue = "") String search, Model model){
        Map<String, String> queries = new HashMap<>();
        queries.put("search", search);
        DynaTableRequest request = new DynaTableRequest();
        request.setQueries(queries);
        Long countSearch = contentService.countSearchContent(request);

        model.addAttribute("search", search);
        model.addAttribute("countSearch", countSearch);
        return "search-content";
    }

    @GetMapping({"/search-content/{search}.do", "/tim-kiem/{search}.do"})
    public String searchContentVariable(
            @PathVariable(value = "search") String search,
                                         Model model){
        Map<String, String> queries = new HashMap<>();
        queries.put("search", search);
        DynaTableRequest request = new DynaTableRequest();
        request.setQueries(queries);
        Long countSearch = contentService.countSearchContent(request);

        model.addAttribute("search", search);
        model.addAttribute("countSearch", countSearch);
        return "search-content";
    }

}
