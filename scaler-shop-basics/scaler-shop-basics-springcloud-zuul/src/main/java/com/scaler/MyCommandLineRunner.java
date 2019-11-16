//package com.scaler;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.ConfigChangeListener;
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//
////端口号更改需要重启生效
//@Component
//@Slf4j
//public class MyCommandLineRunner implements CommandLineRunner {
//	@ApolloConfig
//	private Config config;
//
//	@Override
//	public void run(String... args) throws Exception {
//		//Apollo分布式配置中心方法监听
//		config.addChangeListener(new ConfigChangeListener() {
//
//			@Override  //一旦配置文件发生变化都会走这个方法
//			public void onChange(ConfigChangeEvent changeEvent) {
//				log.info("####分布式配置中心监听#####" + changeEvent.changedKeys().toString());
//			}
//		});
//	}
//
//}
