package com.SVO.TechTome.web;

import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.services.StoreItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/item")
public class StoreItemController {

    private final StoreItemService storeItemService;

    public StoreItemController(StoreItemService storeItemService) {
        this.storeItemService = storeItemService;
    }

    @GetMapping("/{id}")
    public ModelAndView getItemPage(@PathVariable UUID id) {
        StoreItem item = storeItemService.getById(id);

        ModelAndView mav = new ModelAndView("item");
        mav.addObject("item", item);
        return mav;
    }
}
