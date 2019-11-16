package com.scaler.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.core.token.GenerateToken;
import com.scaler.core.twitter.SnowflakeIdUtils;
import com.scaler.pay.input.dto.PayCratePayTokenDto;
import com.scaler.pay.mapper.PaymentTransactionMapper;
import com.scaler.pay.mapper.entity.PaymentTransactionEntity;
import com.scaler.pay.service.api.PayMentTransacTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PayMentTransacTokenServiceImpl
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/25 20:47
 * @Version 1.0
 **/
@RestController
public class PayMentTransacTokenServiceImpl extends BaseApiService<JSONObject> implements PayMentTransacTokenService {

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    @Override
    public BaseResponse<JSONObject> cratePayToken(PayCratePayTokenDto payCratePayTokenDto) {
        String orderId = payCratePayTokenDto.getOrderId();
        if (StringUtils.isEmpty(orderId)) {
            return setResultError("订单号码不能为空!");
        }
        Long payAmount = payCratePayTokenDto.getPayAmount();
        if (payAmount == null) {
            return setResultError("金额不能为空!");
        }
        Long userId = payCratePayTokenDto.getUserId();
        if (userId == null) {
            return setResultError("userId不能为空!");
        }
        PaymentTransactionEntity paymentTransactionEntity=new PaymentTransactionEntity();
        paymentTransactionEntity.setOrderId(orderId);
        paymentTransactionEntity.setPayAmount(payAmount);
        paymentTransactionEntity.setUserId(userId);
        // 使用雪花算法 生成全局id
        paymentTransactionEntity.setPaymentId(SnowflakeIdUtils.nextId());
        int result = paymentTransactionMapper.insertPaymentTransaction(paymentTransactionEntity);
        if (!toDaoResult(result)) {
            return setResultError("系统错误!");
        }
        // 获取主键id
        Long payId = paymentTransactionEntity.getId();
        if (payId == null) {
            return setResultError("系统错误!");
        }

        // 3.生成对应支付令牌
        String keyPrefix = "pay_";
        String token = generateToken.createToken(keyPrefix, payId + "");
        JSONObject dataResult = new JSONObject();
        dataResult.put("token", token);

        return setResultSuccess(dataResult);
    }
}
