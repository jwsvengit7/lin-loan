package com.lin.lintransactionmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootConfiguration
@EnableDiscoveryClient
public class LinTransactionModuleApplication {
    public static void main(String [] args){
        SpringApplication.run(LinTransactionModuleApplication.class,args);
    }
}
