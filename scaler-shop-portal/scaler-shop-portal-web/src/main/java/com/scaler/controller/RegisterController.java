package com.scaler.controller;

import com.alibaba.fastjson.JSONObject;
import com.scaler.web.vo.RegisterVo;
import com.scaler.base.BaseResponse;
import com.scaler.core.bean.ScalerBeanUtils;
import com.scaler.feign.MemberRegisterServiceFeign;
import com.scaler.member.input.dto.UserInpDTO;
import com.scaler.web.base.BaseWebController;
import com.scaler.web.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
//注册
@Controller
public class RegisterController extends BaseWebController {
    private static final String MEMBER_REGISTER_PAGE = "member/register";
    private static final String MEMBER_LOGIN_PAGE = "member/login";
    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String getRegister() {
        return MEMBER_REGISTER_PAGE;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerVo")@Validated RegisterVo registerVo,
                               BindingResult bindingResult, Model model, HttpSession httpSession) {
        //1,接受表单参数   创建对象接受参数 vo- do- dto
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return MEMBER_REGISTER_PAGE;
        }
        //判断图形验证码
        String graphicCode = registerVo.getGraphicCode();
        Boolean aBoolean = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!aBoolean){
            setErrorMsg(model,"图形验证码不正确");
            return MEMBER_REGISTER_PAGE;
        }
        //2，调用会员服务接口实现注册
        UserInpDTO userInpDTO = ScalerBeanUtils.voToDto(registerVo, UserInpDTO.class);
        BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(userInpDTO, registerVo.getRegistCode());
        if (!isSuccess(register)) {
            setErrorMsg(model, register.getMsg());
            return MEMBER_REGISTER_PAGE;
        }
        //跳转到登陆页面
        return MEMBER_LOGIN_PAGE;
    }

}
