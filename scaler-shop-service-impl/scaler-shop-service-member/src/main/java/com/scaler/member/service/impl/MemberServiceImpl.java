package com.scaler.member.service.impl;

import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.core.bean.ScalerBeanUtils;
import com.scaler.constants.Constants;
import com.scaler.core.token.GenerateToken;
import com.scaler.core.type.TypeCastHelper;
import com.scaler.core.utils.MD5Util;
import com.scaler.member.feign.WeiXinServiceFeign;
import com.scaler.member.input.dto.UserLoginInpDTO;
import com.scaler.member.mapper.UserMapper;
import com.scaler.member.mapper.UserTokenMapper;
import com.scaler.member.mapper.entity.UserDo;
import com.scaler.member.output.dto.UserOutDTO;
import com.scaler.member.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MemberServiceImpl  查询会员信息
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/15 21:00
 * @Version 1.0
 **/
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        // 1.验证参数
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        //2.根据手机号码查询用户信息 单独定义code 表示用户信息不存在
        UserOutDTO userOutDTO = ScalerBeanUtils.doToDto(userMapper.existMobile(mobile), UserOutDTO.class);
        if (userOutDTO == null) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户不存在");
        }
        return setResultSuccess(userOutDTO);
    }

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {
        //1.验证token
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空");
        }
        //2.使用token向redis中查询userId
        String redisUserId = generateToken.getToken(token);
        if (StringUtils.isEmpty(redisUserId)) {
            return setResultError("token已经失效或者token错误");
        }
        //3.根据userid查询用户信息
        Long userId = TypeCastHelper.toLong(redisUserId);
        UserDo userDo = userMapper.findByUserId(userId);
        if (userDo == null) {
            return setResultError("用户不存在");
        }

        return setResultSuccess(ScalerBeanUtils.doToDto(userDo, UserOutDTO.class));
    }

    @Override
    public BaseResponse<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        // 判断登陆类型
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        // 目的是限制范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        // 2.对登陆密码实现加密
        String newPassWord = MD5Util.MD5(password);
        // 3.使用手机号码+密码查询数据库 ，判断用户是否存在
        UserDo userDo = userMapper.login(mobile, newPassWord);
        if (userDo == null) {
            return setResultError("用户名称或者密码错误!");
        }
        return setResultSuccess(ScalerBeanUtils.doToDto(userDo, UserOutDTO.class));
    }
    //token存放在PC端 存放在本地文件中   如果退出或者修改密码 ，忘记密码 对token状态进行标识
    //token很难防御伪造 尽量实现安全 中能在某些业务模块加上必须验证本人操作

}

