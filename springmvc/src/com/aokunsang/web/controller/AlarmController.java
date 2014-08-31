/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.JsonResultBean;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.po.Alarm;
import com.aokunsang.po.User;
import com.aokunsang.service.AlarmService;
import com.aokunsang.util.ResultTypeEnum;
import com.aokunsang.util.TextUtil;
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
    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL})
	@RequestMapping(value="/alarm/list")
	public ModelAndView alarmList(Integer offset,Integer limit,String whereCondition){
        String sql = "select %s from alarm "+ (TextUtil.isEmpty(whereCondition)?"":(" where "+whereCondition));
        String sql2 = sql;
        if(limit!=null&&limit>=0)
        {
            sql2 += " limit "+limit;
        }
        if(offset!=null&&offset>=0)
        {
            sql2 += " offset "+offset;
        }
        return new ModelAndView("alarm/list", "result", new JsonResultBean(true,
                alarmService.getCount(String.format(sql,"count(*) as totalCount")),
                alarmService.query(String.format(sql2,"*"), null)));
	}
}
