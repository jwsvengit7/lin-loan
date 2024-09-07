package com.lin.linloanauthmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lin.linloanauthmodule","com.lin.commons.helpers"})
public class LinLoanAuthModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinLoanAuthModuleApplication.class, args);
    }

}
