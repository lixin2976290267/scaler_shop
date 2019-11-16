package com.scaler.pay.mapper.entity;

import java.util.Date;

import lombok.Data;

/**
 * 日志存储
 * 
 * 
 * @description:
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Data
public class PaymentTransactionLogEntity {
	/** 主键ID */

	private Integer id;
	/** 同步回调日志 */
	private String synchLog;
	/** 异步回调日志 */
	private String asyncLog;
	/** 支付渠道ID */
	private Integer channelId;
	/** 支付交易ID */
	private String transactionId;
	/** 乐观锁 */
	private Integer revision;
	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	private Date createdTime;
	/** 更新人 */
	private String updatedBy;
	/** 更新时间 */
	private Date updatedTime;
	/**  */
	private String untitled;
}
