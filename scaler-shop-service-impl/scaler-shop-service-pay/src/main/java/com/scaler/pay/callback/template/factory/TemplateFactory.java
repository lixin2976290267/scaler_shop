package com.scaler.pay.callback.template.factory;


import com.scaler.core.utils.SpringContextUtil;
import com.scaler.pay.callback.template.AbstractPayCallbackTemplate;

/**
 * 获取具体实现的模版工厂方案
 * 
 * 
 * @description:
 */
public class TemplateFactory {

	public static AbstractPayCallbackTemplate getPayCallbackTemplate(String beanId) {
		return (AbstractPayCallbackTemplate) SpringContextUtil.getBean(beanId);
	}

}
