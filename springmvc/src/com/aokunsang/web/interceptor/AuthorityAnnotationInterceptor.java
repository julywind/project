package com.aokunsang.web.interceptor;

/**
 * Created by marty on 14-8-4.
 */
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aokunsang.authority.AuthorityHelper;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;
import com.aokunsang.util.ResultTypeEnum;
import com.aokunsang.web.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("uri:", request.getRequestURI());
        HandlerMethod handler2=(HandlerMethod) handler;
        FireAuthority fireAuthority = handler2.getMethodAnnotation(FireAuthority.class);

        logger.debug(getClass().getSimpleName(),"HeaderInfo:  start");
        Enumeration df = request.getHeaderNames();
        String headerinfo = "";
        while(df.hasMoreElements())
        {
            String test = (String)df.nextElement();
            logger.debug(getClass().getSimpleName(),"HeaderInfo:   <"+test+">"+request.getHeader(test));
            headerinfo+="HeaderInfo:   <"+test+">"+request.getHeader(test)+"\n";

        }
        System.out.println(headerinfo);
        logger.debug(getClass().getSimpleName(),"HeaderInfo:  end");
        if(null == fireAuthority){
            //没有声明权限,放行
            return true;
        }

        logger.debug("fireAuthority", fireAuthority.toString());

        HttpSession session = request.getSession();
        User manager = (User)session.getAttribute(LoginController.LOGIN_FLAG);
        boolean aflag = false;

        if(manager==null) {
            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWord");
            manager = loginService.getUser(userName, passWord);
            if(manager!=null) {
                session.setAttribute(LoginController.LOGIN_FLAG,manager);
            }
        }

        if(manager!=null)
        {
            for(AuthorityType at:fireAuthority.authorityTypes()){
                if(AuthorityHelper.hasAuthority(at.getIndex(), manager.getAuthority())==true){
                    aflag = true;
                    break;
                }
            }
        }

        if(false == aflag){
            if (fireAuthority.resultType() == ResultTypeEnum.page) {
                //传统的登录页面
                StringBuilder sb = new StringBuilder();
                sb.append(request.getContextPath());
                if(manager!=null) {
                    sb.append("/tip/noPermission.html?&msg=").append(URLEncoder.encode("No permission to access", "UTF-8"));
                }else
                {
                    sb.append("/user/login.html");
                }
                response.sendRedirect(sb.toString());
            } else if (fireAuthority.resultType() == ResultTypeEnum.json) {
                //ajax类型的登录提示
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
                if(manager!=null) {
                    pw.println("{\"result\":false,\"code\":12,\"errorMessage\":\""+"No permission to access"+"\"}");
                }else
                {
                    pw.println("{\"result\":false,\"code\":12,\"errorMessage\":\""+"You did't logged in system."+"\"}");
                }

                pw.flush();
                pw.close();
            }

            return false;

        }
        return true;
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        String name=java.net.URLEncoder.encode("测试", "UTF-8");
        System.out.println(name);
        System.out.println(java.net.URLDecoder.decode(name, "ISO-8859-1"));
    }


    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}

