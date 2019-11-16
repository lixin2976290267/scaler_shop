package com.scaler.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseApiService;
import com.scaler.base.BaseResponse;
import com.scaler.core.bean.ScalerBeanUtils;
import com.scaler.constants.Constants;
import com.scaler.core.utils.MD5Util;
import com.scaler.member.feign.VerificaCodeServiceFeign;
import com.scaler.member.input.dto.UserInpDTO;
import com.scaler.member.mapper.UserMapper;
import com.scaler.member.mapper.entity.UserDo;
import com.scaler.member.service.MemberRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MemberRegisterServiceImpl 会员注册
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/20 22:40
 * @Version 1.0
 **/
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;

    @Override
    @Transactional
    public BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO, String registCode) {
        //1.参数验证
//        String userName = userInpDTO.getUserName();
//        if (StringUtils.isEmpty(userName)) {
//            return setResultError("用户名称不能为空");
//        }
        String mobile = userInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空");
        }
        String password = userInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        //2.验证码注册码是否正确  会员调用微信接口实现注册码验证
        BaseResponse<JSONObject> verificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile, registCode);
        if (!verificaWeixinCode.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setResultError(verificaWeixinCode.getMsg());
        }
        //3.对用户密码进行加密
        String newPassword = MD5Util.MD5(password);
        userInpDTO.setPassword(newPassword);
        //4.调用数据库插入数据
        UserDo userDo = ScalerBeanUtils.dtoToDo(userInpDTO, UserDo.class);
        return userMapper.register(userDo) > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");

    }
}
