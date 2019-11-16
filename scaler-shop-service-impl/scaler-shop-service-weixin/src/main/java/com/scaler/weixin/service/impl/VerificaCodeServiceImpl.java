package com.scaler.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.utils.RedisUtil;
import com.scaler.weixin.service.VerificaCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VerificaCodeServiceImpl
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/20 16:43
 * @Version 1.0
 **/
@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
        //1.验证参数是否为空
        if (StringUtils.isEmpty(phone)){
            return setResultError("手机号码不能为空!");
        }
        if (StringUtils.isEmpty(weixinCode)){
            return setResultError("注册码不能为空!");
        }
        //2.根据手机号码查询redis返回对应的注册码
        String weixinCodeKey=Constants.WEIXINCODE_KEY + phone;
        String redisCode = redisUtil.getString(weixinCodeKey);
        if (StringUtils.isEmpty(redisCode)){
            return setResultError("注册码可能已经过期!");
        }
        //3.redis中的注册码与传递参数的weixincode  进行比对
        if (!redisCode.equals(weixinCode)){
            return setResultError("注册码不正确!");
        }
        redisUtil.delKey(weixinCodeKey);
        return setResultSuccess("验证码比对正确！");
    }
}
