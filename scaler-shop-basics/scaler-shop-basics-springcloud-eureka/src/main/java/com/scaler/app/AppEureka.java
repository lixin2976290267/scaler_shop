package com.scaler.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName AppEureka
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/15 10:51
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class AppEureka {
    public static void main(String[] args) {
        SpringApplication.run(AppEureka.class,args);
    }
}
