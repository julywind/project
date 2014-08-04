/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.util.ResultTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aokunsang.web.BaseController;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Controller
public class MyHomeController extends BaseController {

    @FireAuthority(authorityTypes = {AuthorityType.SALES_ORDER_FIND}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/home",method=RequestMethod.GET)
	public String ajaxGet(){
		
		return "MyHome";
	}
}
