package com.cson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

/**
 * 本代码来自 Cspiration，由 @Cspiration 提供
 * - 讲师：Edward Shi
 * - 官方网站：https://cspiration.com
 * - 版权所有，禁止转发和分享
 */
@SpringBootApplication
@EnableEurekaServer
public class CsonEurekaApp {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(CsonEurekaApp.class, args);
    }
}
