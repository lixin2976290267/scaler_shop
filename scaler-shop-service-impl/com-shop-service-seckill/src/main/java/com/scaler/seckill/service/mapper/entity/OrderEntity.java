package com.scaler.seckill.service.mapper.entity;

import java.util.Date;

import lombok.Data;

/**
 * 秒杀成功订单
 * 
 * 
 * @description:
 */
@Data
public class OrderEntity {

	/**
	 * 库存id
	 * 
	 */
	private Long seckillId;
	/**
	 * 用户手机号码
	 */
	private String userPhone;
	/**
	 * 订单状态
	 */
	private Long state;
	/**
	 * 创建时间
	 */
	private Date createTime;
}
