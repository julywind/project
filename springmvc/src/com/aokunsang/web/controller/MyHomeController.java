/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.JsonResultBean;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.po.User;
import com.aokunsang.util.ResultTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aokunsang.web.BaseController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Controller
public class MyHomeController extends BaseController {
    private User getLoginUser(HttpSession session){
        return (User)session.getAttribute(LoginController.LOGIN_FLAG);
    }
    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/home")
	public ModelAndView ajaxGet(HttpSession session){
        return new ModelAndView("MyHome","result", new JsonResultBean(true,getLoginUser(session)));
	}
}
