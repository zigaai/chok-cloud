package com.zigaai.upms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.zigaai.common.security.feign")
@EnableDiscoveryClient
@SpringBootApplication
// @MapperScan(basePackages = "com.zigaai.upms.mapper")
public class UpmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }
}
