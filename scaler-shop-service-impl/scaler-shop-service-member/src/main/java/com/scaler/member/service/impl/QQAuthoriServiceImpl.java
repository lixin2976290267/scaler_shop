package com.scaler.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.token.GenerateToken;
import com.scaler.member.mapper.UserMapper;
import com.scaler.member.mapper.entity.UserDo;
import com.scaler.member.service.QQAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName QQAuthoriServiceImpl
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/22 22:46
 * @Version 1.0
 **/
@RestController
public class QQAuthoriServiceImpl extends BaseApiService<JSONObject> implements QQAuthorService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<JSONObject> findByOpenId(String qqOpenId) {
        //1.根据open id去查询用户信息
        if (StringUtils.isEmpty(qqOpenId)) {
            return setResultError("qqOpenId不能为空!");
        }
        UserDo userDo = userMapper.findByOpenId(qqOpenId);
        //如果未查到 直接返回状态吗203
        if (userDo == null) {
            return setResultError(Constants.HTTP_RES_CODE_NOTUSER_203, "根据qqOpenId没有查询到用户信息");
        }
        String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + "QQ_LOGIN";
        Long userId = userDo.getUserId();
        String userToken = generateToken.createToken(keyPrefix, userId + "");
        JSONObject data=new JSONObject();
        data.put("token",userToken);
        //唯一登陆 未实现
        return setResultSuccess(data);


        //如果能查到用户信息 返回对应用户信息的token

    }
}
