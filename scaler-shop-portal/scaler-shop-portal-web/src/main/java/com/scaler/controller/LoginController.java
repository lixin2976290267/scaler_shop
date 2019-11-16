package com.scaler.controller;

import com.alibaba.fastjson.JSONObject;
import com.scaler.web.vo.LoginVo;
import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.bean.ScalerBeanUtils;
import com.scaler.feign.MemberLoginServiceFeign;
import com.scaler.member.input.dto.UserLoginInpDTO;
import com.scaler.web.base.BaseWebController;
import com.scaler.web.constants.WebConstants;
import com.scaler.web.utils.CookieUtils;
import com.scaler.web.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseWebController {
    private static final String MEMBER_LOGIN_PAGE = "member/login";
    private static final String INDEX_FTL = "redirect:/";

    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;


    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("/login")
    public String getLogin() {
        return MEMBER_LOGIN_PAGE;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginVo") LoginVo loginVo, Model model, HttpServletRequest request,
                            HttpServletResponse response, HttpSession httpSession) {
        //判断图形验证码
        String graphicCode = loginVo.getGraphicCode();
        Boolean aBoolean = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!aBoolean){
            setErrorMsg(model,"图形验证码不正确");
            return MEMBER_LOGIN_PAGE;
        }
        // 2.将vo转换为dto
        UserLoginInpDTO userLoginInpDTO = ScalerBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
        String info = webBrowserInfo(request);
        userLoginInpDTO.setDeviceInfor(info);
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        if (login==null){
            setErrorMsg(model,login.getMsg());
            return MEMBER_LOGIN_PAGE;
        }
        //2.将token存入到cookie中
        JSONObject data=login.getData();
        String token=data.getString("token");
        CookieUtils.setCookie(request,response,WebConstants.LOGIN_TOKEN_COOKIENAME,token);
        return INDEX_FTL;
    }



}
