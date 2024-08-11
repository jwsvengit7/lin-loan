package com.lin.linloannotificationmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LinLoanNotificationModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinLoanNotificationModuleApplication.class, args);
	}

}
