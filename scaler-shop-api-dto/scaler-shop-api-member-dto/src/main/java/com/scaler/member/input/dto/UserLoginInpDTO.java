package com.scaler.member.input.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * 
 * @description: 用户登陆请求参数
 */
@Data
@ApiModel(value = "用户登陆参数")
public class UserLoginInpDTO {
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 登陆类型 PC、Android 、IOS
	 */
	@ApiModelProperty(value = "登陆类型")
	private String loginType;
	/**
	 * 设备信息
	 */
	@ApiModelProperty(value = "设备信息")
	private String deviceInfor;
	// 与swagger 接口文档对称

	@ApiModelProperty(value = "腾讯开发id")
	private String qqOpenId;

}
