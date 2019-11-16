package com.scaler.zuul.handler.impl;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scaler.zuul.handler.BaseHandler;
import com.scaler.zuul.handler.GatewayHandler;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀限流
 * 
 * 
 * @description:
 */
@Component
@Slf4j
public class CurrentLimitHandler extends BaseHandler implements GatewayHandler {
	// 每秒时间存入令牌桶中Token为1个 1s/1r 写在分布式配置中心
	private RateLimiter rateLimiter = RateLimiter.create(1);
	@Override
	public Boolean service(RequestContext ctx, String ipAddres, HttpServletRequest request, HttpServletResponse response) {
		// 1.用户实现令牌桶限流
		log.info(">>>第一关API接口限流>>>>>");
		boolean tryAcquire = rateLimiter.tryAcquire(0, TimeUnit.SECONDS);
		if (!tryAcquire) {
			resultError(ctx, "The queue is too large, please try again later...");
			return Boolean.FALSE;
		}
		// 传递给下一个
		gatewayHandler.service(ctx, ipAddres, request, response);
		return Boolean.TRUE;
	}
}
