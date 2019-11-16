package com.scaler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.scaler.seckill.service.mapper")
@EnableAsync
@EnableHystrix
public class AppSpike {

	public static void main(String[] args) {
		SpringApplication.run(AppSpike.class, args);
	}

}
