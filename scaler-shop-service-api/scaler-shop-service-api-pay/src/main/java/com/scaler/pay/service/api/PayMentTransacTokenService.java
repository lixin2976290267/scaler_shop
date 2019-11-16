package com.scaler.pay.service.api;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseResponse;
import com.scaler.pay.input.dto.PayCratePayTokenDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

public interface PayMentTransacTokenService {
    /**
     * 创建支付令牌
     *
     * @return
     */
    @GetMapping("/cratePayToken")
    public BaseResponse<JSONObject> cratePayToken(@Validated PayCratePayTokenDto payCratePayTokenDto);
}
