package com.scaler.zuul.handler.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scaler.core.sign.SignUtil;
import com.scaler.zuul.handler.BaseHandler;
import com.scaler.zuul.handler.GatewayHandler;
import org.springframework.stereotype.Component;


import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 参数验证
 * 
 * 
 * @description:

 */
@Component
@Slf4j
public class ToVerifyMapHandler extends BaseHandler implements GatewayHandler {

	@Override
	public Boolean service(RequestContext ctx, String ipAddres, HttpServletRequest request,
			HttpServletResponse response) {
		log.info(">>>>>>>>>拦截3 参数验证<<<<<<<<<<<<<<<<<<<<<<<<");
		Map<String, String> verifyMap = SignUtil.toVerifyMap(request.getParameterMap(), false);
		if (!SignUtil.verify(verifyMap)) {
			resultError(ctx, "ip:" + ipAddres + ",Sign fail");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
