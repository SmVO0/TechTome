package com.SVO.TechTome.web;

import com.SVO.TechTome.services.EcontCityService;
import com.SVO.TechTome.services.EcontOfficeService;
import com.SVO.TechTome.services.EcontStreetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/econt")
public class EcontNomenclaturesController {

    private final EcontCityService econtCityService;
    private final EcontOfficeService econtOfficeService;
    private final EcontStreetService econtStreetService;

    public EcontNomenclaturesController(EcontCityService econtCityService,
                                        EcontOfficeService econtOfficeService,
                                        EcontStreetService econtStreetService) {
        this.econtCityService = econtCityService;
        this.econtOfficeService = econtOfficeService;
        this.econtStreetService = econtStreetService;
    }

    @GetMapping("/cities")
    public List<EcontCityService.CityOption> searchCities(
            @RequestParam(defaultValue = "") String q) {
        return econtCityService.search(q);
    }

    @GetMapping("/offices")
    public List<EcontOfficeService.OfficeOption> searchOffices(
            @RequestParam(defaultValue = "") String q) {
        return econtOfficeService.search(q);
    }

    @GetMapping("/streets")
    public List<String> searchStreets(
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String q) {
        return econtStreetService.search(city, q);
    }

    @GetMapping("/streets/preload")
    public void preloadStreets(@RequestParam(defaultValue = "") String city) {
        econtStreetService.preload(city);
    }
}
