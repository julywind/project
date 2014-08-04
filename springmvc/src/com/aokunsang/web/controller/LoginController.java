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
import org.springframework.web.bind.annotation.ResponseBody;

import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;
import com.aokunsang.web.BaseController;

/**
 * 登陆方法
 * @author tushen
 * @date Nov 4, 2011
 */
@Controller
public class LoginController extends BaseController{

	@Autowired
	private LoginService loginService;

    @FireAuthority(authorityTypes = AuthorityType.SALES_ORDER_DELETE, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/login",method=RequestMethod.GET)
	public String login(){
		
		return "login";
	}

    @FireAuthority(authorityTypes = {AuthorityType.SALES_ORDER_DELETE,AuthorityType.SALES_ORDER_CREATE}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public String logon(String userName,String passWord){
		User user = null;
		try {
			user = loginService.getUser(userName, passWord);
		} catch (RuntimeException e) {
		}
		if(user!=null){
			return "MyHome";
		}else{
			return "login";
		}
	}

    @FireAuthority(authorityTypes = {AuthorityType.WORKER}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public String register(User user){
		loginService.addUser(user);
		return "login";
	}
}
