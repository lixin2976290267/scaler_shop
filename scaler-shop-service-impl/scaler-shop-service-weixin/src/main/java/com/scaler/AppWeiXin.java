package com.scaler;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName AppWeiXin
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/15 21:15
 * @Version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableSwagger2Doc
//@EnableApolloConfig
@EnableFeignClients
public class AppWeiXin {
    public static void main(String[] args) {
        SpringApplication.run(AppWeiXin.class,args);
    }
}
