package com.scaler.seckill.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.scaler.api.seckill.service.SpikeCommodityService;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.core.token.GenerateToken;
import com.scaler.seckill.producer.SpikeCommodityProducer;
import com.scaler.seckill.service.mapper.SeckillMapper;
import com.scaler.seckill.service.mapper.entity.SeckillEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private GenerateToken generateToken;
	@Autowired
	private SpikeCommodityProducer spikeCommodityProducer;

	@Override
	@Transactional
	@HystrixCommand(fallbackMethod = "spikeFallback")
	public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
		// 1.参数验证
		if (StringUtils.isEmpty(phone)) {
			return setResultError("手机号码不能为空!");
		}
		if (seckillId == null) {
			return setResultError("商品库存id不能为空!");
		}
		// 2.从redis从获取对应的秒杀token
		String seckillToken = generateToken.getListKeyToken(seckillId + "");
		if (StringUtils.isEmpty(seckillToken)) {
			log.info(">>>seckillId:{}, 亲，该秒杀已经售空，请下次再来!", seckillId);
			return setResultError("亲，该秒杀已经售空，请下次再来!");
		}

		// 3.获取到秒杀token之后，异步放入mq中实现修改商品的库存
		sendSeckillMsg(seckillId, phone);
		return setResultSuccess("正在排队中.......");
	}
    private BaseResponse<JSONObject> spikeFallback(String phone, Long seckillId) {
        return setResultError("服务器忙,请稍后重试!");
    }
	/**
	 * 获取到秒杀token之后，异步放入mq中实现修改商品的库存
	 */
	@Async
	private void sendSeckillMsg(Long seckillId, String phone) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seckillId", seckillId);
		jsonObject.put("phone", phone);
		spikeCommodityProducer.send(jsonObject);
	}

	/**
	 *
	 * 
	 * @param seckillId
	 * @param tokenQuantity
	 * @return
	 */

	// 采用redis数据库类型为 list类型 key为 商品库存id list 多个秒杀token
	@Override
	public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
		// 1.验证参数
		if (seckillId == null) {
			return setResultError("商品库存id不能为空!");
		}
		if (tokenQuantity == null) {
			return setResultError("token数量不能为空!");
		}
		SeckillEntity seckillEntity = seckillMapper.findBySeckillId(seckillId);
		if (seckillEntity == null) {
			return setResultError("商品信息不存在!");
		}
		// 2.使用多线程异步生产令牌
		createSeckillToken(seckillId, tokenQuantity);
		return setResultSuccess("令牌正在生成中.....");
	}

	@Async
	private void createSeckillToken(Long seckillId, Long tokenQuantity) {
		generateToken.createListToken("seckill_", seckillId + "", tokenQuantity);
	}

}
