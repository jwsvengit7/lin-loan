package com.lin.loanservicemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@ComponentScan(basePackages = {"com.lin.loanservicemodule","com.lin.commons","com.lin.commonsshared.jwt"})
public class LoanServiceModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceModuleApplication.class, args);
    }

}
