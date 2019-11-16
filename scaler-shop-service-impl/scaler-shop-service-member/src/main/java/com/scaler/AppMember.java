package com.scaler;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName AppWeiXin
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/15 11:03
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
//@EnableApolloConfig
@MapperScan("com.scaler.member.mapper")
public class AppMember {
    public static void main(String[] args) {
        SpringApplication.run(AppMember.class,args);
    }
}
