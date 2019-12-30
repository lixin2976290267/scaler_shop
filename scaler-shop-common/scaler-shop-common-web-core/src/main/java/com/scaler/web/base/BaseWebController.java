package com.scaler.web.base;

import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public class BaseWebController {
    /**
     * 500页面
     */
    protected static final String ERROR_500_FTL = "500.ftl";

    public Boolean isSuccess(BaseResponse<?> baseResp) {
        if (baseResp == null) {
            return false;
        }
        if (baseResp.getCode().equals(Constants.HTTP_RES_CODE_500)) {
            return false;
        }
        return true;
    }

    public void setErrorMsg(Model model, String errorMsg) {
        model.addAttribute("error", errorMsg);
    }

    
}
