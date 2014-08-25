/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.util.ResultTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;
import com.aokunsang.web.BaseController;

import javax.servlet.http.HttpSession;

/**
 * 登陆方法
 * @author tushen
 * @date Nov 4, 2011
 */
@Controller
public class LoginController extends BaseController{

    public static final String LOGIN_FLAG = "logged_user";
	@Autowired
	private LoginService loginService;

    private User getLoginUser(HttpSession session){
        return (User)session.getAttribute(LOGIN_FLAG);
    }
	@RequestMapping(value="/user/login",method=RequestMethod.GET)
	public String login(HttpSession session){
        if(getLoginUser(session)!=null)
        {
            return "redirect:/user/home";
        }
		return "login";
	}

	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public String logon(HttpSession session,String userName,String passWord){
        if(getLoginUser(session)!=null)
        {
            return "redirect:/user/home";
        }

		User user = loginService.getUser(userName, passWord);
		if(user!=null){
            session.setAttribute(LOGIN_FLAG,user);
			return "redirect:/user/home";
		}else{
			return "login";
		}
	}

    @FireAuthority(authorityTypes = {AuthorityType.USER_MANAGE}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public String register(User user){
		if(null == user.getAuthority()){
			user.setAuthority("100000");
		}
		if(!loginService.duplicateUser(user)){
			loginService.addUser(user);
			return "login";
		}
		return "redirect:/user/home";
	}

    @FireAuthority(authorityTypes = {AuthorityType.USER_MANAGE}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/user/register",method=RequestMethod.GET)
    public String registerPage(User user){
        if(null == user.getAuthority()){
            //user为null  这样使用 会抛出空指针异常
            //user.setAuthority("000000");
            return "register";
        }
        return "redirect:/user/home";
    }

    @RequestMapping(value="/tip/noPermission")
    public String noPermission(){
        return "noPermission";
    }

    @RequestMapping(value="/user/logout")
    public String logout(HttpSession session){
        session.removeAttribute(LOGIN_FLAG);
        return "redirect:/user/home";
    }
}
