package com.aokunsang.web.interceptor;

/**
 * Created by marty on 14-8-4.
 */
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aokunsang.authority.AuthorityHelper;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.po.User;
import com.aokunsang.util.ResultTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("");
        HandlerMethod handler2=(HandlerMethod) handler;
        FireAuthority fireAuthority = handler2.getMethodAnnotation(FireAuthority.class);

        if(null == fireAuthority){
            //没有声明权限,放行
            return true;
        }

        logger.debug("fireAuthority", fireAuthority.toString());

        HttpSession session = request.getSession();
        User manager = (User)session.getAttribute("user");
        boolean aflag = false;

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
                sb.append("/oprst.jsp?oprst=false&opmsg=").append(URLEncoder.encode("没有权限","utf-8"));
                response.sendRedirect(sb.toString());
            } else if (fireAuthority.resultType() == ResultTypeEnum.json) {
                //ajax类型的登录提示
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
                pw.println("{\"result\":false,\"code\":12,\"errorMessage\":\""+"没有权限"+"\"}");
                pw.flush();
                pw.close();
            }

            return false;

        }
        return true;
    }

}

