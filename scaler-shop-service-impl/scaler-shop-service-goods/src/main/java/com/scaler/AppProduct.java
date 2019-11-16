package com.scaler;

import com.scaler.elk.aop.AopLogAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = { "com.scaler.product.es" })
@EnableAspectJAutoProxy
public class AppProduct {

	@Bean
	public AopLogAspect aopLogAspect(){
		return new AopLogAspect();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppProduct.class, args);
	}

}
