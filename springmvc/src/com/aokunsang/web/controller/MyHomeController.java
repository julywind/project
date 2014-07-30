/**
 * 
 */
package com.aokunsang.web.controller;

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

	@RequestMapping(value="/user/home",method=RequestMethod.GET)
	public String ajaxGet(){
		
		return "MyHome";
	}
}
