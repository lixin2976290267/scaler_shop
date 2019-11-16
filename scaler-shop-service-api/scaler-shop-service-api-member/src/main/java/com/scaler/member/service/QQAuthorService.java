package com.scaler.member.service;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//用户授权接口
public interface QQAuthorService {

    /**
     * 根据 openid查询是否已经绑定,如果已经绑定，则直接实现自动登陆
     *
     * @return
     */
    @RequestMapping("/findByOpenId")
    BaseResponse<JSONObject> findByOpenId(@RequestParam("qqOpenId") String qqOpenId);

}
