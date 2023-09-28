package com.study.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MonitorCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorCenterApplication.class, args);
	}

}
