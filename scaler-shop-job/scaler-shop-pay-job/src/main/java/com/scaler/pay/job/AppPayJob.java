package com.scaler.pay.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 支付服务任务调度
 * 
 * 
 * @description:
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class AppPayJob {

	public static void main(String[] args) {
		SpringApplication.run(AppPayJob.class, args);
	}

}
