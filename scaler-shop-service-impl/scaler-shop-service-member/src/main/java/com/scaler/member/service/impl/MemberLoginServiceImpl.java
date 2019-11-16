package com.scaler.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.token.GenerateToken;
import com.scaler.core.transaction.RedisDataSoureceTransaction;
import com.scaler.core.utils.MD5Util;
import com.scaler.member.input.dto.UserLoginInpDTO;
import com.scaler.member.mapper.UserMapper;
import com.scaler.member.mapper.UserTokenMapper;
import com.scaler.member.mapper.entity.UserDo;
import com.scaler.member.mapper.entity.UserTokenDo;
import com.scaler.member.service.MemberLoginService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

/**
 * @ClassName MemberLoginServiceImpl
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/21 15:02
 * @Version 1.0
 **/
@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        //1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }

        //2.对登陆密码进行加密
        String newPassword = MD5Util.MD5(password);
        UserDo userDo = userMapper.login(mobile, newPassword);
        //3.使用手机号码+密码进行登陆
        if (userDo == null) {
            return setResultError("用户名或者密码错误！");
        }

        //用户登陆Token 相当于session id 存在redis中
        //用户每一个端登陆成功之后，会对应生成一个token令牌（临时且唯一）
        // 存在redis中最为reids key  value为 user id

        //4.获取user id
        Long userId = userDo.getUserId();
        //5.根据userid+logintype 查询之前是否登陆过
        UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDo != null) {
                //如果登陆过 清除之前redistoken
                String token = userTokenDo.getToken();
                generateToken.removeToken(token);
                //把token的状态改为1
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
                if (!toDaoResult(updateTokenAvailability)) {
                    return setResultError("系统错误");
                }
            }


            // 如果有存放open Id，修改到数据库中
            // openid关联用户账号信息 QQ联合登陆
            String qqOpenId = userLoginInpDTO.getQqOpenId();
            if (!StringUtils.isEmpty(qqOpenId)) {
                userMapper.updateUserOpenId(qqOpenId, userId);
            }


            //1.插入新的token
            UserTokenDo newUserTokenDo = new UserTokenDo();
            newUserTokenDo.setUserId(userId);

            //5.生成对应的token 存在redis中
            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + loginType;
            String newToken = generateToken.createToken(keyPrefix, userId + "");

            newUserTokenDo.setToken(newToken);
            newUserTokenDo.setLoginType(userLoginInpDTO.getLoginType());
            newUserTokenDo.setDeviceInfor(deviceInfor);
            int insertUserToken = userTokenMapper.insertUserToken(newUserTokenDo);
            if (!toDaoResult(insertUserToken)) {
                redisDataSoureceTransaction.rollback(transactionStatus);
                return setResultError("系统错误");
            }
            JSONObject data = new JSONObject();
            data.put("token", newToken);
            redisDataSoureceTransaction.commit(transactionStatus);
            return setResultSuccess(data);
        } catch (Exception e) {
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception e1) {
            }
            return setResultError("系统错误");
        }

    }
}
//redis 与数据库如何保持一致问题
//@Transactional 不能控制redis事务
//redis 中存在事务
//自定义方法 使用编程式事务begin（既需要控制数据库事务也需要控制redis事务）  commit