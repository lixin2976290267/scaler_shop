package com.scaler.weixin.mp.handler;



import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.utils.RedisUtil;
import com.scaler.member.output.dto.UserOutDTO;
import com.scaler.weixin.feign.MemberServiceFeign;
import com.scaler.core.utils.RegexUtils;
import com.scaler.weixin.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {
	// 用户发送手机验证码提示
	@Value("${scaler.weixin.registration.code.message}")
	private String registrationCodeMessage;
	// 默认用户发送验证码提示
	@Value("${scaler.weixin.default.registration.code.message}")
	private String defaultRegistrationCodeMessage;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private MemberServiceFeign memberServiceFeign;
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
									WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		//1.获得微信客户端发送的消息
		String fromContent = wxMessage.getContent();
		//2.使用正则表达式验证消息是否为手机号码
		if (RegexUtils.checkMobile(fromContent)){
			//1.根据手机号码调用会员服务接口查询用户信息是否存在
			BaseResponse<UserOutDTO> resultInfo = memberServiceFeign.existMobile(fromContent);
			if (Constants.HTTP_RES_CODE_200.equals(resultInfo.getCode())){
				return new TextBuilder().build("该手机号码"+fromContent+"已经存在！", wxMessage, weixinService);
			}
			//返回码必须是203
			if (!resultInfo.getCode().equals(Constants.HTTP_RES_CODE_EXISTMOBILE_203)){
				new TextBuilder().build(resultInfo.getMsg(), wxMessage, weixinService);
			}
			//3.如果是手机号码随机生成4位数注册码
			int registCode=registCode();
			String content=String.format(registrationCodeMessage,registCode);
			//将注册码存在redis中 key为手机号码  value为weixinCODE
			redisUtil.setString(Constants.WEIXINCODE_KEY+fromContent,registCode+"",Constants.WEIXINCODE_TIMEOUT);
			//将weixinCODE返回给用户
			return new TextBuilder().build(content, wxMessage, weixinService);
		}

		log.info("fromContent:" + fromContent);
		// TODO 组装回复消息
		// String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
		//如果不是手机号码 则返回默认恢复内容
		return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);

	}
	// 随机数  生成注册码
	private int registCode() {
		int registCode = (int) (Math.random() * 9000 + 1000);
		return registCode;
	}


}