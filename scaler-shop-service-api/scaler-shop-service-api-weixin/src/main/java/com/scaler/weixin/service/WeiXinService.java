package com.scaler.weixin.service;

import com.scaler.base.BaseResponse;
import com.scaler.weixin.input.dto.AppInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName WeiXinService
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/15 11:55
 * @Version 1.0
 **/
@Api(tags = "微信服务接口")
public interface WeiXinService {
    @ApiOperation(value = "微信服务接口")
    @GetMapping("/getApp")
    public BaseResponse<AppInpDTO> getApp();
}
