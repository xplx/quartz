package com.example.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
        System.out.println("【【【【【【 简单Quartz-Config-Cluster微服务 】】】】】】已启动.");
    }
}
