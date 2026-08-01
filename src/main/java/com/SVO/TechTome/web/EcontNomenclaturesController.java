package com.SVO.TechTome.web;

import com.SVO.TechTome.services.EcontCityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/econt")
public class EcontNomenclaturesController {

    private final EcontCityService econtCityService;

    public EcontNomenclaturesController(EcontCityService econtCityService) {
        this.econtCityService = econtCityService;
    }

    @GetMapping("/cities")
    public List<EcontCityService.CityOption> searchCities(
            @RequestParam(defaultValue = "") String q) {
        return econtCityService.search(q);
    }
}
