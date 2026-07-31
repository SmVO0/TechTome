package com.SVO.TechTome.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "econt")
public class EcontConfig {

    private String apiUrl;
    private String username;
    private String password;
    private Sender sender = new Sender();

    @Data
    public static class Sender {
        private String name;
        private String phone;
        private String city;
    }
}
