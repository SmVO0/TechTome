package com.SVO.TechTome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableFeignClients
public class TechTomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechTomeApplication.class, args);
	}

}
