package com.scaler.zuul.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scaler.zuul.handler.factory.FactoryHandler;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

/**
 * 责任链模式执行
 * 
 * 
 * @description:
 */
@Component
public class ResponsibilityClient {
	public void responsibility(RequestContext ctx, String ipAddres, HttpServletRequest request,
			HttpServletResponse response) {
		GatewayHandler handler = FactoryHandler.getHandler();
		handler.service(ctx, ipAddres, request, response);
	}

}
