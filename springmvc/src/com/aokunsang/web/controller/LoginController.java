/**
 * 
 */
package com.aokunsang.web.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    //@FireAuthority(authorityTypes = AuthorityType.SALES_ORDER_DELETE, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}

    //@FireAuthority(authorityTypes = {AuthorityType.SALES_ORDER_DELETE,AuthorityType.SALES_ORDER_CREATE}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public String logon(String userName,String passWord){
		User user = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");			
			String encode = new String(md5.digest(passWord.getBytes("GBK")));
			
			user = loginService.getUser(userName, encode);
		} catch (RuntimeException e) {
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user!=null){
			return "MyHome";
		}else{
			return "login";
		}
	}

    //@FireAuthority(authorityTypes = {AuthorityType.WORKER}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public String register(User user){
		if(null == user.getAuthority()){
			user.setAuthority("000000");
		}
		loginService.addUser(user);
		return "login";
	}

    @RequestMapping(value="/tip/noPermission")
    public String noPermission(){
        return "noPermission";
    }
}
