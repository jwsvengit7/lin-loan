package com.lin.linloanprofilemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LinLoanProfileModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinLoanProfileModuleApplication.class, args);
    }

}
