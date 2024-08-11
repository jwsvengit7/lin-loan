package com.lin.linloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LinLoanDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinLoanDiscoveryApplication.class, args);
    }

}
