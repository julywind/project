/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.JsonResultBean;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.util.ResultTypeEnum;
import com.aokunsang.util.TextUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;
import com.aokunsang.web.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
	public ModelAndView login(HttpSession session){
        if(getLoginUser(session)!=null)
        {
            return new ModelAndView("JumpUrl","result", new JsonResultBean(true,getLoginUser(session).toString(),"/user/home"));
        }
		return new ModelAndView("login", "result", new JsonResultBean(false,"请输入userName和passWord登录"));
	}

    @RequestMapping(value="/user/info",method=RequestMethod.GET)
    public ModelAndView loginInfo(HttpSession session){
        return new ModelAndView("login", "user", getLoginUser(session));
    }

	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public ModelAndView logon(HttpSession session,String userName,String passWord){
        Map<String,Object> data = new HashMap<String,Object>();
        if(getLoginUser(session)!=null)
        {
            //return new ModelAndView(new RedirectView("/user/home",true),"result", new JsonResultBean(true,"登录成功"));
            data.put("msg","登陆成功");
            data.put("user", getLoginUser(session));
            return new ModelAndView("JumpUrl", "result", new JsonResultBean(true,data,"/user/home"));
        }

		User user = loginService.getUser(userName, passWord);
		if(user!=null){
            user.setPassWord("");
            data.put("msg","登陆成功");
            data.put("user",user);
            session.setAttribute(LOGIN_FLAG,user);
            //return new ModelAndView(new RedirectView("/user/home",true), "result", new JsonResultBean(true,"登录成功"));
            return new ModelAndView("JumpUrl", "result", new JsonResultBean(true,data,"/user/home"));
		}else{
            data.put("msg","用户名或者密码错误");
            data.put("user",null);
            return new ModelAndView("login", "result", new JsonResultBean(false,data));
		}
	}

    @FireAuthority(authorityTypes = {AuthorityType.USER_MANAGE})
    @RequestMapping(value="/user/checkName",method=RequestMethod.POST)
    public ModelAndView checkUserExist(User user){
        if(null == user.getAuthority()){
            user.setAuthority("100000");
        }
        if(!loginService.duplicateUser(user)){
            //return new ModelAndView(new RedirectView("/user/home",true), "result", new JsonResultBean(true,"可以使用"));
            return new ModelAndView("JumpUrl", "result", new JsonResultBean(true,"可以使用","/user/home"));
        }
        //return new ModelAndView(new RedirectView("/user/home",true), "result", new JsonResultBean(false,"<font color='red'>已存在</font>"));
        return new ModelAndView("JumpUrl", "result", new JsonResultBean(false,"<font color='red'>已存在</font>","/user/home"));
    }


    @FireAuthority(authorityTypes = {AuthorityType.USER_MANAGE}, resultType= ResultTypeEnum.page)
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public ModelAndView register(User user){
		if(null == user.getAuthority()){
			user.setAuthority("100000");
		}
		if(!loginService.duplicateUser(user)){
			loginService.addUser(user);
            return new ModelAndView("JumpUrl", "result", new JsonResultBean(true,"操作成功","user/list"));
		}
        return new ModelAndView("JumpUrl", "result", new JsonResultBean(false,"用户名已存在","user/register"));
	}

    @FireAuthority(authorityTypes = {AuthorityType.USER_MANAGE}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/user/register",method=RequestMethod.GET)
    public ModelAndView registerPage(User user){
        if(null == user.getAuthority()){
            user.setAuthority("000000");
            return new ModelAndView("register");
        }
        return new ModelAndView("register", "result", new JsonResultBean(true,"您访问了登陆界面，无json数据"));
    }

    @RequestMapping(value="/tip/noPermission")
    public ModelAndView noPermission(){
        return new ModelAndView("noPermission", "result", new JsonResultBean(false,"您无权访问此路径"));
    }

    @RequestMapping(value="/user/logout")
    public ModelAndView logout(HttpSession session){
        session.removeAttribute(LOGIN_FLAG);
        //return new ModelAndView(new RedirectView("/user/home",true), "result", new JsonResultBean(true,"操作成功"));
        return new ModelAndView("JumpUrl", "result", new JsonResultBean(true,"操作成功","/user/home"));
    }

    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/user/list")
    public ModelAndView getUsers(Integer offset,Integer limit,String whereCondition){
        String sql = "select %s from "+User.TABLE_NAME+" "+ (TextUtil.isEmpty(whereCondition)?"":(" where "+whereCondition));
        String sql2 = sql;
        if(limit!=null&&limit>=0)
        {
            sql2 += " limit "+limit;
        }
        if(offset!=null&&offset>=0)
        {
            sql2 += " offset "+offset;
        }
        return new ModelAndView("user/list", "result", new JsonResultBean(true,
                loginService.getCount(String.format(sql,"count(*) as totalCount")),
                loginService.query(String.format(sql2,"*"), null)));
    }

    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/user/updatePasswd",method=RequestMethod.GET)
    public ModelAndView showUpdatePassWd(User user){
        return new ModelAndView("user/update_passwd","result",new JsonResultBean(true,user));
    }

    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/user/updatePasswd",method=RequestMethod.POST)
    public ModelAndView updatePassWd(User user,String newPassword,String rePassword,HttpSession session) {

        if(TextUtil.isEmpty(newPassword) || TextUtil.isEmpty(rePassword) || !newPassword.equals(rePassword))
        {
            return new ModelAndView("JumpUrl"/*new RedirectView("/user/home",true)*/, "result", new JsonResultBean(false, "两次密码输入不相同","user/home"));
        }
        User old_user = loginService.getUser(user.getUserName(), user.getPassWord());
        if (old_user == null) {
            return new ModelAndView("JumpUrl"/*new RedirectView("/user/home",true)*/, "result", new JsonResultBean(false, "原密码不正确","user/home"));
        } else {
            old_user.setPassWord(newPassword);
            int result=loginService.updatePasswd(old_user);
            if(result>0)
                return logout(session);/*new ModelAndView("user/update_passwd", "result", new JsonResultBean(true, "操作成功"));*/
            else
            {
                return new ModelAndView("JumpUrl"/*new RedirectView("/user/home",true)*/, "result", new JsonResultBean(false, "修改失败","user/home"));
            }
        }
    }
}
