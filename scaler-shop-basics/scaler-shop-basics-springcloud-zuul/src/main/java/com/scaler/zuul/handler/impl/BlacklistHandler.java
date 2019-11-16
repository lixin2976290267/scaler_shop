package com.scaler.zuul.handler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scaler.zuul.handler.BaseHandler;
import com.scaler.zuul.handler.GatewayHandler;
import com.scaler.zuul.mapper.BlacklistMapper;
import com.scaler.zuul.mapper.entity.ScalerBlacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 黑名单Handler
 * 
 * 
 * @description:

 */
@Component
@Slf4j
public class BlacklistHandler extends BaseHandler implements GatewayHandler {

	@Autowired
	private BlacklistMapper blacklistMapper;

	@Override
	public Boolean service(RequestContext ctx, String ipAddres, HttpServletRequest request,
			HttpServletResponse response) {
		// >>>>>>>>>>>>>黑名单拦截操作<<<<<<<<<<<<<<<<<<<
		log.info(">>>>>>>>>拦截1 黑名单拦截 ipAddres:{}<<<<<<<<<<<<<<<<<<<<<<<<<<", ipAddres);
		ScalerBlacklist meiteBlacklist = blacklistMapper.findBlacklist(ipAddres);
		if (meiteBlacklist != null) {
			resultError(ctx, "ip:" + ipAddres + ",Insufficient access rights");
			return Boolean.FALSE;
		}
		// 传递给下一个
		gatewayHandler.service(ctx, ipAddres, request, response);
		return Boolean.TRUE;
	}

}
