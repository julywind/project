/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.po.Alarm;
import com.aokunsang.po.User;
import com.aokunsang.service.AlarmService;
import com.aokunsang.util.ResultTypeEnum;
import com.aokunsang.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 登陆方法
 * @author tushen
 * @date Nov 4, 2011
 */
@Controller
public class AlarmController extends BaseController{

	@Autowired
	private AlarmService alarmService;

	@RequestMapping(value="/alarm/list",method=RequestMethod.GET)
	public String alarmList(Alarm alarm){
        alarmService.getAlarm(alarm);
		return "login";
	}
}
