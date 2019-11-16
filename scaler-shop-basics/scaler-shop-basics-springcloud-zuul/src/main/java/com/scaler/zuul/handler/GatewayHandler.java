package com.scaler.zuul.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.context.RequestContext;

/**
 * 
 * 网关处理接口
 * 
 * 
 * @description:
 */
public interface GatewayHandler {
	/**
	 * 网关拦截处理请求
	 */
	Boolean service(RequestContext ctx, String ipAddres, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 设置下一个
	 */
	void setNextHandler(GatewayHandler gatewayHandler);

	/**
	 * 获取下一个Handler
	 * 
	 * @return
	 */
	public GatewayHandler getNextHandler();
}
