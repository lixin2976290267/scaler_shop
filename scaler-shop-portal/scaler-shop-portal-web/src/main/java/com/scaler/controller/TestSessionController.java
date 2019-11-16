package com.scaler.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * 
 * @description: Session使用
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
@Slf4j
public class TestSessionController {
	@Value("${server.port}")
	private String serverPort;

	// 创建session 会话
	@RequestMapping("/createSession")
	public String createSession(HttpServletRequest request, String nameValue) {
		HttpSession session = request.getSession();
		log.info(">>>>>>>存入Session,SessionId:{},SessionValue:{}", session.getId(), nameValue);
		session.setAttribute("name", nameValue);
		return "success" + ",端口号:" + serverPort;
	}

	// 获取session 会话
	@RequestMapping("/getSession")
	public Object getSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		log.info(">>>>>>>获取Session sessionid:{}", session.getId());
		Object value = session.getAttribute("name");
		return value + ",端口号:" + serverPort;
	}

}
