package com.SVO.TechTome.config;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class StarRenderer {

    public String render(BigDecimal rating) {
        if (rating == null) return "";
        int full  = rating.setScale(0, RoundingMode.DOWN).intValue();
        boolean half = rating.subtract(BigDecimal.valueOf(full))
                             .compareTo(new BigDecimal("0.25")) >= 0
                      && rating.subtract(BigDecimal.valueOf(full))
                             .compareTo(new BigDecimal("0.75")) < 0;
        int empty = 5 - full - (half ? 1 : 0);

        return "★".repeat(Math.max(0, full))
             + (half ? "½" : "")
             + "☆".repeat(Math.max(0, empty));
    }
}
