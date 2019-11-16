package com.scaler.member.service;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseResponse;
import com.scaler.member.input.dto.UserLoginInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "会员登陆服务接口")
public interface MemberLoginService {
    /**
     * 用户登陆接口
     *
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "会员用户登陆信息接口")
    BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);
}
