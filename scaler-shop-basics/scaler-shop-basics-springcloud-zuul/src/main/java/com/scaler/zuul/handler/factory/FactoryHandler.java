package com.scaler.zuul.handler.factory;


import com.scaler.core.utils.SpringContextUtil;
import com.scaler.zuul.handler.GatewayHandler;

/**
 * 工厂Handler
 *
 * @description:
 */
public class FactoryHandler {

    public static GatewayHandler getHandler() {
        // 1.限流拦截
        GatewayHandler handler0 = (GatewayHandler) SpringContextUtil.getBean("currentLimitHandler");
        // 1.黑名单拦截
        GatewayHandler handler1 = (GatewayHandler) SpringContextUtil.getBean("blacklistHandler");
        handler0.setNextHandler(handler1);
        // 2.验证accessTokenc
        GatewayHandler handler2 = (GatewayHandler) SpringContextUtil.getBean("apiAuthorityHandler");
        handler1.setNextHandler(handler2);
        // 3.API接口参数接口验签
        GatewayHandler handler3 = (GatewayHandler) SpringContextUtil.getBean("toVerifyMapHandler");
        handler2.setNextHandler(handler3);
        return handler0;
    }

}
