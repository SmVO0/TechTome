package com.SVO.TechTome.web;

import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false, defaultValue = "") String q,
                               @RequestParam(defaultValue = "0") int page) {
        Page<StoreItem> results = searchService.search(q, page);

        ModelAndView mav = new ModelAndView("search");
        mav.addObject("query", q);
        mav.addObject("results", results);
        mav.addObject("currentPage", page);
        return mav;
    }
}
